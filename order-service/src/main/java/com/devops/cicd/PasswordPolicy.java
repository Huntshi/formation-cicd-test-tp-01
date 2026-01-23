package com.devops.cicd;

import java.util.regex.Pattern;

public class PasswordPolicy {

    private static final Pattern STRONG_PASSWORD =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,24}$");

    public static boolean isStrong(String password) {
        if (password == null) {
            return false;
        }
        return STRONG_PASSWORD.matcher(password).matches();
    }
}
