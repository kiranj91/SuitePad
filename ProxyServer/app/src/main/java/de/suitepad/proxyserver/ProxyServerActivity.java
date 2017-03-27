package de.suitepad.proxyserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ProxyServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_server);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SENDTO.equals(action) && type != null) {

            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Intent sendRequestIntent = new Intent();
            sendRequestIntent.setAction(Intent.ACTION_SENDTO);
            sendRequestIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            sendRequestIntent.setType("text/plain");
            sendRequestIntent.setPackage("de.suitepad.menudatasource");
            startActivityForResult(sendRequestIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("json", result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
