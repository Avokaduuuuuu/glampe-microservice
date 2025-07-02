package com.avocado.selection;

import com.avocado.exception.CampSiteException;
import com.avocado.exception.ResultCode;
import com.avocado.selection.dto.req.SelectionUpdateRequest;
import com.avocado.selection.dto.resp.SelectionResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SelectionServiceImpl implements SelectionService {

    private final SelectionRepository selectionRepository;

    @Override
    public SelectionResponse getById(Long id) {
        return SelectionMapper.INSTANCE.toResponse(
                selectionRepository.findById(id)
                        .orElseThrow(() -> new CampSiteException(ResultCode.SELECTION_NOT_FOUND))
        );
    }

    @Override
    public SelectionResponse update(SelectionUpdateRequest request, Long id) {
        return SelectionMapper.INSTANCE.toResponse(
                selectionRepository.save(
                        request.toModifiedEntity(selectionRepository
                                .findById(id)
                                .orElseThrow(() -> new CampSiteException(ResultCode.SELECTION_NOT_FOUND))
                        )
                )
        );
    }

    @Override
    public Map<Long, SelectionResponse> batch(List<Long> ids) {
        return selectionRepository.findAllByIdIn(ids)
                .stream().collect(Collectors.toMap(SelectionEntity::getId, SelectionMapper.INSTANCE::toResponse));
    }
}
