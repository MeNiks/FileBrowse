package com.niks.myfilebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.niks.baseutils.BaseAppCompatActivity;
import com.niks.filebrowse.ActivityBrowseFile;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void browseInternal(View v) {

        Intent i = new Intent(MainActivity.this, ActivityBrowseFile.class);
        i.putExtra("path", "/mnt/sdcard");
        startActivity(i);
    }

    public void browseExternal(View v) {

        Intent i = new Intent(MainActivity.this, ActivityBrowseFile.class);
        i.putExtra("path", "/mnt/extSdCard");
        startActivity(i);
    }
}
