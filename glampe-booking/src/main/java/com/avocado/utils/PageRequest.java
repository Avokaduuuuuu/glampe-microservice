package com.avocado.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private Integer currentPage;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
}
