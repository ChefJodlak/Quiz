package com.example.quiz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.quiz.quiz.GetQuestions;

public class Quiz extends AppCompatActivity {

    String result, resultAnswer;
    int answer;
    TextView question = (TextView) findViewById(R.id.question);
    Button answer1 = (Button) findViewById(R.id.answer1);
    Button answer2 = (Button) findViewById(R.id.answer2);
    Button answer3 = (Button) findViewById(R.id.answer3);
    Button answer4 = (Button) findViewById(R.id.answer4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        new GetQuiz().execute();
        setQuizValues(result);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 1;
                new CheckAnswer().execute();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 2;
                new CheckAnswer().execute();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 3;
                new CheckAnswer().execute();
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 4;
                new CheckAnswer().execute();
            }
        });

    }

    private void setQuizValues(String result){
        GetQuestions questions = new GetQuestions(result);

        question.setText(questions.question);
        answer1.setText(questions.answer1);
        answer2.setText(questions.answer2);
        answer3.setText(questions.answer3);
        answer4.setText(questions.answer4);
    }

    public class GetQuiz extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url("http://patrycja.localhost.run/questions/random")
                        //.header("token", "")
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                try{
                    result = response.body().string();
                } catch (NullPointerException e){
                    return null;
                }

                return result;

            }
            catch(Exception e) {
                return null;
            }
        }


    }

    public class CheckAnswer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url("http://patrycja.localhost.run/questions/answer/a6tg65f/"+answer)
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


    }

}
