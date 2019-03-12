package com.lhy.xposed.mhzs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.helper.Config;
import com.lhy.xposed.mhzs.helper.HYHelper;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.ToastUtils;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;

import java.io.IOException;

public class HelpActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    private WebView mWebView;
    private final int GET_MARKDOWN_SUCCESS = 0x1;
    private final int GET_MARKDOWN_FAILED = 0x0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_MARKDOWN_FAILED) {
                ToastUtils.toast(HelpActivity.this, "请检查网络是否接通！");
                progressBar.setVisibility(View.GONE);
            } else if (msg.what == GET_MARKDOWN_SUCCESS) {
                progressBar.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
                showHtml((String) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mToolbar = findViewById(R.id.toolbar_help);
        mWebView = findViewById(R.id.help_web);
        progressBar = findViewById(R.id.progress_help);

        mToolbar.setTitle("使用帮助");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getHelpMarkdown();

//        mWebView.loadUrl("file:///android_asset/help.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 服务器获取帮助md
     */
    public void getHelpMarkdown() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(Config.HELP_MARKDOWN_URL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(GET_MARKDOWN_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what = GET_MARKDOWN_SUCCESS;
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }


    /**
     * 展示网页
     */
    private void showHtml(String md) {
        DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(true,
                Extensions.NONE);
        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        Node document = parser.parse(md);
        String html = renderer.render(document);

//        LogUtil.e(html);

        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.loadData(html, "text/html; charset=UTF-8", null);
    }


}
