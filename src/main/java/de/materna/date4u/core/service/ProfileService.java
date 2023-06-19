package de.materna.date4u.core.service;

import de.materna.date4u.core.entity.Photo;
import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.core.filter.ProfileSpecification;
import de.materna.date4u.core.filter.SearchFilter;
import de.materna.date4u.core.persistence.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> search(SearchFilter searchFilter) {
        if(searchFilter.isValidDate() && searchFilter.isValidHornlength()){
            logger.info("Searched with valid criteria date and valid hornlength");
            logger.info("Returned by criteria " + searchFilter);
            return profileRepository.findAll(ProfileSpecification.betweenAge(searchFilter)
                    .and(ProfileSpecification.betweenHornlength(searchFilter)));
        } else if( searchFilter.isValidDate()) {
            logger.info("Searched with valid criteria date");
            logger.info("Returned by criteria " + searchFilter);
            return profileRepository.findAll(ProfileSpecification.betweenAge(searchFilter));
        } else if(searchFilter.isValidHornlength()){
            logger.info("Searched with valid hornlength");
            logger.info("Returned by criteria " + searchFilter);
            return profileRepository.findAll(ProfileSpecification.betweenHornlength(searchFilter));
        } else {
            logger.info("Searched with no valid criteria");
            logger.info("Returned all");
            return profileRepository.findAll();
        }
    }

    public Profile save(Profile profile, Optional<Photo> photoOptional){
        profile.setPhotos(photoOptional.map(Set::of).orElse(Collections.emptySet()));
        prepareForSave(profile);
        return profileRepository.save(profile);
    }

    private void prepareForSave(Profile profile) {
        if(Objects.isNull(profile.getId())) {
            profileRepository.findIdByNickname(profile.getNickname()).ifPresent(profile::setId);
        }
        if(profile.getUnicorn().isInvalid()){
            profile.setUnicornToNull();
        }
        if(Objects.isNull(profile.getUnicorn().getProfile())){
            profile.getUnicorn().setProfile(profile);
        }
        profile.getPhotos().stream().filter(p -> p.getProfile() == null).forEach(p -> p.setProfile(profile));
    }

    public void updateLastSeenByNickname(Long id){
        profileRepository.updateLastSeenByNickname(id);
    }

    public Optional<Long> findIdByNickname(String nickname) {
        return profileRepository.findIdByNickname(nickname);
    }

    public Optional<Profile> findProfileByNickname(String nickname) {
        return profileRepository.findByNickname(nickname);
    }
}
