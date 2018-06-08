package com.aakriti.box.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.aakriti.box.R;
import com.aakriti.box.model.UserClass;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

/**
 * Created by ritwik on 03-05-2018.
 */

public class ActivityRegister extends AppCompatActivity {

    private Context mContext;
    private EditText et_homeName, et_userName, et_password, et_confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = ActivityRegister.this;

        et_homeName = (EditText) findViewById(R.id.et_homeName);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmPassword = (EditText) findViewById(R.id.et_confirmPassword);


    }

    public void onRegisterClick(View view) {

        String homeName = et_homeName.getText().toString().trim();
        String userName = et_userName.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirmPass = et_confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(homeName)) {
            Util.showMessageWithOk(ActivityRegister.this, "Please enter your house name");
            return;
        } else if (homeName.length() < 4) {
            Util.showMessageWithOk(ActivityRegister.this, "Home name is too short");
            return;
        } else if (TextUtils.isEmpty(userName)) {
            Util.showMessageWithOk(ActivityRegister.this, "Please enter a user name");
            return;
        } else if (TextUtils.isEmpty(password)) {
            Util.showMessageWithOk(ActivityRegister.this, "Please enter a password");
            return;
        } else if (TextUtils.isEmpty(confirmPass)) {
            Util.showMessageWithOk(ActivityRegister.this, "Confirm Password empty");
            return;
        } else if (password.length() < 8) {
            Util.showMessageWithOk(ActivityRegister.this, "Password is too short");
            return;
        } else if (!Util.isPasswordValid(password)) {
            Util.showMessageWithOk(ActivityRegister.this, "Password should have atleast one alphabet, one number and one special character.");
            return;
        } else if (!password.equals(confirmPass)) {
            Util.showMessageWithOk(ActivityRegister.this, "Entered Passwords do not match");
            return;
        }
        UserClass userClass = new UserClass();
        userClass.setName(userName);
        userClass.setPassword(password);
        userClass.setHomeName(homeName);
        Util.saveUserClass(mContext, userClass);

        Util.showCallBackMessageWithOkCallback(mContext, "Registration done successfully", new AlertDialogCallBack() {
            @Override
            public void onSubmit() {
                finish();
            }

            @Override
            public void onCancel() {

            }
        });

    }
}
