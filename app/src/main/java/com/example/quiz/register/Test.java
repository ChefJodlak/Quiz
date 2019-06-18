/*package com.example.quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Registeasr1 extends AppCompatActivity {

    Context c;
    String urlAddress="http://10.0.2.2/android/poster.php";
    EditText usernameEditText,passwordEditText,emailEditText;
    Button registerButton;
    String username, password, email;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEditText = (EditText) findViewById(R.id.register_username);
        passwordEditText = (EditText) findViewById(R.id.register_password);
        emailEditText = (EditText) findViewById(R.id.register_email);
        registerButton = (Button) findViewById(R.id.register_button);

        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        email = emailEditText.getText().toString();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//START ASYNC TASK
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            URL githubEndpoint = new URL("https://api.github.com/");

// Create connection
                            HttpsURLConnection myConnection =
                                    (HttpsURLConnection) githubEndpoint.openConnection();
                            myConnection.setRequestMethod("POST");

                            myConnection.setRequestProperty("user", username);
                            myConnection.setRequestProperty("password", password);
                            myConnection.setRequestProperty("mail", email);

                            if (myConnection.getResponseCode() == 200) {
                                // Success
                                // Further processing here
                            } else {
                                goToLoginScreen();
                            }

                        } catch (MalformedURLException e) {
                            //bad  URL, tell the user
                        } catch (IOException e) {
                            //network error/ tell the user
                        }

                    }
                });
            }

        });
    }
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}*/



