package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.quiz.MainScreen;
import com.example.quiz.data.Endpoint;
import com.example.quiz.utilities.ViewDialog;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogInUser extends AsyncTask<String, Void, String> {
    int code;
    String username, password, result;
    ViewDialog viewDialog;
    LoginUtils loginUtils;
    Context context;
    Activity activity;
    AlertDialog.Builder myAlert;

    public LogInUser(String username, String password,
                     LoginUtils loginUtils, Context context,
                     Activity activity, AlertDialog.Builder myAlert, ViewDialog viewDialog){
        this.username = username;
        this.password = password;
        this.loginUtils = loginUtils;
        this.context = context;
        this.activity = activity;
        this.myAlert = myAlert;
        this.viewDialog = viewDialog;
    }

    private void setUserData(String result, String username) {
        new UserData(result, username);
    }

    public void goToMainScreen()
    {
        Intent intent = new Intent(context, MainScreen.class);
        context.startActivity(intent);
        ActivityCompat.finishAffinity(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        viewDialog.showDialog();

    }
    @Override
    protected String doInBackground(String... params){

        try {
            //1. Create okHttp Client object
            OkHttpClient client = new OkHttpClient();
            Endpoint endpoint = new Endpoint();
            //2. Define request being sent to the server
            System.out.println(endpoint.getUrl()+"users/login?user="
                    +username +"&pass="+password);
            Request request = new Request.Builder()
                    .url(endpoint.getUrl()+"users/login?user="
                            +username +"&pass="+password)
                    .header("Connection", "close")
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
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        viewDialog.hideDialog();
        if(code == 200){
            setUserData(result, username);
            loginUtils.updateUiWithUser(username);
            goToMainScreen();
        }else{
            loginUtils.showFailedAlert(myAlert);
        }
    }
}