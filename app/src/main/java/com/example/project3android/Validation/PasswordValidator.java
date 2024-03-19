package com.example.project3android.Validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PasswordValidator implements TextWatcher {
    private EditText passwordEditText;
    private Validation validator;

    public PasswordValidator(EditText passwordEditText, Validation validator) {
        this.passwordEditText = passwordEditText;
        this.validator = validator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputText = s.toString();
        if (!validator.isValidPass(inputText)) {
            passwordEditText.setError("Minimum of 8 characters including 1 number, 1 uppercase," +
                    " 1 lowercase and 1 special character required");
        } else {
            passwordEditText.setError(null);
        }
    }
}

