package de.materna.date4u.core.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Unicorn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToOne
    @JoinColumn(name = "profile_fk")
    private Profile profile;

    protected Unicorn() {
    }

    public Unicorn(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean isInvalid() {
        boolean isNull = Objects.isNull(email) || Objects.isNull(password);
        return isNull || (email.isBlank() || password.isBlank());
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Unicorn{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
