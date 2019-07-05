package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz.R;

/**
 * The LoginUtils has main methods that are needed for user to make a successful login.
 */
public class LoginUtils {

    Activity activity;
    Context context;

    public LoginUtils(Activity activity, Context context) {this.activity = activity;
        this.context = context;}

    /**
     * Check if the user has provided correct data and display according message on error
     *
     * @param usernameEditText      Username field
     * @param passwordEditText      Password field
     * @return                      return boolean
     */
    public Boolean checkLogin(EditText usernameEditText, EditText passwordEditText) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError(context.getResources().getString(R.string.invalid_username));
            usernameEditText.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(password) || password.length() < 5) {
            passwordEditText.setError(context.getResources().getString(R.string.invalid_password));
            passwordEditText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Display welcome message
     *
     * @param username      User name
     */
    public void updateUiWithUser(String username) {
        String welcome = context.getResources().getString(R.string.welcome) + " " +username+ "!";
        Toast.makeText(context, welcome, Toast.LENGTH_LONG).show();
    }

    /**
     * Display error alert
     *
     * @param myAlert       AlertDialog object passed from the Login class
     */
    public void showFailedAlert(AlertDialog.Builder myAlert){
        myAlert.setMessage("Incorrect data!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }
}
