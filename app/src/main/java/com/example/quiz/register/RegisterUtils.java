package com.example.quiz.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.quiz.R;
import com.example.quiz.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUtils {

    Register register;
    Activity activity;
    Context context;

    public RegisterUtils(Activity activity, Context context) {this.activity = activity;
    this.context = context;}

    public void showSuccessAlert(AlertDialog.Builder myAlert){

        myAlert.setCancelable(false);
        myAlert.setMessage("Success!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        register.goToLoginScreen();
                    }
                })
                .create();
        myAlert.show();
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
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher.matches());
        return matcher.matches();
    }

    public Boolean checkRegister(EditText usernameEditText,
                                  EditText passwordEditText, EditText emailEditText) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError(context.getResources().getString(R.string.invalid_username));
            usernameEditText.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(password) || password.length() < 5) {
            passwordEditText.setError(context.getResources().getString(R.string.invalid_password));
            passwordEditText.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(email) || isEmailValid(email) == false){
            emailEditText.setError(context.getResources().getString(R.string.invalid_email));
            emailEditText.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }
}
