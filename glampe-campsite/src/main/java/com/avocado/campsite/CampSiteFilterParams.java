package com.avocado.campsite;

import com.avocado.camptype.CampTypeEntity;
import com.avocado.placetype.PlaceTypeEntity;
import com.avocado.utils.PageRequest;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampSiteFilterParams extends PageRequest {
    private String name;
    private String address;
    private String city;
    private String status;
    private Long placeTypeId;

    public Specification<CampSiteEntity> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (address != null) {
                predicates.add(cb.like(root.get("address"), "%" + address + "%"));
            }
            if (city != null) {
                predicates.add(cb.like(root.get("city"), "%" + city + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (placeTypeId != null) {
                Join<PlaceTypeEntity, CampSiteEntity> join = root.join("placeTypes", JoinType.INNER);
                predicates.add(cb.equal(join.get("id"), placeTypeId));
                query.distinct(true);
            }
            if (getSortBy().equalsIgnoreCase("highest") || getSortBy().equalsIgnoreCase("lowest")) {
                Join<CampTypeEntity, CampSiteEntity> join = root.join("campTypes", JoinType.LEFT);

                query.groupBy(root.get("id"));

                Expression<BigDecimal> priceExpression = getSortBy().equalsIgnoreCase("highest")
                        ? cb.max(join.get("price"))
                        : cb.min(join.get("price"));

                query.orderBy(getSortBy().equalsIgnoreCase("highest")
                ? cb.desc(priceExpression) : cb.asc(priceExpression));

                query.distinct(true);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
