package com.aakriti.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aakriti.box.R;
import com.aakriti.box.model.UserClass;
import com.aakriti.box.util.Util;

/**
 * Created by ritwik on 2-04-2018.
 */

public class SigninActivity extends AppCompatActivity {

    private EditText input_username, input_password;
    private Button btn_login, btn_register;
    private CheckBox chbx_autoLogin;
    private TextView tv_forgot_pass;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mContext = SigninActivity.this;
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        chbx_autoLogin = (CheckBox) findViewById(R.id.chbx_autoLogin);
        tv_forgot_pass = (TextView) findViewById(R.id.tv_forgot_pass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserClass userClass = Util.fetchUserClass(mContext);
                if (userClass != null) {
                    String userName = input_username.getText().toString().trim();
                    String password = input_password.getText().toString().trim();
                    if (TextUtils.isEmpty(userName)) {
                        Util.showMessageWithOk(SigninActivity.this, "Please enter your userName");
                        return;
                    } else if (TextUtils.isEmpty(password)) {
                        Util.showMessageWithOk(SigninActivity.this, "Please enter your password");
                        return;
                    } else if (password.length() < 8) {
                        Util.showMessageWithOk(SigninActivity.this, "Please enter the correct password");
                        return;
                    } else if (!userName.equalsIgnoreCase(userClass.getName())) {
                        Util.showMessageWithOk(SigninActivity.this, "Please enter the correct user name");
                        return;
                    } else if (!password.contentEquals(userClass.getPassword())) {
                        Util.showMessageWithOk(SigninActivity.this, "Please enter the correct password");
                        return;
                    }
                    // Save AutoLogin state
                    userClass.setIsLoggedin(chbx_autoLogin.isChecked());
                    Util.saveUserClass(mContext, userClass);
                    // Credentials Verified
                    Intent intent = new Intent(mContext, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {

                    Util.showMessageWithOk(SigninActivity.this, "Not registered!\nPlease register first.");
                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        tv_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Util.fetchUserClass(mContext) != null) {
                    Intent intent = new Intent(mContext, ChangePassActivity.class);
                    startActivity(intent);
                } else {
                    Util.showMessageWithOk(SigninActivity.this, "Not registered!\nPlease register first.");
                }

            }
        });
    }
}
