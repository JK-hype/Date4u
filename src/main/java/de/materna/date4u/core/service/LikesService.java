package de.materna.date4u.core.service;

import de.materna.date4u.core.entity.Likes;
import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.core.persistence.LikesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LikesService {

    private static final Logger logger = LoggerFactory.getLogger(LikesService.class);

    private final LikesRepository likesRepository;
    private final ProfileService profileService;

    public LikesService(LikesRepository likesRepository, ProfileService profileService) {
        this.likesRepository = likesRepository;
        this.profileService = profileService;
    }

    public List<Profile> findLiker(String likeeNickname) {
        Optional<Long> likeeIdOptional = profileService.findIdByNickname(likeeNickname);
        return likeeIdOptional
                .map(id -> likesRepository.findByLikee(id).stream()
                        .map(Likes::getLiker)
                        .toList())
                .orElse(Collections.emptyList());
    }

    public boolean toggleLike(String likerNickname, String likeeNickname) {

        Optional<Likes> likes;
        Optional<Profile> liker = profileService.findProfileByNickname(likerNickname);
        Optional<Profile> likee = profileService.findProfileByNickname(likeeNickname);
        if (liker.isPresent() && likee.isPresent()) {
            likes = likesRepository.findByLikerAndLikee(liker.get().getId(), likee.get().getId());
            if (likes.isPresent()) {
                likesRepository.delete(likes.get());
                logger.info("Deleted " + likes);
            } else {
                Likes newLikes = new Likes(liker.get(), likee.get());
                likesRepository.save(newLikes);
                logger.info("Saved " + newLikes);
            }
        } else {
            logger.warn("Could not find Likes with liker: " + likerNickname + " and likee: " + likeeNickname);
            return false;
        }

        return true;
    }

    public boolean existsLikerandLikee(String likerNickname, String likeeNickname) {
        Optional<Long> likerId = profileService.findIdByNickname(likerNickname);
        Optional<Long> likeeId = profileService.findIdByNickname(likeeNickname);
        if (likerId.isPresent() && likeeId.isPresent()) {
            return likesRepository.findByLikerAndLikee(likerId.get(), likeeId.get()).isPresent();
        }
        return false;
    }
}
