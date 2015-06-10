package org.me.server.model.dto;


import java.util.Objects;

public class User {
//    private Id userId;
    private String username;
    private String password;

    private boolean isActive;


//    public User(String username, String password) {
//        this(/*new Id(), */username, password);
//    }

    public User(/*Id userId, */String username, String password) {
//        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isActive = true;
    }

//    public Id getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Id userId) {
//        this.userId = userId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
//        return Objects.equals(user, user.userId);
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(userId);
        return Objects.hash(username);
    }

//    @Override
//    public User clone() {
//        return new User(username, password);
//    }
}

