package de.materna.date4u.interfaces.formData;

public class UnicornFormData {

    private Long id;
    private String email;
    private String password;

    public UnicornFormData() {
    }

    public UnicornFormData(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UnicornFormData{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
