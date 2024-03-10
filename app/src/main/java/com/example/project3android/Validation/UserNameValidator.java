package com.example.project3android.Validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.project3android.Validation.Validation;

public class UserNameValidator implements TextWatcher {
    private EditText userName;
    private Validation validator;
    public UserNameValidator(EditText userNameEditText, Validation validator) {
        this.userName = userNameEditText;
        this.validator = validator;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }
    @Override
    public void afterTextChanged(Editable editable) {
        // validate the input using regex
        String inputText = editable.toString();
        // check validity in username validation function
        if (!validator.isValidUN(inputText)) {
            // if invalid - print error
            userName.setError("Minimum 1 letter");
        } else {
            userName.setError(null);
        }
    }
}
