package com.example.quiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Register extends AppCompatActivity {

    String urlAddress="https://quiz-backend-1.appspot.com/users/register/";
    EditText usernameEditText,passwordEditText,emailEditText;
    Button registerButton;
    String username, password, email;
    Integer response = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = (EditText) findViewById(R.id.register_username);
        passwordEditText = (EditText) findViewById(R.id.register_password);
        emailEditText = (EditText) findViewById(R.id.register_email);
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//START ASYNC TASK
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            URL registerEndpoint = new URL(urlAddress);

                            username = usernameEditText.getText().toString();
                            password = passwordEditText.getText().toString();
                            email = emailEditText.getText().toString();

// Create connection
                            HttpsURLConnection myConnection =
                                    (HttpsURLConnection) registerEndpoint.openConnection();
                            myConnection.setRequestMethod("POST");

                            myConnection.setRequestProperty("user", username);
                            myConnection.setRequestProperty("password", password);
                            myConnection.setRequestProperty("mail", email);

                            response = myConnection.getResponseCode();

                        } catch (MalformedURLException e) {
                            //bad  URL, tell the user
                        } catch (IOException e) {
                            //network error/ tell the user
                        }
                    }
                });
                if (response == 200) {
                    // Success
                    // Further processing here
                } else {
                    showSuccessAlert();

                }
            }

        });
    }
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void showSuccessAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(false);
        myAlert.setMessage("Success!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToLoginScreen();
                    }
                })
                .create();
        myAlert.show();
    }
    public void showFailedAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Fail! Please provide correct data!")
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