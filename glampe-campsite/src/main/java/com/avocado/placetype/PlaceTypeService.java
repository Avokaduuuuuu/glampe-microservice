package com.avocado.placetype;

import com.avocado.placetype.dto.resp.PlaceTypeResponse;

import java.util.List;

public interface PlaceTypeService {

    List<PlaceTypeResponse> fetchAll();
}
