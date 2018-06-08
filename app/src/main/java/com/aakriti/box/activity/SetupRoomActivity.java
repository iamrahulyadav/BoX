package com.aakriti.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aakriti.box.R;
import com.aakriti.box.adapter.ApplianceAdapter;
import com.aakriti.box.model.Appliance;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

import java.util.ArrayList;

/**
 * Created by ritwik on 15-04-2018.
 */

public class SetupRoomActivity extends AppCompatActivity {


    private Context mContext;
    private EditText et_roomName;
    private ListView lv_appliances;
    private ApplianceAdapter adapter;
    private ArrayList<Appliance> appliances;
    public static final int REQ_CODE_ADD_APPLIANCE = 5347; // Constant
    public static final String EXTRA_INTENT = "com.akriti.APPLIANCE_EXTRA_INTENT"; // Constant
    private AdapterView.OnItemClickListener onItemClickListener;
    private Room roomExtra;

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
        setContentView(R.layout.activity_setup_room);
        mContext = SetupRoomActivity.this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("SetUp Room");

        et_roomName = (EditText) findViewById(R.id.et_roomName);
        lv_appliances = (ListView) findViewById(R.id.lv_appliances);

        appliances = new ArrayList<>();
        try {
            roomExtra = (Room) getIntent().getSerializableExtra("room");
        } catch (Exception e) {
            e.printStackTrace();
            Util.showCallBackMessageWithOkCallback(mContext, "Something went wrong!\n Please try again.", new AlertDialogCallBack() {
                @Override
                public void onSubmit() {
                    finish();
                }

                @Override
                public void onCancel() {

                }
            });
        }


        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(mContext, "" + appliances.get(i).getApplianceName(), Toast.LENGTH_SHORT).show();
             /*   Intent intent = new Intent(mContext, SetupRoomActivity.class);
                startActivity(intent);*/
            }
        };


    }

    public void onSaveClick(View view) {
        String roomName = et_roomName.getText().toString().trim();
        if (TextUtils.isEmpty(roomName)) {
            Util.showMessageWithOk(SetupRoomActivity.this, "Please enter the room name.");
            return;
        } else if (appliances.size() < 1) {
            Util.showMessageWithOk(SetupRoomActivity.this, "Please add an Appliance.");
            return;
        }

        ArrayList<Room> rooms = Util.fetchRooms(mContext);
        if (rooms == null)
            rooms = new ArrayList<>();
        Room room = new Room();
        room = roomExtra;
        room.setRoomName(roomName);
        room.setAppliances(appliances);
        rooms.add(room);
        Util.saveRooms(mContext, rooms);
        Toast.makeText(mContext, "Room Configured Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public void onAddApplianceClick(View view) {

        if (appliances.size() == 8) {
            Util.showMessageWithOk(SetupRoomActivity.this, "You have already added 8 Appliances");
            return;
        } else {
            Intent intent = new Intent(mContext, CreateApplianceActivity.class);
            startActivityForResult(intent, REQ_CODE_ADD_APPLIANCE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQ_CODE_ADD_APPLIANCE) {

            Appliance appliance = (Appliance) data.getSerializableExtra(EXTRA_INTENT);
            appliances.add(appliance);

            adapter = new ApplianceAdapter(mContext, appliances);
            lv_appliances.setVisibility(View.VISIBLE);
            lv_appliances.setAdapter(adapter);
            lv_appliances.setClickable(false);
            //lv_appliances.setOnItemClickListener(onItemClickListener);

        }
    }
}
