package com.tcl.lishanwang.mytube.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.bean.RegisterRequestBean;
import com.tcl.lishanwang.mytube.bean.RegisterResponseBean;
import com.tcl.lishanwang.mytube.http.RetrofitClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.et_user_name)
    EditText mEtUserName;
    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.tv_register)
    TextView mTvRegister;
    @InjectView(R.id.et_email)
    EditText mEtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mEtUserName.setText("");
        mEtPassword.setText("");
        mBtnLogin.setText("注册");
        mTvRegister.setVisibility(View.GONE);
        mEtUserName.requestFocus();
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = mEtUserName.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                String email = mEtEmail.getText().toString().trim();
                String serial = Build.SERIAL;

                if (TextUtils.isEmpty(username)) {
                    mEtUserName.setError("用户名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mEtPassword.setError("密码不能为空");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEtPassword.setError("email不能为空");
                    return;
                }

                RegisterRequestBean registerRequestBean = new RegisterRequestBean(username, password, email, serial);
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍后..");
                progressDialog.show();
                RetrofitClient.getInstance().getTubeService().register(registerRequestBean).enqueue(new Callback<RegisterResponseBean>() {
                    @Override
                    public void onResponse(Call<RegisterResponseBean> call, Response<RegisterResponseBean> response) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseBean> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

                break;
            case R.id.tv_register:
                break;
        }
    }
}
