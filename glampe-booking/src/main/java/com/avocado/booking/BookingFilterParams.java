package com.avocado.booking;

import com.avocado.utils.PageRequest;
import com.avocado.utils.PageResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingFilterParams extends PageRequest {
    Long userId;
    Long campSiteId;
    String status;

    public Specification<BookingEntity> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) predicates.add(cb.equal(root.get("userId"), userId));
            if (campSiteId != null) predicates.add(cb.equal(root.get("campSiteId"), campSiteId));
            if (status != null) predicates.add(cb.equal(root.get("status"), status));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
