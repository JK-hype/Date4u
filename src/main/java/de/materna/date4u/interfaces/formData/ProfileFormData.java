package de.materna.date4u.interfaces.formData;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProfileFormData {

    private Long id;
    private String nickname;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate birthdate;
    private int hornlength;
    private int gender;
    private Integer attractedToGender;
    private String description;
    private LocalDateTime lastseen;
    private List<Long> photos = new ArrayList<>();
    private UnicornFormData unicorn = new UnicornFormData();

    public ProfileFormData() { }
    public ProfileFormData(long id, String nickname,
                           LocalDate birthdate, int hornlength, int gender,
                           Integer attractedToGender, String description, LocalDateTime lastseen,
                           List<Long> photos, UnicornFormData unicorn) {
        this.id = id;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.hornlength = hornlength;
        this.gender = gender;
        this.attractedToGender = attractedToGender;
        this.description = description;
        this.lastseen = lastseen;
        this.photos = photos;
        this.unicorn = unicorn;
    }

    public long getId() {
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

    public Integer getAttractedToGender() {
        return attractedToGender;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getLastseen() {
        return lastseen;
    }

    public List<Long> getPhotos() {
        return photos;
    }

    public UnicornFormData getUnicorn() {
        return unicorn;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setHornlength(int hornlength) {
        this.hornlength = hornlength;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAttractedToGender(Integer attractedToGender) {
        this.attractedToGender = attractedToGender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastseen(LocalDateTime lastseen) {
        this.lastseen = lastseen;
    }

    public void setUnicorn(UnicornFormData unicorn) {
        this.unicorn = unicorn;
    }

    public void setPhotos(List<Long> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "ProfileFormData{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", birthdate=" + birthdate +
                ", hornlength=" + hornlength +
                ", gender=" + gender +
                ", attractedToGender=" + attractedToGender +
                ", description='" + description + '\'' +
                ", lastseen=" + lastseen +
                ", photos=" + photos +
                ", unicorn=" + unicorn +
                '}';
    }
}