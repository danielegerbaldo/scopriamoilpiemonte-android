package com.example.guitaass.AsynkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class RequestNetAsync extends AsyncTask<String, String, String> {
    private Context context;
    ProgressDialog progressDialog;
    private String result;
    private Handler messageHandler; //message handler of activity that call this class
    private Message resultMessage;  //this message will be send to handler
    private String dialogTitle;
    private String dialogMessage;

    public RequestNetAsync(Context context, Handler messageHandler, String dialogTitle, String dialogMessage){
        this.context = context;
        this.messageHandler = messageHandler;
        resultMessage = new Message();
        resultMessage.setTarget(messageHandler);
        progressDialog = new ProgressDialog(context);
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
    }

    public RequestNetAsync(){}

    @Override
    protected void onPostExecute(String s) {
        Log.d("onPostExecute", "Result = " + s);
        //Toast.makeText(context, "Result = " + s, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        result = s;
        switch (result){
            case "\"OK\"":
                result = "OK";
                break;
            case "\"ERROR\"":
                result = "ERROR";
                break;
            case "\"DATAERR\"":
                result = "DATAERR";
                break;
            case "\"UBIERR\"":
                result = "UBIERR";
                break;
            case "\"USRERR\"":
                result = "USRERR";
                break;
            case "\"TIMERR\"":
                result = "TIMERR";
                break;
            case "\"ERRADMIN\"":
                result = "ERRADMIN";
                break;
            case "\"ERRUT\"":
                result = "ERRUT";
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putString("RESULT", result);
        resultMessage.setData(bundle);
        resultMessage.sendToTarget();
    }



    @Override
    protected void onPreExecute() {
        progressDialog.setTitle(dialogTitle);
        progressDialog.setCancelable(false);    //con flag false fa si che non possa essere cancellato se si clicca fuori dalla sua area
        progressDialog.setMessage(dialogMessage);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                cancel(true);
            }
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... args){
        try{
            Log.d("RequestAsybc", "sono in backgrund");
            return sendPost(args[0], args[1]);
        }catch (Exception e){
            return"connessione_timeout";
        }
    }

    private static String sendPost(String urlLink, String param) throws Exception {
        String parameters = param;
        URL url = new URL(urlLink);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(20000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        Log.d("URL", String.valueOf(url));
        Log.d("Connection", String.valueOf(connection));
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
        Log.d("SendPost", parameters);
        writer.write((parameters));
        writer.flush();
        writer.close();
        os.close();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            //Toast.makeText(context, "HTTP OK", Toast.LENGTH_SHORT).show();
            Log.d("responseCode", "OK");
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            Log.d("Reader", "creato il buffer reader");
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while((line = in.readLine()) != null) {
                Log.d("Line", line);
                sb.append(line);
                break;
            }
            in.close();
            Log.d("sendPost", "result: " + sb.toString());
            return sb.toString();
        }
        return "ERROR";
    }
}
