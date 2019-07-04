package com.example.quiz.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.quiz.Quiz;
import com.example.quiz.data.Endpoint;
import com.example.quiz.utilities.ViewDialog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetQuiz extends AsyncTask<String, Void, String> {

    private String result;
    private ViewDialog viewDialog;
    private Context context;
    private AlertDialog.Builder myAlert;
    private int code;

    public GetQuiz(ViewDialog viewDialog, AlertDialog.Builder myAlert, Context context){
        this.viewDialog = viewDialog;
        this.myAlert = myAlert;
        this.context = context;
    }

    public void StartGame(String result) {
        Intent intent = new Intent(context, Quiz.class);
        intent.putExtra("json", result);
        context.startActivity(intent);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        viewDialog.showDialog();

    }
    @Override
    protected String doInBackground(String... params) {

        try {

            //1. Create okHttp Client object
            OkHttpClient client = new OkHttpClient();
            Endpoint endpoint = new Endpoint();

            //2. Define request being sent to the server
            Request request = new Request.Builder()
                    .url(endpoint.getUrl() + "questions/random")
                    //.header("token", "")
                    .build();

            //3. Transport the request and wait for response to process next
            try {
                Response response = client.newCall(request).execute();
                code = response.code();
                result = response.body().string();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (code == 200){
            viewDialog.hideDialog();
            new GetQuestions(result);
            StartGame(result);
        }else{
            showFailedAlert(myAlert);
        }

    }
}
