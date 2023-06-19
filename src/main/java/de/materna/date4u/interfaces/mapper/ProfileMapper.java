package de.materna.date4u.interfaces.mapper;

import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.interfaces.formData.ProfileFormData;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProfileMapper {

    @Mapping(target = "photos", ignore = true)
    Profile mapProfileFormDataToProfile(ProfileFormData profileFormData);

    @Mapping(target = "photos", expression = "java(profile.getPhotos().stream().map(p -> p.getId()).toList())")
    ProfileFormData mapProfileToProfileFormData(Profile profile);
}
