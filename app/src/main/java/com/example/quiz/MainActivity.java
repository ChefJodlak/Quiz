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

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The MainActivity class has three main reasons:
 * 1) Check server status
 * 2) Lead user to the Login screen
 * 3) Lead user to the Register screen
 */
public class MainActivity extends AppCompatActivity {

    int code;

    /**
     * Set response code that
     * @param code      code that is returned by the Http request
     */
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check server status, if server is ON then allow user to proceeding to the Login/Register
        //Screen
        new GetServerStatus().execute();

        //Upon pressing login button, user is leaded to the
        // Login screen(if the server in online)
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
        //Upon pressing register button, user is leaded to the
        // Register screen(if the server in online)
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

    /**
     * Display error Alert
     */
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

    /**
     * The GetServerStatus is an Async class that is made for sending a HTTP Request to the endpoint
     * that is displaying server status.
     */
    public class GetServerStatus extends AsyncTask<String, Void, String>{

        /**
         * Send Http request to the endpoint whose task is to display server status.
         *
         * @return      return result of the Http request
         */
        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();

                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server

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

        /**
         * Set the value of Edit Text with the server status.
         */
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            TextView textview = findViewById(R.id.status);
            try {
                JSONObject obj = new JSONObject(s);
                if(obj.getBoolean("alive")){
                    textview.setText("Server is Alive!");
                }
            }catch(JSONException e){
                textview.setText("Server Offline!");
            }
        }
        }

    /**
     * Lead user to the Login screen.
     */
    private void goToLoginScreen()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * Lead user to the Register screen.
     */
    private void goToRegisterScreen()
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
