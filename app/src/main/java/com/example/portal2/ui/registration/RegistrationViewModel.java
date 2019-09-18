package com.example.portal2.ui.registration;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.portal2.R;
import com.example.portal2.data.login.LoginRepository;
import com.example.portal2.data.login.Result;
import com.example.portal2.data.login.model.LoggedInUser;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegistrationViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<RegistrationFormState> getRegistrationFormState() {
        return registrationFormState;
    }

    LiveData<RegistrationResult> getRegistrationResult() {
        return registrationResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            registrationResult.setValue(new RegistrationResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            registrationResult.setValue(new RegistrationResult(R.string.registration_failed));
        }
    }

    public LoggedInUser register(String email, String password, String displayName) {

        LoggedInUser user = new LoggedInUser(email, password, displayName);
        Result<LoggedInUser> result = loginRepository.createUser(email, user);
        if( result instanceof Result.Success ) {
            registrationResult.setValue(new RegistrationResult(new LoggedInUserView(user.getDisplayName())));
            return user;
        }
        registrationResult.setValue(new RegistrationResult(R.string.registration_failed));
        return null;
    }

    public void registrationDataChanged(String username, String password, String displayName) {
        if (!isUserNameValid(username)) {
            registrationFormState.setValue(new RegistrationFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            registrationFormState.setValue(new RegistrationFormState(null, R.string.invalid_password, null));
        } else if(!isDisplayValid(displayName)){
            registrationFormState.setValue(new RegistrationFormState(null, null, R.string.invalid_displayName));
        } else {
            registrationFormState.setValue(new RegistrationFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        if (username.contains("@") && username.contains(".com")) {
            if( (loginRepository.userExists(username)) ){
                return false;
            }
            return true;
        }
        return false;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isDisplayValid(String displayName) {
        return displayName != null && displayName.trim().length() > 2;
    }
}
