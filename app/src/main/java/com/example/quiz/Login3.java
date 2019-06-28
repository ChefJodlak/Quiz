package com.example.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quiz.data.Endpoint;
import com.example.quiz.login.UserData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login3 extends AppCompatActivity {

    String username, password;
    EditText usernameEditText, passwordEditText;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.submit);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new logInUser().execute();

                }
        });

    }
    private void setUserData(String result, String username) {
        new UserData(result, username);
    }

    public class logInUser extends AsyncTask<String, Void, String> {
        int code;

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();
                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"users/login?user="+username+"&pass="+password)
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                code = response.code();
                try {
                    result = response.body().string();
                }catch(NullPointerException e){
                    e.printStackTrace();
                }
                return result;

            }
            catch(Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(code == 200){
                System.out.println(result);
                setUserData(result, username);
                goToMainScreen();
            }

        }

    }
    private void goToMainScreen()
    {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
}

