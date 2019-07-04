package com.example.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quiz.data.Endpoint;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginScreen extends AppCompatActivity {

    private String login, password;
    private EditText usernameEditText,passwordEditText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        usernameEditText = (EditText) findViewById(R.id.username2);
        passwordEditText = (EditText) findViewById(R.id.password2);
        button = (Button) findViewById(R.id.login2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new LoginScreen.FeedTask().execute();
                goToLoginScreen();
            }
        });
    }

    public class FeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {

                Endpoint endpoint = new Endpoint();
                //1. Create okHttp Client object
                login = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                OkHttpClient client = new OkHttpClient();

                //2. Define request being sent to the server
                RequestBody postData = new FormBody.Builder()
                        .build();

                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"login?user="+login+"&pass="+password)
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                return result;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textview = (TextView) findViewById(R.id.status);
            textview.setText(s);
        }
    }
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
    }
