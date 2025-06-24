package com.avocado.camptype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CampTypeRepository extends JpaRepository<CampTypeEntity, Long>, JpaSpecificationExecutor<CampTypeEntity> {
    List<CampTypeEntity> findAllByIdIn(List<Long> ids);

    List<Long> id(Long id);
}
