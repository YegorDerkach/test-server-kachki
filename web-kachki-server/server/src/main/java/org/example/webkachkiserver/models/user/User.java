package org.example.webkachkiserver.models.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="user")
@Builder
@Data
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private TYPE type = TYPE.USER;
    private List<Long> boughtCoursesId;
    private String pathToPhoto;
    private String description;
    private Contacts contacts;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public String getDescription() {
        return description;
    }

    public Contacts getContacts() {
        return contacts;
    }
}
