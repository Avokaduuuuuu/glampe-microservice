package com.avocado.booking;

import com.avocado.booking.dto.req.BookingDetailRequest;
import com.avocado.booking.dto.req.BookingRequest;
import com.avocado.booking.dto.req.BookingSelectionRequest;
import com.avocado.booking.dto.req.BookingUpdateRequest;
import com.avocado.booking.dto.resp.BookingBasicResponse;
import com.avocado.booking.dto.resp.BookingResponse;
import com.avocado.booking.enums.BookingStatusEnum;
import com.avocado.bookingdetail.BookingDetailEntity;
import com.avocado.bookingdetail.BookingDetailMapper;
import com.avocado.bookingdetail.dto.resp.BookingDetailResponse;
import com.avocado.bookingdetail.enums.BookingDetailStatusEnum;
import com.avocado.bookingselection.BookingSelectionEntity;
import com.avocado.bookingselection.BookingSelectionMapper;
import com.avocado.bookingselection.dto.resp.BookingSelectionBasicResponse;
import com.avocado.bookingselection.dto.resp.BookingSelectionResponse;
import com.avocado.client.campsite.CampSiteClient;
import com.avocado.client.campsite.dto.*;
import com.avocado.client.user.UserClient;
import com.avocado.utils.ApiResponse;
import com.avocado.config.SystemProperties;
import com.avocado.exception.BookingException;
import com.avocado.exception.ResultCode;
import com.avocado.utils.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final CampSiteClient campSiteClient;

    @Override
    public BookingResponse fetchBookingById(Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingException(ResultCode.BOOKING_NOT_FOUND));

        BookingBasicCampSiteResponse bookingBasicCampSiteResponse = campSiteClient.getCampSitesBatch(List.of(bookingEntity.getCampSiteId())).getData().get(bookingEntity.getCampSiteId());

        BookingResponse response = BookingMapper.INSTANCE.toResponse(bookingEntity);
        response.setCampSite(bookingBasicCampSiteResponse);
        response.setUser(userClient.getUserById(bookingEntity.getUserId()).getData());
        ApiResponse<Map<Long, BookingCampTypeResponse>> campTypeMap = campSiteClient.getBookingCampTypes(bookingEntity.getBookingDetails().stream().map(BookingDetailEntity::getCampTypeId).toList());
        response.setBookingDetails(bookingEntity.getBookingDetails().stream().map(bd -> {
            BookingDetailResponse bdr = BookingDetailMapper.INSTANCE.toResponse(bd);

            bdr.setCampType(campTypeMap.getData().get(bd.getCampTypeId()));
            log.info("BookingDetailResponse {}", bdr);
            return bdr;
        }).toList());

        ApiResponse<Map<Long, BookingCampSiteSelectionResponse>> selectionMap = campSiteClient.getCampSitesSelections(bookingEntity.getBookingSelections().stream().map(BookingSelectionEntity::getSelectionId).toList());
        log.info("BookingSelectionResponse {}", selectionMap);
        response.setBookingSelections(bookingEntity.getBookingSelections().stream().map(bs -> {
            BookingSelectionResponse bsr = BookingSelectionMapper.INSTANCE.toDetailResponse(bs);
            log.info("Selection Id {}", bs.getSelectionId());
            log.info("Selection Response {}", selectionMap.getData().get(bs.getSelectionId()));
            bsr.setSelection(selectionMap.getData().get(bs.getSelectionId()));
            log.info("BookingSelectionResponse {}", bsr);
            return bsr;
        }).toList());
        return response;
    }

    @Override
    public BookingBasicResponse createBooking(BookingRequest request) {
        userClient.getUserById(request.userId());
        BigDecimal systemFee = request.totalAmount().multiply(BigDecimal.valueOf(SystemProperties.SYSTEM_FEE.getValue()));

        BookingCampSiteResponse campSiteResponse = campSiteClient.getBookingCampSite(request.campSiteId()).getData();

        log.info("Campsite response: {}", campSiteResponse);

        List<BookingCampTypeResponse> campTypes = campSiteResponse.getCampTypes();
        List<BookingCampSiteSelectionResponse> selections = campSiteResponse.getSelections();

        Map<Long, BookingCampTypeResponse> campTypeMap = campTypes.stream()
                .collect(Collectors.toMap(BookingCampTypeResponse::getId, Function.identity()));
        Map<BookingDetailRequest, BookingCampTypeResponse> bookingDetailCampTypeMap = request.bookingDetailRequests().stream()
                .filter(bdr -> campTypeMap.containsKey(bdr.campTypeId()))
                .collect(Collectors.toMap(Function.identity(), bdr -> campTypeMap.get(bdr.campTypeId())));

        log.info("Booking detail response: {}", bookingDetailCampTypeMap);

        Map<Long, BookingCampSiteSelectionResponse> selectionMap = selections.stream()
                .collect(Collectors.toMap(BookingCampSiteSelectionResponse::getId, Function.identity()));
        Map<BookingSelectionRequest, BookingCampSiteSelectionResponse> bookingSelectionMap = request.bookingSelectionRequests().stream()
                .filter(bsr -> selectionMap.containsKey(bsr.selectionId()))
                .collect(Collectors.toMap(Function.identity(), bsr -> selectionMap.get(bsr.selectionId())));

        log.info("Booking selection response: {}", bookingSelectionMap);



        BookingEntity bookingEntity = BookingEntity.builder()
                .userId(request.userId())
                .campSiteId(request.campSiteId())
                .checkInAt(request.checkInTime())
                .checkOutAt(request.checkOutTime())
                .totalAmount(request.totalAmount())
                .systemFee(systemFee)
                .netAmount(request.totalAmount().subtract(systemFee))
                .status(BookingStatusEnum.Pending)
                .build();

        bookingEntity.setBookingDetails(toBookingDetailList(bookingDetailCampTypeMap, bookingEntity));
        log.info("Set Booking Details");
        bookingEntity.setBookingSelections(toBookingSelectionRequest(bookingSelectionMap, bookingEntity));
        log.info("Set Booking Selections");



        BookingBasicResponse bookingResponse = BookingMapper.INSTANCE.toBasicResponse(bookingRepository.save(bookingEntity));
        return bookingResponse;
    }

    @Override
    public PageResponse<BookingBasicResponse> fetchAll(BookingFilterParams params) {
        Specification<BookingEntity> specification = params.getSpecification();
        Sort sort = Sort.by(Sort.Direction.fromString(params.getSortOrder()), params.getSortBy());
        Pageable pageable = PageRequest.of(params.getCurrentPage(), params.getPageSize(), sort);

        Page<BookingEntity> bookingEntities = bookingRepository.findAll(specification, pageable);

        ApiResponse<Map<Long, BookingBasicCampSiteResponse>> bookingCampSiteMap = campSiteClient.getCampSitesBatch(
                bookingEntities.getContent().stream().map(BookingEntity::getCampSiteId).collect(Collectors.toList())
        );


        return PageResponse.<BookingBasicResponse>builder()
                .currentPage(params.getCurrentPage())
                .pageSize(params.getPageSize())
                .sortBy(params.getSortBy())
                .sortOrder(params.getSortOrder())
                .totalPages(bookingEntities.getTotalPages())
                .totalRecords(bookingEntities.getTotalElements())
                .records(bookingEntities.getContent().stream().map(bookingEntity -> {
                    BookingBasicResponse bookingResponse = BookingMapper.INSTANCE.toBasicResponse(bookingEntity);
                    bookingResponse.setCampSite(bookingCampSiteMap.getData().get(bookingEntity.getCampSiteId()));
                    return bookingResponse;
                }).toList())
                .build();
    }

    @Override
    public BookingBasicResponse updateBooking(BookingUpdateRequest request, Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingException(ResultCode.BOOKING_NOT_FOUND));

        bookingEntity.setStatus(request.status());
        return BookingMapper.INSTANCE.toBasicResponse(bookingRepository.save(bookingEntity));
    }

    private List<BookingDetailEntity> toBookingDetailList(Map<BookingDetailRequest, BookingCampTypeResponse> bookingDetailRequest, BookingEntity bookingEntity) {
        List<BookingDetailEntity> bookingDetailEntities = new ArrayList<>();
        bookingDetailRequest.forEach((key, value) -> {
            for (int i = 0;i<key.quantity();++i) {
                Long totalDays = Math.max(1, ChronoUnit.DAYS.between(bookingEntity.getCheckInAt(), bookingEntity.getCheckOutAt()));
                Long weekendDays = countWeekendDays(bookingEntity.getCheckInAt(), bookingEntity.getCheckOutAt());
                long weekDays = totalDays - weekendDays;
                bookingDetailEntities.add(
                        BookingDetailEntity.builder()
                                .campTypeId(key.campTypeId())
                                .amount(
                                        value.getPrice().multiply(BigDecimal.valueOf(weekDays)).add(value.getWeekendPrice().multiply(BigDecimal.valueOf(weekendDays)))
                                )
                                .status(BookingDetailStatusEnum.Pending)
                                .booking(bookingEntity)
                                .build()
                );
                log.info("Booking Detail End");
            }
        });
        return bookingDetailEntities;
    }

    private Long countWeekendDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkOut.isBefore(checkIn)) {
            log.error("Check-out time is before check-in time! checkIn={}, checkOut={}", checkIn, checkOut);
            return 0L;
        }

        long count = 0L;
        LocalDate date = checkIn.toLocalDate();
        LocalDate endDate = checkOut.toLocalDate();

        while (date.isBefore(endDate)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                count++;
            }
            date = date.plusDays(1);
        }

        return count;
    }


    private List<BookingSelectionEntity> toBookingSelectionRequest(Map<BookingSelectionRequest, BookingCampSiteSelectionResponse> bookingSelectionRequest, BookingEntity bookingEntity) {
        List<BookingSelectionEntity> bookingSelectionEntities = new ArrayList<>();
        bookingSelectionRequest.forEach((key, value) -> {
            bookingSelectionEntities.add(BookingSelectionEntity
                    .builder()
                            .selectionId(key.selectionId())
                            .name(value.getName())
                            .quantity(key.quantity())
                            .booking(bookingEntity)
                    .build()
            );
        });
        return bookingSelectionEntities;
    }
}
