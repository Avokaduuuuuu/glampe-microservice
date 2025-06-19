package com.avocado.campsite;

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
public class CampSiteFilterParams extends PageResponse {
    private String name;
    private String address;
    private String city;
    private String status;

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
                predicates.add(cb.like(root.get("status"), "%" + status + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
