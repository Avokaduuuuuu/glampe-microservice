package com.avocado.campsite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface CampSiteRepository extends JpaRepository<CampSiteEntity, Long>, JpaSpecificationExecutor<CampSiteEntity> {
    List<CampSiteEntity> findAllByIdIn(List<Long> ids);


}
