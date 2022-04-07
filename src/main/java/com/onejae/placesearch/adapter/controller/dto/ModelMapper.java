package com.onejae.placesearch.adapter.controller.dto;

import com.onejae.placesearch.adapter.repository.KeywordData;
import com.onejae.placesearch.usecase.dto.PlaceData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    KeywordResponseData keywordDataToKeywordResponseData(KeywordData keywordData);
    PlaceResponseData placeDataToPlaceResponseData(PlaceData placeData);
}
