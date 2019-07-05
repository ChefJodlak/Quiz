package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import com.example.quiz.MainActivity;
import com.example.quiz.R;
import com.example.quiz.data.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The Logout is an Async Class which purpose is to send a HTTP request with login token
 * to a Logout Endpoint.
 */
public class Logout extends AsyncTask<String, Void, String> {

    private String token, result;
    private int code;
    private Context context;
    private AlertDialog.Builder myAlert;
    Activity activity;


    public Logout(AlertDialog.Builder myAlert, Context context, Activity activity, String token) {
        this.myAlert = myAlert;
        this.context = context;
        this.activity = activity;
        this.token = token;
    }

    /**
     * Redirect user to the main screen and close current Activity.
     */
    public void goToMainScreen()
    {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        activity.finish();
    }

    /**
     * Display Success Message at the bottom of the screen
     */
    private void displaySuccessMessage() {
        String welcome = context.getResources().getString(R.string.logout_success);
        Toast.makeText(context, welcome, Toast.LENGTH_LONG).show();
    }

    /**
     * Display Fail Alert
     */
    private void showFailedAlert(AlertDialog.Builder myAlert){
        myAlert.setMessage("Unexpected error!")
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
     * Send Http request to the Logout endpoint
     * @return      return a result of the http request
     */
    @Override
    protected String doInBackground(String... params) {

        try {
            System.out.println(token);
            //1. Create okHttp Client object
            OkHttpClient client = new OkHttpClient();
            Endpoint endpoint = new Endpoint();

            //2. Define request being sent to the server
            Request request = new Request.Builder()
                    .url(endpoint.getUrl() + "users/logout")
                    .header("QuizIdentity", token)
                    .build();

            //3. Transport the request and wait for response to process next
            try{
                Response response = client.newCall(request).execute();
                code = response.code();
                result = response.body().string();
            } catch (NullPointerException e){
                e.printStackTrace();
            }


            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check the code of the response, if it is Successful then redirect user to the Main Screen
     * else display Error Alert
     *
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(code == 200){
            displaySuccessMessage();
            goToMainScreen();
        } else{
            showFailedAlert(myAlert);
        }

    }
}
