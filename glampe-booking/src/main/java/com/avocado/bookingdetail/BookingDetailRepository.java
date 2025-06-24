package com.avocado.bookingdetail;

import com.avocado.bookingdetail.dto.resp.BookingDetailAvailabilityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Long> {

    @Query("SELECT bd.campTypeId as campTypeId, COUNT(bd.id) as bookedQuantity " +
            "FROM BookingDetailEntity bd " +
            "JOIN bd.booking b " +
            "WHERE bd.campTypeId IN :campTypeIds " +
            "AND (DATE(b.checkInAt) < DATE(:checkOutAt) AND DATE(b.checkOutAt) > DATE(:checkInAt)) " +
            "GROUP BY bd.campTypeId")
    List<Object[]> findBookedBookingsDetail(
            @Param("campTypeIds") List<Long> campTypeIds,
            @Param("checkInAt") LocalDate checkInAt,
            @Param("checkOutAt") LocalDate checkOutAt);
}
