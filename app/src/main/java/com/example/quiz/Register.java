package com.example.quiz;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.quiz.data.Endpoint;
import com.example.quiz.register.RegisterUtils;
import com.example.quiz.utilities.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The Register Class's purpose is to validate a fields and execute an Async Class RegisterAccount.
 */
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

        //Check if the values has been correctly provided
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

    /**
     * The RegisterAccount is an Async Class which purpose is to send a HTTP request
     * with the values provided by the User to a Register Endpoint.
     */
    public class RegisterAccount extends AsyncTask<String, Void, String>{
        private String result;
        private int code;

        /**
         * Display wait indicator until Http request is not finished
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewDialog.showDialog();

        }

        /**
         * Send a data provided by a user to a Register Endpoint
         *
         * @return          response from a server
         */
        @Override
        protected String doInBackground(String... params){
            EditText usernameEditText = findViewById(R.id.register_username);
            EditText passwordEditText = findViewById(R.id.register_password);
            EditText emailEditText = findViewById(R.id.register_email);

            // create your json here
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user", usernameEditText.getText().toString());
                jsonObject.put("password", passwordEditText.getText().toString());
                jsonObject.put("mail", emailEditText.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            // put your json here
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Endpoint endpoint = new Endpoint();

            Request request = new Request.Builder()
                    .url(endpoint.getUrl()+"users/register")
                    .post(body)
                    .header("Content-Type", "application/json")
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();
                result = response.body().string();
                code = response.code();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Check if response have been successful, if yes then display success Alert else display
         * Error Alert
         *
         * @param s         response
         */
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            viewDialog.hideDialog();
            if (code == 201) {
                registerUtils.showSuccessAlert(myAlert);
            }else{
                registerUtils.showFailedAlert(myAlert);
            }
        }
    }
}