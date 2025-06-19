package com.avocado.selection;

import com.avocado.selection.dto.req.SelectionUpdateRequest;
import com.avocado.selection.dto.resp.SelectionResponse;

public interface SelectionService {
    SelectionResponse getById(Long id);
    SelectionResponse update(SelectionUpdateRequest request, Long id);
}
