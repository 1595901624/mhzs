package com.lhy.xposed.mhzs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.lhy.xposed.mhzs.R;

public class HelpActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mToolbar = findViewById(R.id.toolbar_help);
        mWebView = findViewById(R.id.help_web);
        mToolbar.setTitle("使用帮助");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebView.loadUrl("file:///android_asset/help.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
