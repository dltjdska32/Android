package com.example.webviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    //  <uses-permission android:name="android.permission.INTERNET"/> 선언필요
    // +
    //AndroidManifest.xml에 가셔서
    //<application..으로 시작하는 태그에서
    //다음 옵션을 추가해줍니다!
    //android:usesCleartextTraffic="true"

    private WebView webView;
    private String url = "https://www.naver.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        //자바스크립트를 허용해주기위한 함수
        webView.getSettings().setJavaScriptEnabled(true);
        // url주소를 실행하는 함수
        webView.loadUrl(url);
        //구글크롬으로 실행
        webView.setWebChromeClient(new WebChromeClient());
        //그냥실행
        webView.setWebViewClient(new WebViewClientClass());
    }

    //앱상에서 뒤로가기를 누를경우 원래화면으로 돌아가도록 하는 로직
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //뒤로가기를 누르고 뒤로가기를 할수있으면 실행
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
           //웹뷰를 뒤로가게 해라
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        //shouldOverridngUrlLoading() 함수는 현재페이지의 url을 읽어오는 함수
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}