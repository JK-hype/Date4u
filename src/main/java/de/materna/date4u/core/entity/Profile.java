package de.materna.date4u.core.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;
    private LocalDate birthdate;
    private short hornlength;
    private byte gender;

    @Column(name = "attracted_to_gender")
    private Byte attractedToGender;
    private String description;
    private LocalDateTime lastseen;

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Photo> photos;

    @OneToOne(mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Unicorn unicorn;

    protected Profile() {
    }

    public Profile(String nickname, LocalDate birthdate, int hornlength,
                   int gender, Integer attractedToGender, String description,
                   Set<Photo> photos, Unicorn unicorn) {
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.description = description;
        setLastseen();
        this.unicorn = unicorn;
        setHornlength(hornlength);
        setGender(gender);
        setAttractedToGender(attractedToGender);
        setPhotos(photos);
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public int getHornlength() {
        return hornlength;
    }

    public int getGender() {
        return gender;
    }

    public @Nullable Integer getAttractedToGender() {
        return attractedToGender == null ? null : attractedToGender.intValue();
    }

    public LocalDateTime getLastseen() {
        return lastseen;
    }

    public String getDescription() {
        return description;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Unicorn getUnicorn() {
        return unicorn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void setHornlength(int hornlength) {
        this.hornlength = (short) hornlength;
    }

    private void setGender(int gender) {
        this.gender = (byte) gender;
    }


    private void setAttractedToGender(@Nullable Integer attractedToGender) {
        this.attractedToGender = attractedToGender == null ? null : attractedToGender.byteValue();
    }

    private void setLastseen() {
        this.lastseen = LocalDateTime.now();
    }

    public void setPhotos(Set<Photo> photos) {
        Set<Photo> orderedPhotos = new TreeSet<>(Comparator.comparing(Photo::isProfilePhoto)
                .reversed()
                .thenComparing(Photo::getCreated));
        if (photos != null) {
            orderedPhotos.addAll(photos);
        }
        this.photos = orderedPhotos;
    }

    public void setUnicornToNull() {
        this.unicorn = null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Profile profile
                && Objects.equals(nickname, profile.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", birthdate=" + birthdate +
                ", hornlength=" + hornlength +
                ", gender=" + gender +
                ", attractedToGender=" + attractedToGender +
                ", description='" + description + '\'' +
                ", lastseen=" + lastseen +
                ", photos=" + photos  +
                ", unicorn=" + unicorn +
                '}';
    }

}