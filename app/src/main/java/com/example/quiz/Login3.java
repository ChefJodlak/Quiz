package com.example.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login3 extends AppCompatActivity {

    String username, password;
    EditText usernameEditText, passwordEditText;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.submit);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FeedTask1().execute();
                System.out.println(result);
            }
        });

    }
    public class FeedTask1 extends AsyncTask<String, Void, String> {
        String result;
        @Override
        protected String doInBackground(String... params) {


                //1. Create okHttp Client object
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                OkHttpClient client = new OkHttpClient();

                //2. Define request being sent to the server
                RequestBody postData = new FormBody.Builder()
                        .build();

                Request request = new Request.Builder()
                        .url("http://patrycja.localhost.run/users/login?user="+username+"&pass="+password)
                        .build();

                //3. Transport the request and wait for response to process next
            try {
                Response response = client.newCall(request).execute();
                result = response.header("Server").toString();
            }catch(IOException e){
                return null;
            }

                System.out.println("Gowno "+result);
                return result;
        }

    }
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}

