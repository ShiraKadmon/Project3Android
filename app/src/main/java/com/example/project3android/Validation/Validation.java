package com.example.project3android.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public boolean isValidUN(String input) {
        // define the regex pattern for at least 1 characters
        // input must include: 1 letter
        String regex = ".*[a-zA-Z].*";
        // compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // create a matcher with the input string
        Matcher matcher = pattern.matcher(input);
        // check if the input matches the pattern
        return matcher.matches();
    }

    public boolean isValidPass(String input) {
        // define the regex pattern for at least 8 characters
        // input must include: 1 number, 1 lowercase, 1 uppercase, 1 special character
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]).{8,}$";
        // compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // create a matcher with the input string
        Matcher matcher = pattern.matcher(input);
        // check if the input matches the pattern
        return matcher.matches();
    }
}
