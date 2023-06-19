package de.materna.date4u.core.service;

import de.materna.date4u.core.entity.Unicorn;
import de.materna.date4u.core.persistence.UnicornRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicornService {

    private final UnicornRepository unicornRepository;

    public UnicornService(UnicornRepository unicornRepository) {
        this.unicornRepository = unicornRepository;
    }

    public Unicorn saveUnicorn(Unicorn unicorn) {
        return unicornRepository.save(unicorn);
    }
}
