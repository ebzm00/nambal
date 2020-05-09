package com.example.webviewtest;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SubActivity extends AppCompatActivity {

    private static final int FILE_REQUEST_CODE = 9999;
    private ValueCallback mFilePathCallback;
    private WebView webView;
    private WebSettings webSettings;
    private Button btn_input;
    //    private String url = "https://foodimage.netlify.com";
    private String url = "file:///android_asset/www/index.html";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        webView = (WebView) findViewById(R.id.webView);

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.equals(url)) {
                    String keyword = "Food-Name";
                    String script = "javascript:function afterLoad() {"
                            + "document.getElementById('food_name').value = '" + keyword + "';"
                            + "document.forms[0].setAttribute('onsubmit', 'window.food_name.getName(elements[0].value); return true;');"
                            + "};"
                            + "afterLoad();";

                    view.loadUrl(script);
                }
            }
        });
        webView.loadUrl(url);

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void getName(String keyword) {
                Intent intent = new Intent(SubActivity.this, AddActivity.class);
                intent.putExtra("name", keyword);
                startActivity(intent);
                finish();
            }
        }, "food_name");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, FILE_REQUEST_CODE);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            } else {
                mFilePathCallback.onReceiveValue(new Uri[]{data.getData()});
            }
            mFilePathCallback = null;
        } else {
            mFilePathCallback.onReceiveValue(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    private class webViewClientclass extends WebViewClient {
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url != null && url.startsWith("intent://")) {
//                try {
//                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
//                    if (existPackage != null) {
//                        startActivity(intent);
//                    } else {
//                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
//                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
//                        startActivity(marketIntent);
//                    }
//                    return true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (url != null && url.startsWith("market://")) {
//                try {
//                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    if (intent != null) {
//                        startActivity(intent);
//                    }
//                    return true;
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//            }
//            view.loadUrl(url);
//            return false;
//        }
//    }
}
