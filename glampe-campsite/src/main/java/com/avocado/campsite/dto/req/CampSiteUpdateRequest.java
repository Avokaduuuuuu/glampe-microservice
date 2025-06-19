package com.avocado.campsite.dto.req;

import com.avocado.campsite.CampSiteEntity;
import com.avocado.campsite.enums.CampSiteStatusEnum;

import java.util.List;

public record CampSiteUpdateRequest(
        String name,
        String address,
        String city,
        Double latitude,
        Double longitude,
        Double depositRate,
        String description,
        CampSiteStatusEnum status
) {

    public CampSiteEntity toModifiedCampSite(CampSiteEntity campSiteEntity){
        if (name != null) campSiteEntity.setName(name);
        if (address != null) campSiteEntity.setAddress(address);
        if (city != null) campSiteEntity.setCity(city);
        if (latitude != null) campSiteEntity.setLatitude(latitude);
        if (longitude != null) campSiteEntity.setLongitude(longitude);
        if (depositRate != null) campSiteEntity.setDepositRate(depositRate);
        if (description != null) campSiteEntity.setDescription(description);
        if (status != null) campSiteEntity.setStatus(status);
        return campSiteEntity;
    }
}
