package com.avocado.placetype;

import com.avocado.placetype.dto.resp.PlaceTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceTypeServiceImpl implements PlaceTypeService {

    private final PlaceTypeRepository placeTypeRepository;

    @Override
    public List<PlaceTypeResponse> fetchAll() {
        return placeTypeRepository.findAll()
                .stream().map(PlaceTypeMapper.INSTANCE::toResponse).toList();
    }
}
