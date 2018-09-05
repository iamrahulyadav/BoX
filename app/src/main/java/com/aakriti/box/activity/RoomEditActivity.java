package com.aakriti.box.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import com.aakriti.box.R;
import com.aakriti.box.adapter.ApplianceManagementAdapter;
import com.aakriti.box.adapter.EditApplianceAdapter;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

public class RoomEditActivity extends AppCompatActivity {

    private Context mContext;
    private ListView lv_appliances;
    private Room room;
    private EditText et_roomName;
    private EditApplianceAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        mContext = RoomEditActivity.this;
        initView();
    }

    private void initView() {
        lv_appliances = (ListView) findViewById(R.id.lv_appliances);
        et_roomName = (EditText) findViewById(R.id.et_roomName);
        room = (Room) getIntent().getSerializableExtra("selectedRoom");
        if (room != null) {
            et_roomName.setText(room.getRoomName());
            adapter = new EditApplianceAdapter(mContext, room.getAppliances());
            lv_appliances.setAdapter(adapter);


        } else {
            Util.showCallBackMessageWithOkCallback(mContext, "Room could not be loaded. Please try again.", new AlertDialogCallBack() {
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
}
