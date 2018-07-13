package com.jdqm.androidunittest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jdqm on 2018-7-13.
 */
public class EmailValidator {

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }
}
