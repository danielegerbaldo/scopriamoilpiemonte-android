package com.example.guitaass.webGoogleLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.MainActivity;
import com.example.guitaass.R;
import com.example.guitaass.retrofit.utente.LoginResponse;
import com.example.guitaass.retrofit.utente.RetrofitClient;
import com.example.guitaass.retrofit.utente.UserDto;
import com.example.guitaass.sindaco.home.SindacoHome;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleLogin2 extends AppCompatActivity {

    private SharedPreferences shpr;
    private static final String TAG = "GoogleLogin2";

    public static final String USER_AGENT_FAKE = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    String url = "http://" + "scopriamoilpiemonte.it" + "/oauth2/authorization/google";

    WebView webView;

    Context context;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login2);

        shpr = PreferenceManager.getDefaultSharedPreferences(this);


        /*WebView webView
        shpr = PreferenceManager.getDefaultSharedPreferences(this);

        String ip = shpr.getString("IP", "localhost");
        */

        context = this;


        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString(USER_AGENT_FAKE);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url){

                System.out.println("Current URL : "+ url + " " + Html.fromHtml("<br>") + " is http url ? "+ URLUtil.isHttpUrl(url));

                if( url.equals("http://www.c-sharpcorner.com/1/247/android-programming.aspx"))
                {
                    Toast.makeText(getApplicationContext(), "Android Articles page is loading... ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url){
                CookieSyncManager.getInstance().sync();
                URL url1 = null;
                try {
                    url1 = new URL(url);
                }
                catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.d(TAG, "C sharp corner user Ref " + url1.getRef());
                Log.d(TAG, " C sharp corner user host " + url1.getHost());
                Log.d(TAG, "C sharp corner user authority " + url1.getAuthority());
                String cookies = CookieManager.getInstance().getCookie(url);
                String googleCookie = cookies.substring(cookies.indexOf("GoogleLogin=") + 12).trim();
                Log.d(TAG, "All COOKIES " + cookies);
                Log.d(TAG, "GOOGLE COOKIE : " + googleCookie);
                Toast.makeText(getApplicationContext(),"All Cookies " + cookies , Toast.LENGTH_LONG).show();

                //valido il token
                Call<UserDto> call = RetrofitClient.getInstance(
                        getApplicationContext())
                        .getMyApi()
                        .validateToken(googleCookie, googleCookie);

                call.enqueue(new Callback<UserDto>() {
                    @Override
                    public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                        if(response.body() != null){
                            shpr.edit().putString("token", googleCookie).apply();
                            UserDto userDto = response.body();
                            Log.d(TAG, "onResponse: USER DTO: ID" + userDto.getId() + " email " + userDto.getEmail());
                            ingresso(userDto.getId());
                        }

                    }

                    @Override
                    public void onFailure(Call<UserDto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Errore comunicazione con server", Toast.LENGTH_LONG).show();
                    }
                });

                //Intent intent = new Intent(view.getContext(), MainActivity.class);
                //startActivity(intent);
            }
        });
        webView.loadUrl(url);
        //CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private void ingresso(long utenteID){
        Call<Utente> callUtente = RetrofitClient
                .getInstance(getApplicationContext())
                .getMyApi()
                .infoUtente(utenteID, "Bearer " + shpr.getString("token", "")/*, utenteID*/);
        //callUtente.request().headers().newBuilder().add("Bearer", shpr.getString("token", "")).build();
        //callUtente.request()
        Log.d(TAG, "ingresso: request = " + callUtente.request());
        Log.d(TAG, "ingresso: request header = " + callUtente.request().headers());
        callUtente.enqueue(new Callback<Utente>() {
            @Override
            public void onResponse(Call<Utente> call, Response<Utente> response) {
                Toast.makeText(getApplicationContext(), "utente trovato: " + response.body().getNome() + " " + response.body().getCognome(), Toast.LENGTH_LONG).show();
                Utente utente = response.body();
                if(utente != null){
                    //Toast.makeText(getApplicationContext(), "login come " + utente.getEmail(), Toast.LENGTH_LONG).show();
                    //creo un intento e gli aggiungo i dati che servono solo alla home dell'utente
                    Intent intent = new Intent();
                    intent.putExtra("Nome", utente.getNome());
                    intent.putExtra("Cognome", utente.getCognome());
                    intent.putExtra("Email", utente.getEmail());

                    //aggiungo nelle shared preference i dati che serviranno per tutta l'applicazione
                    shpr.edit().putLong("utente_id", utente.getId()).apply();
                    intent.setClass(getApplicationContext(), SindacoHome.class);
                    Log.d(TAG, "ingresso: " + utente.getRuoli().get(0).getAuthority());
                    if(utente.getRuoli().get(0).getAuthority().equals("ROLE_MAYOR")){
                        Log.d(TAG, "ingresso: login come sindaco");
                        Toast.makeText(getApplicationContext(), "login come sindaco di: " + utente.getDipendenteDiComune(), Toast.LENGTH_LONG).show();
                        shpr.edit().putLong("comune_id", utente.getDipendenteDiComune()).apply();
                        shpr.edit().putString("ruolo", utente.getRuoli().get(0).getAuthority()).apply();
                        //shpr.edit().putString("email", utente.getEmail());
                    }else{
                        Toast.makeText(getApplicationContext(), "login come utente ", Toast.LENGTH_LONG).show();
                        shpr.edit().putString("ruolo", "utente").apply();
                        //intent.setClass(getApplicationContext(), UtenteHome.class);
                    }
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "c'è stato un errore interno", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Utente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "utente non trovato", Toast.LENGTH_LONG).show();
            }
        });

    }


}