package com.avocado.camptype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampTypeRepository extends JpaRepository<CampTypeEntity, Long> {
    List<CampTypeEntity> findAllByIdIn(List<Long> ids);

    List<Long> id(Long id);
}
