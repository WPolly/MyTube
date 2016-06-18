package com.tcl.lishanwang.mytube.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.tcl.lishanwang.mytube.bean.LoginRequestBean;
import com.tcl.lishanwang.mytube.bean.RegisterResponseBean;
import com.tcl.lishanwang.mytube.config.Constants;
import com.tcl.lishanwang.mytube.http.RetrofitClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

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
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mSp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        mEtEmail.setVisibility(View.INVISIBLE);
        mEtUserName.requestFocus();
        String username = mSp.getString("username", "");
        mEtUserName.setText(username);
        mEtUserName.setSelection(username.length());
        String password = mSp.getString("password", "");
        mEtPassword.setText(password);
        mEtPassword.setSelection(password.length());

    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                final String username = mEtUserName.getText().toString().trim();
                final String password = mEtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    mEtUserName.setError("用户名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mEtPassword.setError("密码不能为空");
                    return;
                }

                LoginRequestBean loginRequestBean = new LoginRequestBean(username, password);
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍后..");
                progressDialog.show();
                RetrofitClient.getInstance().getTubeService().login(loginRequestBean).enqueue(new Callback<RegisterResponseBean>() {
                    @Override
                    public void onResponse(Call<RegisterResponseBean> call, Response<RegisterResponseBean> response) {
                        String accessToken = response.body().response_data.access_token;
                        String userId = response.body().response_data.user_id;
                        mSp.edit().putBoolean(Constants.KEY.SP_IS_LOGIN, true)
                                .putString(Constants.KEY.SP_ACCESS_TOKEN, accessToken)
                                .putString(Constants.KEY.SP_USER_ID, userId)
                                .putString(Constants.KEY.SP_USERNAME, username)
                                .putString(Constants.KEY.SP_PASSWORD, password)
                                .apply();
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseBean> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "登录失败,请重试", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

                break;
            case R.id.tv_register:
                gotoOtherActivity(RegisterActivity.class);
                break;
        }
    }

    private void gotoOtherActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
