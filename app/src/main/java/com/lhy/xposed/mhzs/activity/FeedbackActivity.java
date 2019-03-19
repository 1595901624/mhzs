package com.lhy.xposed.mhzs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.lhy.xposed.mhzs.R;
import com.lhy.xposed.mhzs.bean.Feedback;
import com.lhy.xposed.mhzs.helper.HYHelper;
import com.lhy.xposed.mhzs.helper.ToastUtils;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private EditText detailEditText;
    private EditText contactEditText;
    private Spinner mSpinner;
    private Button submitBtn;

    private String currentSpinnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void submitFeedback() {
        String detail = detailEditText.getText().toString();
        String contact = contactEditText.getText().toString();

        if (TextUtils.isEmpty(detail.trim())) {
            ToastUtils.toast(this, "请输入问题详细描述！");
            return;
        }

        if (TextUtils.isEmpty(contact.trim())) {
            contact = "未知";
        }

        Feedback feedback = new Feedback();
        feedback.setContact(contact);
        feedback.setDetail(detail);
        feedback.setFramework(currentSpinnerName);
        feedback.setDevice(HYHelper.getDeviceInfo());
        feedback.setVersion(HYHelper.getVerisonName(this));
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.toast(FeedbackActivity.this, "感谢您的反馈！");
                    FeedbackActivity.this.finish();
                } else {
                    ToastUtils.toast(FeedbackActivity.this, "提交失败，请检查网络！");
                }
            }
        });

    }

    private void initView() {
        setContentView(R.layout.activity_feedback);
        mToolbar = findViewById(R.id.toolbar_feedback);
        detailEditText = findViewById(R.id.feedback_detail);
        contactEditText = findViewById(R.id.feedback_contact);
        mSpinner = findViewById(R.id.spinner_framework);
        submitBtn = findViewById(R.id.feedback_submit);
        submitBtn.setOnClickListener(this);

        final String[] values = getResources().getStringArray(R.array.framework);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSpinnerName = values[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mToolbar.setTitle("反馈");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            case R.id.feedback_submit:
                submitFeedback();
                break;
            default:
                break;
        }
    }

}
