package de.materna.date4u.core.persistence;

import de.materna.date4u.core.entity.Likes;
import de.materna.date4u.core.entity.LikesId;
import de.materna.date4u.core.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    @Query("SELECT l FROM Likes l WHERE l.liker.id = :likerId AND l.likee.id = :likeeId")
    Optional<Likes> findByLikerAndLikee(Long likerId, Long likeeId);

    @Query("SELECT l FROM Likes l WHERE l.likee.id = :likeeId")
    List<Likes> findByLikee(Long likeeId);
}
