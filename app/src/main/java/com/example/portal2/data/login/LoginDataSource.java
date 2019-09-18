package com.example.portal2.data.login;

import com.example.portal2.data.login.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    LoginRepository repo;

    public Result<LoggedInUser> login(String email, String password) {
        return repo.checkPassword(email, password);
    }

    public void logout() {
        repo.logout();
    }

    public Result<LoggedInUser> register(String username, String password) {
        try {
            LoggedInUser newUser = new LoggedInUser(username, password);
            return new Result.Success<>(newUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
}
