package com.example.portal2.ui.registration;

import android.support.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class RegistrationResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    RegistrationResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistrationResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
