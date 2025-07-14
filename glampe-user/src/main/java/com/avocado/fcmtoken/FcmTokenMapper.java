package com.avocado.fcmtoken;

import com.avocado.fcmtoken.dto.resp.FcmTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FcmTokenMapper {
    FcmTokenMapper INSTANCE = Mappers.getMapper(FcmTokenMapper.class);

    @Mapping(target = "userId", source = "user.id")
    FcmTokenResponse toResponse(FcmTokenEntity entity);
}
