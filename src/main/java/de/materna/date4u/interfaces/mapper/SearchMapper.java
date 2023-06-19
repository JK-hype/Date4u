package de.materna.date4u.interfaces.mapper;

import de.materna.date4u.core.filter.SearchFilter;
import de.materna.date4u.interfaces.formData.SearchFormData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchMapper {
    @Mapping(target = "maxDate", source = "searchFormData.minAge")
    @Mapping(target = "minDate", source = "searchFormData.maxAge")
    SearchFilter mapSearchFormDataToSearchFilter(SearchFormData searchFormData);
}
