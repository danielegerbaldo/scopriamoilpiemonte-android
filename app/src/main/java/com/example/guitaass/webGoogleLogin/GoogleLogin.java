package com.example.guitaass.webGoogleLogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.guitaass.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;

public class GoogleLogin extends AppCompatActivity {

    private static final String TAG = "GoogleLogin";
    private SharedPreferences shpr;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        WebView webView = findViewById(R.id.web_view);
        webView.clearCache(true);

        /*WebViewClient webViewClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                String cookies = CookieManager.getInstance().getCookie(view.getUrl());
                Log.d(TAG, "onPageFinished: cookies = " + cookies);
            }
        };

        webView.setWebViewClient(webViewClient);*/

        /*
        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                String cookies = CookieManager.getInstance().getCookie(window.getUrl());
                Log.d(TAG, "onPageFinished: cookies = " + cookies);
            }
        };
        webView.setWebChromeClient(webChromeClient);
        */

        /*webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setSupportMultipleWindows(true);*/

        /*
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        */


        shpr = PreferenceManager.getDefaultSharedPreferences(this);

        String ip = shpr.getString("IP", "localhost");

        String url = "http://" + ip + "/oauth2/authorization/google";
        //String url = "http://" + ip + "/login/oauth2/code/google";

        Log.d(TAG, "URL = " + url);


        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.acceptThirdPartyCookies(webView);
        cookieManager.acceptCookie();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);


        //webView.loadUrl(url);


        //cookieManager.removeAllCookie();
        //cookieManager.setCookie(url, "GoogleLogin");
        //CookieSyncManager.getInstance().sync();


        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        Log.d(TAG, "intent extras: " + i.getExtras());




        //webView.loadUrl(url);


        //String cookie = cookieManager.getCookie(url);



        Thread coo = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                int maxIt = 60000;
                while(maxIt > 0) {
                    String cookie = cookieManager.getCookie(url);
                    cookieManager.acceptCookie();
                    Log.d(TAG, "thread: cookie = " + cookie);
                    cookie = cookieManager.getCookie("http://" + ip + "/login/oauth2/code/google");
                    Log.d(TAG, "thread: cookie 2 = " + cookie);
                    //Log.d(TAG, "thread: cookie = " + cookie);
                    //Log.d(TAG, "thread: cookie = " + cookieManager.getCookie("http://scopriamoilpiemonte.it"));
                    //Log.d(TAG, "thread: cookie = " + cookieManager.getCookie("http://scopriamoilpiemonte.it/**"));
                    //Log.d(TAG, "thread: cookie = " + cookieManager.getCookie("http://" + ip + "/oauth2/authorization/google"));


                    try {
                        //URL url1 = new URL("http://" + ip + "/login/oauth2/code/google");
                        URL url1 = new URL(url);

                        url1.openConnection().connect();



                        //Log.d(TAG, "onCreate: " + url1.openConnection().getHeaderField("GoogleLogin"));

                        Log.d(TAG, "run: header 1");

                        Map<String, List<String>> mapHeader = url1.openConnection().getHeaderFields();

                        mapHeader.forEach((key, lista) ->{
                            lista.forEach(elemento ->{
                                Log.d(TAG, "header: " + key + " = " + elemento);
                            });
                        });

                        Log.d(TAG, "--------------------------------------------------------");

                        Log.d(TAG, "run: header 2");


                        URL url2 = new URL("http://" + ip + "/login/oauth2/code/google");
                        url2.openConnection().connect();

                        Map<String, List<String>> mapHeader2 = url2.openConnection().getHeaderFields();

                        mapHeader2.forEach((key, lista) ->{
                            lista.forEach(elemento ->{
                                Log.d(TAG, "header: " + key + " = " + elemento);
                            });
                        });

                        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


                        //Log.d(TAG, "onCreate: " + url1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Log.d(TAG, "thread: cookie google: " + abc.get("GoogleLogin"));

                    try {
                        Thread.sleep(1000);
                        maxIt -= 500;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }


            }
        });
        coo.start();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            String mainPart = uri.toString().split("#")[1];
            Log.d(TAG, "onNewIntent: " + mainPart);
            String[] arguments = mainPart.split("&");
            String argument = arguments[0];
            String token = argument.split("=")[1];
            Log.d(TAG, "onNewIntent: token " + token);
        }
    }

}