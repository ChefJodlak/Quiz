package com.example.quiz;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.quiz.login.LogInUser;
import com.example.quiz.login.LoginUtils;
import com.example.quiz.utilities.ViewDialog;

/**
 * The Login class is made for checking if the user has provided a correct data and to try to
 * Log In a User.
 */
public class Login extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    String username, password;
    ViewDialog viewDialog;
    LoginUtils loginUtils;
    AlertDialog.Builder myAlert;
    LogInUser logInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        myAlert = new AlertDialog.Builder(this);
        viewDialog = new ViewDialog(this);
        loginUtils = new LoginUtils(this, this);

        Button loginButton = findViewById(R.id.submit);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Context context = this;
        final Activity activity = this;

        //On click check if user provided a correct data and if it is correct then execute a
        //LonInUser Class
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    if (loginUtils.checkLogin((EditText)findViewById(R.id.username),
                            (EditText)findViewById(R.id.password))){
                        username = usernameEditText.getText().toString();
                        password = passwordEditText.getText().toString();
                        logInUser = new LogInUser(username, password                                ,
                                loginUtils,context, activity, myAlert, viewDialog);
                        logInUser.execute();
                    }

                }
        });
    }




}

