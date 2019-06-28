package com.example.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.quiz.data.Endpoint;
import com.example.quiz.quiz.CheckQuestions;
import com.example.quiz.quiz.GetQuestions;

public class Quiz extends AppCompatActivity {

    private String result, resultAnswer;

    private int userAnswer;
    private Button answer1, answer2, answer3, answer4;

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GetQuiz().execute();
        setContentView(R.layout.activity_quiz);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(1);
                new CheckAnswer().execute();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(2);
                new CheckAnswer().execute();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(3);
                new CheckAnswer().execute();
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(4);
                new CheckAnswer().execute();
            }
        });

    }

    private void setQuizValues(String result){
        GetQuestions questions = new GetQuestions(result);

        ((TextView)findViewById(R.id.question)).setText(questions.question);
        ((Button)findViewById(R.id.answer1)).setText(questions.answer1);
        ((Button)findViewById(R.id.answer2)).setText(questions.answer2);
        ((Button)findViewById(R.id.answer3)).setText(questions.answer3);
        ((Button)findViewById(R.id.answer4)).setText(questions.answer4);
    }

    private void checkAnswer(String resultAnswer){
        CheckQuestions checkAnswer = new CheckQuestions(resultAnswer);

        if(checkAnswer.getIsCorrectAnswer()){
            showSuccessAlert();
        }else{
            showFailedAlert();
        }

    }

    public void showSuccessAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(false);
        myAlert.setMessage("Poprawna odpowiedź!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToMainScreen();
                    }
                })
                .create();
        myAlert.show();
    }
    public void showFailedAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(false);
        myAlert.setMessage("Nieoprawna odpowiedź! :(")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToMainScreen();
                    }
                })
                .create();
        myAlert.show();
    }
    private void goToMainScreen()
    {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        finish();
    }

    public class GetQuiz extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();
                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"questions/random")
                        //.header("token", "")
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                result = response.body().string();
                return result;


            }
            catch(Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            System.out.println(result);
            setQuizValues(result);
        }

    }

    public class CheckAnswer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();
                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"questions/answer/a6tg65f/"+userAnswer)
                        //.header("token", "")
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                resultAnswer = response.body().string();
                return resultAnswer;

            }
            catch(Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            System.out.println(resultAnswer);
            checkAnswer(resultAnswer);
        }

    }

}
