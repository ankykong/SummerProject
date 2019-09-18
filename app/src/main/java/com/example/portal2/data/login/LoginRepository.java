package com.example.portal2.data.login;

import com.example.portal2.data.login.model.LoggedInUser;

import java.io.IOException;
import java.util.HashMap;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private HashMap<String, LoggedInUser> userMap = new HashMap<String, LoggedInUser>();

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public Result<LoggedInUser> checkPassword( String email, String password ){
        try{
            boolean test = userMap.get(email).checkPasswordEqual(password);
            if(test) {
                return new Result.Success(userMap.get(email));
            }
        }
        catch(Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
        return new Result.Error(new Exception("Username or password is incorrect"));
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = checkPassword(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public Result<LoggedInUser> createUser(String email, LoggedInUser user){
        if(userExists(email)){
            return new Result.Error(new Exception("User already exists"));
        }
        userMap.put(email, user);
        return new Result.Success<LoggedInUser>(user);
    }

    public boolean userExists(String email){
        if( userMap == null || userMap.isEmpty() ){
            return false;
        }
        return userMap.containsKey(email);
    }
}
