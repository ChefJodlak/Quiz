package com.example.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.data.Endpoint;
import com.example.quiz.ui.login.Login;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetServerStatus().execute();
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen();
            }
        });
    }

    public class GetServerStatus extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();

                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                RequestBody postData = new FormBody.Builder()
                        .build();

                Request request = new Request.Builder()
                        .url(endpoint.getUrl())
                        .header("Connection", "close")
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                return result;
            }
            catch(Exception e) {
                return null;
            }
            }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            TextView textview = (TextView) findViewById(R.id.status);
            textview.setText(s);
        }
        }
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, Login3.class);
        startActivity(intent);
    }
}
