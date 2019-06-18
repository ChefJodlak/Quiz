package com.example.quiz.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
     * 1.SEND DATA FROM EDITTEXT OVER THE NETWORK
     * 2.DO IT IN BACKGROUND THREAD
     * 3.READ RESPONSE FROM A SERVER
     */
    public class Sender extends AsyncTask<Void,Void,String> {
        Context c;
        String urlAddress;
        EditText usernameTxt,passwordTxt,emailTxt;
        String username,password,email;
        ProgressDialog pd;
        /*
        1.OUR CONSTRUCTOR
        2.RECEIVE CONTEXT,URL ADDRESS AND EDITTEXTS FROM OUR MAINACTIVITY
        */
        public Sender(Context c, String urlAddress,EditText...editTexts) {
            this.c = c;
            this.urlAddress = urlAddress;
//INPUT EDITTEXTS
            this.usernameTxt=editTexts[0];
            this.passwordTxt=editTexts[1];
            this.emailTxt=editTexts[2];
//GET TEXTS FROM EDITEXTS
            username=usernameTxt.getText().toString();
            password=passwordTxt.getText().toString();
            email=emailTxt.getText().toString();
        }
        /*
        1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
        */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(c);
            pd.setTitle("Send");
            pd.setMessage("Sending..Please wait");
            pd.show();
        }
        /*
        1.WHERE WE SEND DATA TO NETWORK
        2.RETURNS FOR US A STRING
        */
        @Override
        protected String doInBackground(Void... params) {
            return this.send();
        }
        /*
        1. CALLED WHEN JOB IS OVER
        2. WE DISMISS OUR PD
        3.RECEIVE A STRING FROM DOINBACKGROUND
        */
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pd.dismiss();
            if(response != null)
            {
//SUCCESS
                Toast.makeText(c,response,Toast.LENGTH_LONG).show();
                usernameTxt.setText("");
                passwordTxt.setText("");
                emailTxt.setText("");
            }else
            {
//NO SUCCESS
                Toast.makeText(c,"Unsuccessful "+response,Toast.LENGTH_LONG).show();
            }
        }
        /*
        SEND DATA OVER THE NETWORK
        RECEIVE AND RETURN A RESPONSE
        */
        private String send()
        {
//CONNECT
            try {
                URL registerEndpoint = new URL(urlAddress);


                HttpsURLConnection con = (HttpsURLConnection) registerEndpoint.openConnection();

                con.setRequestMethod("POST");
                if(con==null)
                {
                    return null;
                }
                try
                {
                    OutputStream os=con.getOutputStream();
//WRITE
                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    bw.write(new DataPackager(username,password,email).packData());
                    bw.flush();
//RELEASE RES
                    bw.close();
                    os.close();
//HAS IT BEEN SUCCESSFUL?
                    int responseCode=con.getResponseCode();
                    if(responseCode==con.HTTP_OK)
                    {
//GET EXACT RESPONSE
                        BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuffer response=new StringBuffer();
                        String line;
//READ LINE BY LINE
                        while ((line=br.readLine()) != null)
                        {
                            response.append(line);
                        }
//RELEASE RES
                        br.close();
                        return response.toString();
                    }else
                    {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
             catch (MalformedURLException e) {
                    //bad  URL, tell the user
                } catch (IOException e) {
                //network error/ tell the user
            }

            return null;

        }

    }

