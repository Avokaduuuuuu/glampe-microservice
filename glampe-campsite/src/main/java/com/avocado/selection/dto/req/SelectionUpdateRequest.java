package com.avocado.selection.dto.req;

import com.avocado.selection.SelectionEntity;

import java.math.BigDecimal;

public record SelectionUpdateRequest(
        String name,
        String description,
        BigDecimal price,
        Boolean isDeleted
) {

    public SelectionEntity toModifiedEntity(SelectionEntity selectionEntity) {
        if(name != null && !name.isEmpty()) selectionEntity.setName(name);
        if(description != null && !description.isEmpty()) selectionEntity.setDescription(description);
        if(price != null) selectionEntity.setPrice(price);
        if (isDeleted != null) selectionEntity.setIsDeleted(isDeleted);
        return selectionEntity;
    }
}
