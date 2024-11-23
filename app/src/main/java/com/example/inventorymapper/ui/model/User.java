package com.example.inventorymapper.ui.model;

public class User {
    private String id;
    private String username;
    private String nickname;
    private boolean admin;  /* As in "admin for the current house shown */

    // TODO: private constuctors with id and from (UserDB) method
    public User(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public User(String username, String nickname, boolean admin) {
        this.username = username;
        this.nickname = nickname;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
