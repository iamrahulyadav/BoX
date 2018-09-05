package com.aakriti.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aakriti.box.R;
import com.aakriti.box.adapter.RoomSetAdapter;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

import java.util.ArrayList;

/**
 * Created by ritwik on 15-06-2018.
 */

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;
    private ListView lv_devices;
    private ArrayList<Room> rooms = new ArrayList<>();
    private RoomSetAdapter roomAdapter;
    private TextView tv_NoRooms;
    private ImageView iv_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = SettingsActivity.this;

        lv_devices = findViewById(R.id.lv_devices);
        tv_NoRooms = (TextView) findViewById(R.id.tv_NoRooms);
        iv_room = (ImageView) findViewById(R.id.iv_room);
        rooms = Util.fetchRooms(mContext);

        if (rooms != null && rooms.size() > 0) {
            // tv_header.setVisibility(View.VISIBLE);
            roomAdapter = new RoomSetAdapter(mContext, rooms);
            lv_devices.setAdapter(roomAdapter);
            tv_NoRooms.setVisibility(View.GONE);
            lv_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long length) {
                    Intent intent = new Intent(mContext, RoomEditActivity.class);
                    intent.putExtra("selectedRoom", rooms.get(position));
                    startActivity(intent);
                }
            });

        } else {
            tv_NoRooms.setVisibility(View.VISIBLE);
            Util.showCallBackMessageWithOkCallback(mContext, "No Rooms Found!", new AlertDialogCallBack() {
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

    }
}
