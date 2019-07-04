package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz.R;

public class LoginUtils {

    Activity activity;
    Context context;

    public LoginUtils(Activity activity, Context context) {this.activity = activity;
        this.context = context;}

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

    public void updateUiWithUser(String username) {
        String welcome = context.getResources().getString(R.string.welcome) + " " +username+ "!";
        Toast.makeText(context, welcome, Toast.LENGTH_LONG).show();
    }

    public void showFailedAlert(AlertDialog.Builder myAlert){
        myAlert.setMessage("Unexpected error!")
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
