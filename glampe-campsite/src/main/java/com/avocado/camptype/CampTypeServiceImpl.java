package com.avocado.camptype;

import com.avocado.camptype.dto.resp.CampTypeResponse;
import com.avocado.client.booking.dto.BookingClient;
import com.avocado.client.booking.dto.req.CampTypeBookedRequest;
import com.avocado.client.booking.dto.resp.CampTypeBookedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampTypeServiceImpl implements CampTypeService {

    private final CampTypeRepository campTypeRepository;
    private final BookingClient bookingClient;

    @Override
    public Map<Long, CampTypeResponse> getCampTypesByIds(List<Long> ids) {
        return campTypeRepository.findAllByIdIn(ids)
                .stream()
                .collect(Collectors.toMap(CampTypeEntity::getId, CampTypeMapper.INSTANCE::toResponse));
    }

    @Override
    public List<CampTypeResponse> getAllCampTypes(CampTypeFilterParams params) {
        Specification<CampTypeEntity> specification = params.buildSpecification();

        List<CampTypeResponse> campTypeResponses = campTypeRepository.findAll(specification)
                .stream().map(CampTypeMapper.INSTANCE::toResponse).toList();

        if (params.getCheckInAt() != null && params.getCheckOutAt() != null) {
            List<Long> campTypeIds = campTypeResponses.stream().map(CampTypeResponse::getId).toList();
            List<CampTypeBookedResponse> bookedCampTypes = bookingClient.bookedCampTypes(
                    new CampTypeBookedRequest(
                            campTypeIds, params.getCheckInAt(), params.getCheckOutAt()
                    )
            ).getData();

            Map<Long, Long> bookedQuantityMap = bookedCampTypes.stream().collect(Collectors.toMap(CampTypeBookedResponse::getCampTypeId, CampTypeBookedResponse::getBookedQuantity));

            for (CampTypeResponse response : campTypeResponses) {
                long booked = bookedQuantityMap.getOrDefault(response.getId(), 0L);
                response.setRemainQuantity(response.getQuantity() - booked);
            }
        } else {
            for (CampTypeResponse response : campTypeResponses) {
                response.setRemainQuantity((long) response.getQuantity());
            }
        }



        return campTypeResponses;
    }

}
