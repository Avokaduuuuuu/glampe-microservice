package com.avocado.camptype;

import com.avocado.camptype.dto.resp.CampTypeResponse;

import java.util.List;
import java.util.Map;

public interface CampTypeService {
    Map<Long, CampTypeResponse> getCampTypesByIds(List<Long> ids);
}
