package com.aakriti.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aakriti.box.R;
import com.aakriti.box.adapter.ApplianceManagementAdapter;
import com.aakriti.box.adapter.EditApplianceAdapter;
import com.aakriti.box.controller.EditRoom;
import com.aakriti.box.model.Appliance;
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
            adapter.setOnDeleteClickListener(new EditApplianceAdapter.OnDeleteEditListener() {
                @Override
                public void onDeleteClick(View view, Appliance obj, int position) {
                    Toast.makeText(mContext, "onDeleteClick", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEditClick(View view, Appliance obj, int position) {
                    Toast.makeText(mContext, "onEditClick", Toast.LENGTH_SHORT).show();
                }
            });

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

    public void onSaveClick(View view) {
        room.setRoomName(et_roomName.getText().toString().trim());
        if (EditRoom.setRoomName(mContext, room)) {
            Toast.makeText(mContext, "Room Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "Something went wrong!Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteClick(View view) {
        if (EditRoom.deleteRoom(mContext, room)) {
            Toast.makeText(mContext, "Room Deleted Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "Something went wrong!Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

}
