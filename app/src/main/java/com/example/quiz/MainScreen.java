package com.example.quiz;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.quiz.login.Logout;
import com.example.quiz.quiz.GetQuiz;
import com.example.quiz.utilities.ViewDialog;

/**
 * The MainScreen class has two main reasons:
 * 1) Start a game
 * 2) Logout a user
 */

public class MainScreen extends AppCompatActivity {

    ViewDialog viewDialog;
    AlertDialog.Builder myAlert;
    Logout logout;
    GetQuiz getQuiz;
    Context context = this;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Pass token value to next activity
        Intent intent = getIntent();
        final String token = intent.getStringExtra("token");

        myAlert = new AlertDialog.Builder(this);
        viewDialog = new ViewDialog(this);
        getQuiz = new GetQuiz(viewDialog, myAlert, this, token);
        Button button = findViewById(R.id.play_button);

        //Execute GetQuiz Async class
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuiz.execute();

            }
        });

        //Execute Logout Async class
        Button logout_button = findViewById(R.id.logout_button);
        logout = new Logout(myAlert, context, activity, token);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.execute();
            }
        });
    }
}
