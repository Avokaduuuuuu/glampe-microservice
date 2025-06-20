package com.avocado.campsite;

import com.avocado.campsite.dto.req.CampSiteCreateRequest;
import com.avocado.campsite.dto.req.CampSiteUpdateRequest;
import com.avocado.campsite.dto.resp.CampSiteResponse;
import com.avocado.utils.PageResponse;

import java.util.List;
import java.util.Map;

public interface CampSiteService {

    CampSiteResponse getCampSiteById(Long id);
    PageResponse<CampSiteResponse> getAll(CampSiteFilterParams filterParams);
    CampSiteResponse addCampSite(CampSiteCreateRequest request);
    CampSiteResponse updateCampSite(CampSiteUpdateRequest request, Long id);

    Map<Long, CampSiteResponse> fetchBatch(List<Long> ids);
}
