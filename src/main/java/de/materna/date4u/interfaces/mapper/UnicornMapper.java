package de.materna.date4u.interfaces.mapper;

import de.materna.date4u.core.entity.Unicorn;
import de.materna.date4u.interfaces.formData.UnicornFormData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnicornMapper {

    Unicorn mapUnicornFormDataToUnicorn(UnicornFormData unicornFormData);

    UnicornFormData mapUnicornToUnicornFormData(Unicorn unicorn);
}
