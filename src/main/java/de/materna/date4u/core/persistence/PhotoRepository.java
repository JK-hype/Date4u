package de.materna.date4u.core.persistence;

import de.materna.date4u.core.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.profile.id = :profileId")
    List<Photo> findPhotosByProfileId(Long profileId);

    @Query("SELECT p FROM Photo p WHERE p.profile.id = :profileId AND p.isProfilePhoto = true")
    Optional<Photo> findProfilePhotoByProfileId(Long profileId);

    @Query("SELECT p.name FROM Photo p")
    List<String> findNames();
}
