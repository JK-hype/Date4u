package de.materna.date4u.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "profile_fk")
    private Profile profile;
    @NotNull
    @Pattern(regexp = "[\\w_-]{1,200}")
    private String name = "";
    private boolean isProfilePhoto;
    @NotNull
    @Past
    private LocalDateTime created = LocalDateTime.now();

    public Photo(Profile profile, @NotNull String name, boolean isProfilePhoto, @NotNull LocalDateTime created) {
        this.profile = profile;
        this.name = name;
        this.isProfilePhoto = isProfilePhoto;
        this.created = created;
    }

    public Photo(@NotNull String name, boolean isProfilePhoto) {
        this.name = name;
        this.isProfilePhoto = isProfilePhoto;
    }

    protected Photo() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isProfilePhoto() {
        return isProfilePhoto;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfilePhoto(boolean profilePhoto) {
        isProfilePhoto = profilePhoto;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isProfilePhoto=" + isProfilePhoto +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (isProfilePhoto != photo.isProfilePhoto) return false;
        if (!Objects.equals(id, photo.id)) return false;
        if (!Objects.equals(profile, photo.profile)) return false;
        if (!name.equals(photo.name)) return false;
        return created.equals(photo.created);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (isProfilePhoto ? 1 : 0);
        result = 31 * result + created.hashCode();
        return result;
    }
}