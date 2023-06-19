package de.materna.date4u.core.service;

import de.materna.date4u.core.persistence.PhotoRepository;
import de.materna.date4u.core.entity.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;
    private final FileSystemService fileSystemService;
    private final ProfileService profileService;

    public PhotoService(PhotoRepository photoRepository, FileSystemService fileSystemService, ProfileService profileService) {
        this.photoRepository = photoRepository;
        this.fileSystemService = fileSystemService;
        this.profileService = profileService;
    }

    public List<Photo> findPhotosByProfileId(Long id) {
        return photoRepository.findPhotosByProfileId(id);
    }

    public Optional<byte[]> download(String name) {
        try {
            return Optional.of(fileSystemService.load(name + ".jpg"));
        } catch (UncheckedIOException e) {
            logger.warn("Error in loading photo " + name);
            return Optional.empty();
        }
    }

    public Optional<Photo> upload(byte[] imageBytes, boolean isProfilePhoto) {
        List<String> photoNames = photoRepository.findNames();
        String imageName = "unicorn" + FileName.getCountAsString(FileName.getMaxFileCount(photoNames) + 1);

        try {
            fileSystemService.store(imageName + ".jpg", imageBytes);
            logger.info("Saved " + imageName);
            return Optional.of(new Photo(imageName, isProfilePhoto));
        } catch (UncheckedIOException e) {
            logger.warn("Error in saving photo " + imageName);
            return Optional.empty();
        }

    }

    public Optional<Photo> findPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    public Optional<Photo> findProfilePhotoById(Long id) {
        return photoRepository.findProfilePhotoByProfileId(id);
    }

    public void deletePhotoById(Long id, String nickname) {
        Optional<Photo> photo = photoRepository.findById(id);
        Optional<Long> idOptional = profileService.findIdByNickname(nickname);
        if (idOptional.isPresent()) {
            if (photo.isPresent() && photo.get().isProfilePhoto()) {
                setOtherProfilePhoto(idOptional.get(), photo);
            }

            photoRepository.deleteById(id);
            logger.info("Deleted photo with id " + id);
        }
    }

    private void setOtherProfilePhoto(Long profileId, Optional<Photo> photo) {
        List<Photo> photos = photoRepository.findPhotosByProfileId(profileId);

        Optional<Photo> first = photos.stream().filter(p -> !p.equals(photo.get())).findFirst();
        if (first.isPresent()) {
            Photo entity = first.get();
            entity.setProfilePhoto(true);
            photoRepository.save(entity);
        }
    }

    public void setPhotoAsProfilePhoto(Long photoId, String nickname) {
        Optional<Photo> currentProfilePhotoOptional = profileService.findIdByNickname(nickname).flatMap(
                photoRepository::findProfilePhotoByProfileId);
        Optional<Photo> futureProfilePhotoOptional = photoRepository.findById(photoId);

        if (currentProfilePhotoOptional.isPresent() && futureProfilePhotoOptional.isPresent()) {
            Photo currentProfilePhoto = currentProfilePhotoOptional.get();
            Photo futureProfilePhoto = futureProfilePhotoOptional.get();

            if (currentProfilePhoto.equals(futureProfilePhoto)) {
                return;
            }

            currentProfilePhoto.setProfilePhoto(false);
            futureProfilePhoto.setProfilePhoto(true);

            photoRepository.save(currentProfilePhoto);
            photoRepository.save(futureProfilePhoto);
            logger.info("Unset " + currentProfilePhoto + " as profile photo");
            logger.info("Set " + futureProfilePhoto + " as profile photo");
        }
    }

    public static class FileName {
        public static int getMaxFileCount(List<String> photoNames) {
            return photoNames.stream()
                    .max(Comparator.naturalOrder())
                    .map(FileName::extractNumber)
                    .map(Integer::parseInt)
                    .orElse(1);
        }

        private static String extractNumber(String stringWithNumber) {
            StringBuilder sb = new StringBuilder();
            stringWithNumber.chars().filter(Character::isDigit).mapToObj(c -> (char) c).forEach(sb::append);
            return sb.toString();
        }

        public static String getCountAsString(int count) {
            StringBuilder numberString = new StringBuilder(String.valueOf(count));

            while (numberString.length() < 3) {
                numberString.insert(0, "0");
            }

            return numberString.toString();
        }
    }
}
