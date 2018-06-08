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

public class ActivityChangePass extends AppCompatActivity {

    private Context mContext;
    private EditText et_password, et_confirm_password, et_userName, et_previousPass;

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        mContext = ActivityChangePass.this;
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_previousPass = (EditText) findViewById(R.id.et_previousPass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);

    }

    public void onChangePasswordClick(View view) {
        String userName = et_userName.getText().toString().trim();
        String previousPassword = et_previousPass.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirmPass = et_confirm_password.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Please enter your user name.");
            return;
        } else if (TextUtils.isEmpty(previousPassword)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Please enter your previous password.");
            return;
        } else if (!userName.equalsIgnoreCase(Util.fetchUserClass(mContext).getName())) {
            Util.showMessageWithOk(ActivityChangePass.this, "Please enter a correct user name.");
            return;
        } else if (!previousPassword.contentEquals(Util.fetchUserClass(mContext).getPassword())) {
            Util.showMessageWithOk(ActivityChangePass.this, "Previous password is not correct.");
            return;
        } else if (TextUtils.isEmpty(password)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Please enter a password.");
            return;
        } else if (TextUtils.isEmpty(confirmPass)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Confirm Password empty.");
            return;
        } else if (password.length() < 8) {
            Util.showMessageWithOk(ActivityChangePass.this, "Password is too short.");
            return;
        } else if (!Util.isPasswordValid(password)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Password should have atleast one alphabet, one number and one special character.");
            return;
        } else if (!password.contentEquals(confirmPass)) {
            Util.showMessageWithOk(ActivityChangePass.this, "Entered Passwords do not match.");
            return;
        }

        // Validation Done
        UserClass userClass = Util.fetchUserClass(mContext);
        userClass.setPassword(password);
        Util.saveUserClass(mContext, userClass);
        Util.showCallBackMessageWithOkCallback(mContext, "Password changed successfully", new AlertDialogCallBack() {
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
