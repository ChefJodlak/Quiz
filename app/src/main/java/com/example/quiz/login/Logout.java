package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.quiz.MainActivity;
import com.example.quiz.MainScreen;
import com.example.quiz.R;
import com.example.quiz.data.Endpoint;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Logout extends AsyncTask<String, Void, String> {

    private String token, result;
    private int code;
    MainScreen mainScreen;
    private Response response;
    private Context context;
    private AlertDialog.Builder myAlert;
    Activity activity;


    public Logout(AlertDialog.Builder myAlert, Context context, Activity activity) {
        this.myAlert = myAlert;
        this.context = context;
        this.activity = activity;
    }
    public void goToMainScreen()
    {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        activity.finish();
    }
    private void displaySuccessMessage() {
        String welcome = context.getResources().getString(R.string.logout_success);
        Toast.makeText(context, welcome, Toast.LENGTH_LONG).show();
    }

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
    @Override
    protected String doInBackground(String... params) {

        try {

            //1. Create okHttp Client object
            OkHttpClient client = new OkHttpClient();
            Endpoint endpoint = new Endpoint();

            //2. Define request being sent to the server
            Request request = new Request.Builder()
                    .url(endpoint.getUrl() + "users/logout")
                    .header("QuizIdentity", "oauwygrf0aw64tgfaesufg0q7gfw7gefr9a76")
                    .build();

            //3. Transport the request and wait for response to process next
            try{
                response = client.newCall(request).execute();
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
