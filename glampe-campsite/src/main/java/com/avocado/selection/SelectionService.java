package com.avocado.selection;

import com.avocado.selection.dto.req.SelectionUpdateRequest;
import com.avocado.selection.dto.resp.SelectionResponse;

import java.util.List;
import java.util.Map;

public interface SelectionService {
    SelectionResponse getById(Long id);
    SelectionResponse update(SelectionUpdateRequest request, Long id);

    Map<Long, SelectionResponse> batch(List<Long> ids);
}
