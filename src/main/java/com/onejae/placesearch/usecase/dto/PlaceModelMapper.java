package com.onejae.placesearch.usecase.dto;

import com.onejae.placesearch.domain.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlaceModelMapper {
    PlaceModelMapper INSTANCE = Mappers.getMapper(PlaceModelMapper.class);

    PlaceData placeToPlaceData(Place place);
}
