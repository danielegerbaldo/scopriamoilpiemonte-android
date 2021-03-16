package com.example.guitaass.AsynkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.common.util.HttpUtils;

import org.jetbrains.annotations.NotNull;

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
        //progressDialog.dismiss();
        /*result = s;
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
        }*/
        Bundle bundle = new Bundle();
        bundle.putString("RESULT", s);
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
            return sendGet(args[0], args[1]);
        }catch (Exception e){
            return"connessione_timeout";
        }
    }

    @NotNull
    private static String sendGet(String urlLink, String body) throws Exception {
        Log.d("AsynchTask", "¿¿¿¿¿¿¿¿¿¿¿¿¿¿Link: " + urlLink + "; body: " + body);
        //impostazioni della connessione
        URL url = new URL(urlLink);


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(20000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        Log.d("AsynchTask", "sendGet: method= " + connection.getRequestMethod());


        //dichiarazione di un output streamer
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));


        //scrivo il body della richiesta
        writer.write((body));
        writer.flush();
        writer.close();
        Log.d("AsynchTask", "sendGet: connection.toString = " + connection.toString());
        Log.d("AsynchTask", "sendGet: connection.getRequestProperty = " + connection.getRequestProperty("email"));
        Log.d("AsynchTask", "sendGet: connection.getContentType = " + connection.getContentType());
        Log.d("AsynchTask", "sendGet: connection.getContent = " + connection.getContent());
        os.close();
        //connection.connect();


        



        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            //Toast.makeText(context, "HTTP OK", Toast.LENGTH_SHORT).show();

            /*BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            while((line = in.readLine()) != null) {
                Log.d("Line", line);
                sb.append(line);
                break;
            }
            in.close();
            Log.d("sendPost", "result: " + sb.toString());
            return sb.toString();*/
            return "ok";
        }
        return "ERROR";
    }
}
