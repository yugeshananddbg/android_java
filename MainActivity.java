package com.astrovastutalk.app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    String deviceToken;
    WebView webView;
    ProgressDialog progressDialog;
    boolean doubleBackToExitPressedOnce = false;
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            System.out.println( "Fetching FCM registration token failed");
//                            return;
//                        }
//
//
//                        String token = task.getResult();
//
//
//
//                        System.out.println( token);
//                        Toast.makeText(MainActivity.this, "From Firebase Function :" + token
//                                , Toast.LENGTH_SHORT).show();
//
//                        deviceToken =token;
//
//                    }
//                });

        webView= findViewById(R.id.webview);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading..");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
            }

        });
        Intent intent=getIntent();
     deviceToken =   intent.getStringExtra(SplashScreen.DeviceToken);
        webView.loadUrl("https://www.astrovastutalk.com/?deviceToken="+deviceToken);

        System.out.println( deviceToken);
        Log.d("device token", deviceToken);

    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}