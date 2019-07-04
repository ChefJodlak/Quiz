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

import com.example.quiz.data.Endpoint;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    int code;

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetServerStatus().execute();
        Button login_button =findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code == 200) {
                    goToLoginScreen();
                }else{
                    showFailedAlert();
                }
            }
        });
        Button register_button =findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code == 200) {
                    goToRegisterScreen();
                }else{
                    showFailedAlert();
                }
            }
        });
    }
    public void showFailedAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Connection error with the server")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }
    public class GetServerStatus extends AsyncTask<String, Void, String>{
        private String result;
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
                setCode(response.code());
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

    private void goToRegisterScreen()
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
