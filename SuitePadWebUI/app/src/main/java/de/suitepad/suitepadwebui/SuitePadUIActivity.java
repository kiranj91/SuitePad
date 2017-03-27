package de.suitepad.suitepadwebui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SuitePadUIActivity extends AppCompatActivity {

    private WebView mWebView;
    private String json = "";

    private InputStream stream = null;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suite_pad_ui);
        mWebView = (WebView) findViewById(R.id.suitePadUI_webview);
        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        mWebView.addJavascriptInterface(jsInterface, "JSInterface");

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);


        mWebView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public WebResourceResponse shouldInterceptRequest (final WebView view, WebResourceRequest request) {

                final Uri uri = request.getUrl();
                Log.i("URI", uri.toString());

                if(uri.toString().contains("someremoteurl.com/sample.json")) {
                    Log.i("Intercept", "Request from Webview has been intercepting here");
                    return getMenuDataJsonResponse(uri);
                }
                else {
                    return super.shouldInterceptRequest(view, request);
                }


                }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.i("ssl", "onReceivedSslError: ");
              //  handle SSL Requests
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(mWebView, url);
                Log.i("page_finished", "ok");
                Toast.makeText(getApplicationContext(), "Menu Data Loaded!", Toast.LENGTH_SHORT).show();
            }

            private WebResourceResponse getMenuDataJsonResponse(final Uri uri) {

                Thread runThread = new Thread() {
                    @Override
                    public void run() {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SENDTO);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, uri.toString());
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("de.suitepad.proxyserver");
                        startActivityForResult(sendIntent, 1);
                    }
                };
                runThread.start();
                SystemClock.sleep(500);
                try {
                    stream = new ByteArrayInputStream(json.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return new WebResourceResponse("application/json; charset=UTF-8", "UTF-8", stream);

            }

        });

       mWebView.loadUrl("file:///android_asset/www/webview.html");

    }

    public interface JavascriptCallback {

    }


    public class JavaScriptInterface implements JavascriptCallback {
        private Activity activity;

        public JavaScriptInterface(Activity activiy) {
            this.activity = activiy;
        }

        @JavascriptInterface
        public void displayResponse(String response){
            Log.i("displayResponse: ", response);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                json =data.getStringExtra("json");
                Log.i("recvng json back..", json);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
