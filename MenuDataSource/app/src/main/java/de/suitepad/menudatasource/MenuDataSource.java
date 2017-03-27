package de.suitepad.menudatasource;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MenuDataSource extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_data_source);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SENDTO.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                sendMenuData(intent); // Handle text being sent
            }
        }
    }

    void sendMenuData(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String menuData = "[\n" +
                "  {\n" +
                "    \"id\": \"58ab140932dfbcc4253b5236\",\n" +
                "    \"name\": \"consectetur\",\n" +
                "    \"price\": 1200,\n" +
                "    \"type\": \"main course\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab140904117a99a73565e4\",\n" +
                "    \"name\": \"adipisicing\",\n" +
                "    \"price\": 1400,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab140950d5905bd0d4752a\",\n" +
                "    \"name\": \"commodo\",\n" +
                "    \"price\": 500,\n" +
                "    \"type\": \"main course\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab14097e1bf08ae9af7829\",\n" +
                "    \"name\": \"labore\",\n" +
                "    \"price\": 1800,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab140961c812ff8022b757\",\n" +
                "    \"name\": \"occaecat\",\n" +
                "    \"price\": 1400,\n" +
                "    \"type\": \"appetizer\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab1409b0148f92565506d0\",\n" +
                "    \"name\": \"incididunt\",\n" +
                "    \"price\": 1300,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab1409a82cddf441e296c7\",\n" +
                "    \"name\": \"ipsum\",\n" +
                "    \"price\": 1500,\n" +
                "    \"type\": \"main course\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab140931b3af85a6a11b10\",\n" +
                "    \"name\": \"consectetur\",\n" +
                "    \"price\": 400,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab1409248dc6f777c816ce\",\n" +
                "    \"name\": \"ut\",\n" +
                "    \"price\": 2500,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab14097fff45868acc9a94\",\n" +
                "    \"name\": \"proident\",\n" +
                "    \"price\": 1300,\n" +
                "    \"type\": \"drink\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58ab14098a4ea9b9491121fa\",\n" +
                "    \"name\": \"in\",\n" +
                "    \"price\": 3700,\n" +
                "    \"type\": \"appetizer\"\n" +
                "  }\n" +
                "]";

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", menuData);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


}
