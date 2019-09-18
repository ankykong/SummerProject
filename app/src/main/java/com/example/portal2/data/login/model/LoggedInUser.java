package com.example.portal2.data.login.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String password;
    private String displayName;

    public LoggedInUser(String userId, String password, String displayName) {
        this.userId = userId;
        this.password = password;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName){
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPasswordEqual(String password){
        return this.password.equals(password);
    }

    public String getDisplayName() {
        return displayName;
    }
}
