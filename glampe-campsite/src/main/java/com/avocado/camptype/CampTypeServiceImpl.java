package com.avocado.camptype;

import com.avocado.camptype.dto.resp.CampTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampTypeServiceImpl implements CampTypeService {

    private final CampTypeRepository campTypeRepository;

    @Override
    public Map<Long, CampTypeResponse> getCampTypesByIds(List<Long> ids) {
        return campTypeRepository.findAllByIdIn(ids)
                .stream()
                .collect(Collectors.toMap(CampTypeEntity::getId, CampTypeMapper.INSTANCE::toResponse));
    }
}
