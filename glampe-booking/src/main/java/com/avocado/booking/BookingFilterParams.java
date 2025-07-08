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
    List<String> statusList;

    public Specification<BookingEntity> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) predicates.add(cb.equal(root.get("userId"), userId));
            if (campSiteId != null) predicates.add(cb.equal(root.get("campSiteId"), campSiteId));
            if (statusList != null) predicates.add(root.get("status").in(statusList));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
