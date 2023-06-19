package de.materna.date4u.core.filter;

import de.materna.date4u.core.entity.Profile;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {

    public static Specification<Profile> betweenAge(SearchFilter searchFilter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("birthdate"), searchFilter.getMinDate(), searchFilter.getMaxDate());
    }

    public static Specification<Profile> betweenHornlength(SearchFilter searchFilter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("hornlength"), searchFilter.getMinHornlength(), searchFilter.getMaxHornlength());
    }
}
