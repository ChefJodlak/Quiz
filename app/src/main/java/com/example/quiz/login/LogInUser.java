package com.example.quiz.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import com.example.quiz.MainScreen;
import com.example.quiz.data.Endpoint;
import com.example.quiz.utilities.ViewDialog;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The LogInUser is an Async class that is made for sending a HTTP Request to the endpoint
 * made for loging in user
 */
public class LogInUser extends AsyncTask<String, Void, String> {
    int code;
    String username, password, result;
    ViewDialog viewDialog;
    LoginUtils loginUtils;
    Context context;
    Activity activity;
    AlertDialog.Builder myAlert;
    UserData userData;

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

    /**
     * Create new UserData object, that is storing a username and token
     *
     * @param result        json that is a response from HTTP Request
     * @param username      username provided in a Login form
     */
    private void setUserData(String result, String username) {
        this.userData = new UserData(result, username);
    }

    /**
     * Redirect user to a MainScreen and send a token to the next Activity.
     *
     * @param token         token that was generated by server after login
     */
    public void goToMainScreen(String token)
    {
        Intent intent = new Intent(context, MainScreen.class);
        intent.putExtra("token", token);
        context.startActivity(intent);
        ActivityCompat.finishAffinity(activity);
    }
    /**
     * Display wait indicator until Http request is not finished
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        viewDialog.showDialog();

    }

    /**
     * Send a HTTP GET Request to a Login Endpoint with a values provided by the User
     *
     * @return      response body
     */
    @Override
    protected String doInBackground(String... params){

        try {
            //1. Create okHttp Client object
            OkHttpClient client = new OkHttpClient();
            Endpoint endpoint = new Endpoint();
            //2. Define request being sent to the server
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

    /**
     * Check the response code and if it is successful then pass a json and username to the
     * UserData Class and obtain the token from this class. If the Response code != 200 then
     * display Error Alert
     *
     * @param s         response body
     */
    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        viewDialog.hideDialog();
        if(code == 200){
            setUserData(result, username);
            loginUtils.updateUiWithUser(username);
            goToMainScreen(userData.getToken());
        }else{
            loginUtils.showFailedAlert(myAlert);
        }
    }
}