package com.lhy.xposed.mhzs.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.helper.Config;
import com.lhy.xposed.mhzs.helper.HYHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import cn.bmob.v3.update.BmobUpdateAgent;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private TextView versionTextView;
    private TextView supportVersionText;
    private TextView feedbackTextView;
    private TextView updateTextView;
    private TextView githubTextView;
    private boolean isAboutActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        isAboutActivity = true;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));

        SpannableString spannableString = new SpannableString("麻花助手当前版本：" + HYHelper.getVerisonName(this));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        versionTextView.setText(spannableString);

        SpannableString spannableString2 = new SpannableString("支持麻花影视版本：" + Config.SUPPORT_MHYS_VERISON);
        spannableString2.setSpan(colorSpan, 9, spannableString2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        supportVersionText.setText(spannableString2);
    }

    private void initView() {
        setContentView(R.layout.activity_about);
        mToolbar = findViewById(R.id.toolbar_about);
        versionTextView = findViewById(R.id.about_current_version);
        supportVersionText = findViewById(R.id.about_mh_version);
        feedbackTextView = findViewById(R.id.about_feedback);
        updateTextView = findViewById(R.id.about_update);
        githubTextView = findViewById(R.id.about_github);

        feedbackTextView.setOnClickListener(this);
        updateTextView.setOnClickListener(this);
        githubTextView.setOnClickListener(this);

        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkUpdate() {
        //发起自动更新
        BmobUpdateAgent.forceUpdate(AboutActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.about_update:
                checkUpdate();
                break;
            case R.id.about_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/1595901624/mhzs")));
                break;
            default:
                break;
        }
    }

}
