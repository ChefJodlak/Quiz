package com.example.quiz;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.quiz.login.Logout;
import com.example.quiz.quiz.GetQuiz;
import com.example.quiz.utilities.ViewDialog;

public class MainScreen extends AppCompatActivity {

    ViewDialog viewDialog;
    AlertDialog.Builder myAlert;
    Logout logout;
    GetQuiz getQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        myAlert = new AlertDialog.Builder(this);
        viewDialog = new ViewDialog(this);
        getQuiz = new GetQuiz(viewDialog, myAlert, this);
        Button button = findViewById(R.id.play_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuiz.execute();

            }
        });

        Button logout_button = findViewById(R.id.logout_button);
        logout = new Logout(myAlert, this, this);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.execute();
            }
        });
    }



}
