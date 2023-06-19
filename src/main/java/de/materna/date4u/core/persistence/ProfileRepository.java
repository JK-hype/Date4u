package de.materna.date4u.core.persistence;

import de.materna.date4u.core.entity.Profile;
import io.micrometer.observation.ObservationFilter;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {

    @Query("SELECT p.nickname, p.id FROM Profile p")
    List<Tuple> findNicknamesAndIds();

    @Query("UPDATE Profile p SET p.lastseen = CURRENT_TIMESTAMP WHERE p.id = :id")
    void updateLastSeenByNickname(Long id);

    @Query("SELECT p.id FROM Profile p WHERE p.nickname = :nickname")
    Optional<Long> findIdByNickname(String nickname);

    Optional<Profile> findByNickname(String nickname);
}
