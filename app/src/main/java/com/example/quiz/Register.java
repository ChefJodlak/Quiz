package com.example.quiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.quiz.data.Endpoint;
import com.example.quiz.register.RegisterUtils;
import com.example.quiz.utilities.ViewDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Register extends AppCompatActivity {
    ViewDialog viewDialog;
    RegisterUtils registerUtils;
    AlertDialog.Builder myAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myAlert = new AlertDialog.Builder(this);

        viewDialog = new ViewDialog(this);
        registerUtils = new RegisterUtils(this, this);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerUtils.checkRegister((EditText)findViewById(R.id.register_username),
                        (EditText)findViewById(R.id.register_password),
                        (EditText)findViewById(R.id.register_email))) {
                    new RegisterAccount().execute();
                }
            } });
    }

    public void goToLoginScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public class RegisterAccount extends AsyncTask<String, Void, String>{
        private String result;
        private int code;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewDialog.showDialog();

        }
        @Override
        protected String doInBackground(String... params){
            EditText usernameEditText = findViewById(R.id.register_username);
            EditText passwordEditText = findViewById(R.id.register_password);
            EditText emailEditText = findViewById(R.id.register_email);
            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .build();

                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                RequestBody formBody = new FormBody.Builder()
                        .add("user", usernameEditText.toString())
                        .add("password", passwordEditText.toString())
                        .add("email", emailEditText.toString())
                        .build();

                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"users/register")
                        .post(formBody)
                        .header("Connection", "close")
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                code = response.code();
                return result;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            viewDialog.hideDialog();
            System.out.println(result+code);
            if (code == 200) {
                registerUtils.showSuccessAlert(myAlert);
            }else{
                registerUtils.showFailedAlert(myAlert);
            }
        }
    }
}