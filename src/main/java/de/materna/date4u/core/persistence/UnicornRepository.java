package de.materna.date4u.core.persistence;

import de.materna.date4u.core.entity.Unicorn;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnicornRepository extends JpaRepository<Unicorn, Long> {
    Optional<Unicorn> findByEmail(String email);
}
