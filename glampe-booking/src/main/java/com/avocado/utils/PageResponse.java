package com.avocado.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private Integer currentPage;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
    private Long totalRecords;
    private Integer totalPages;
    private List<T> records;
}
