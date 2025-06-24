package com.avocado.camptype;

import com.avocado.campsite.CampSiteEntity;
import com.avocado.utils.PageRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampTypeFilterParams{
    private Long campSiteId;
    private LocalDate checkInAt;
    private LocalDate checkOutAt;

    public Specification<CampTypeEntity> buildSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (campSiteId != null) {
                Join<CampTypeEntity, CampSiteEntity> campSiteJoin = root.join("campSite", JoinType.INNER);
                predicates.add(cb.equal(campSiteJoin.get("id"), campSiteId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}