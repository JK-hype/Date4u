package de.materna.date4u.infrastructure.configuration;

import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.core.entity.Unicorn;
import de.materna.date4u.core.persistence.ProfileRepository;
import de.materna.date4u.core.persistence.UnicornRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    UserDetailsService userDetailsService(
            UnicornRepository unicornRepository,
            ProfileRepository profileRepository) {
        return identifier -> profileRepository.findByNickname(identifier)
                .or(() -> unicornRepository.findByEmail(identifier)
                        .map(Unicorn::getProfile))
                .map(profile -> User.withUsername(profile.getNickname())
                        .password(profile.getUnicorn().getPassword())
                        .roles("USER").build())
                .orElseThrow(() -> new UsernameNotFoundException(identifier));

    }
}
