package com.daniel.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private CheckBox mRememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mAccount = findViewById(R.id.et_accout);
        mPassword = findViewById(R.id.et_password);
        mRememberPass = findViewById(R.id.remember_pass);
        mLogin = findViewById(R.id.btn_login);

        boolean isRemember = mPreferences.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号和密码都设置到文本框中
            String account = mPreferences.getString("account", "");
            String password = mPreferences.getString("password", "");
            mAccount.setText(account);
            mPassword.setText(password);
            mRememberPass.setChecked(true);
        }
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                //如果账号是admin，且密码是123456，就认为登陆成功
                if (account.equals("admin") && password.equals("123456")) {
                    mEditor = mPreferences.edit();
                    if (mRememberPass.isChecked()) { //检查复选框是否被选中
                        mEditor.putBoolean("remember_password", true);
                        mEditor.putString("account", account);
                        mEditor.putString("password", password);
                    } else {
                        mEditor.clear();
                    }
                    mEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
