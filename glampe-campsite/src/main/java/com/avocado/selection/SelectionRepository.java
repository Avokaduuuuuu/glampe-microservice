package com.avocado.selection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectionRepository extends JpaRepository<SelectionEntity, Long> {
    List<SelectionEntity> findAllByIdIn(List<Long> ids);
}
