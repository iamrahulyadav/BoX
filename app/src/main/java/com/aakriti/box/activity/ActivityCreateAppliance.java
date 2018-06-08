package com.aakriti.box.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.aakriti.box.R;
import com.aakriti.box.model.Appliance;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

/**
 * Created by ritwik on 06-05-2018.
 */

public class ActivityCreateAppliance extends AppCompatActivity {

    private Context mContext;
    private EditText et_ApplianceName;
    private RadioGroup rg_image;
    private int imageId;
    private Appliance appliance;

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
        setContentView(R.layout.activity_create_appliances);
        mContext = ActivityCreateAppliance.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appliance");
        et_ApplianceName = (EditText) findViewById(R.id.et_ApplianceName);
        rg_image = (RadioGroup) findViewById(R.id.rg_image);

        rg_image.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {

                    case R.id.rb_bulb:
                        imageId = R.drawable.ic_bulb;
                        break;
                    case R.id.rb_plug:
                        imageId = R.drawable.ic_plug;
                        break;
                    case R.id.rb_fan:
                        imageId = R.drawable.ic_fan;
                        break;
                    case R.id.rb_power_chord:
                        imageId = R.drawable.ic_power_chord;
                        break;

                }
            }
        });

    }

    public void onSaveClick(View view) {

        String applianceName = et_ApplianceName.getText().toString().trim();
        if (TextUtils.isEmpty(applianceName)) {
            Util.showMessageWithOk(ActivityCreateAppliance.this, "Please enter Appliance Name");
            return;
        } else if (rg_image.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Util.showMessageWithOk(ActivityCreateAppliance.this, "Please select an Image");
            return;
        } else {
            // one of the radio buttons is checked
            appliance = new Appliance();
            appliance.setApplianceName(applianceName);
            appliance.setImageId(imageId);
            Util.showCallBackMessageWithOkCallback(mContext, "Appliance Added Successfully.", new AlertDialogCallBack() {
                @Override
                public void onSubmit() {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(ActivitySetUpRoom.EXTRA_INTENT, appliance);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }

                @Override
                public void onCancel() {

                }
            });
        }

    }
}
