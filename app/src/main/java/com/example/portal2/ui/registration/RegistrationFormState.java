package com.example.portal2.ui.registration;

import android.support.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegistrationFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer displayNameError;
    private boolean isDataValid;

    RegistrationFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer displayNameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.displayNameError = displayNameError;
        this.isDataValid = false;
    }

    RegistrationFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.displayNameError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getDisplayNameError() {
        return displayNameError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
