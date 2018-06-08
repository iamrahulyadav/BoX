package com.aakriti.box.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aakriti.box.R;
import com.aakriti.box.adapter.RoomAdapter;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

import java.util.ArrayList;

/**
 * Created by ritwik on 03-05-2018.
 */

public class DashboardActivity extends AppCompatActivity {

    private Context mContext;
    private ListView lv_rooms;
    private ArrayList<Room> rooms = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private FloatingActionButton fab_add_room;
    private BluetoothAdapter mBluetoothAdapter;
    private String LOG_TAG = "DashboardActivity";
    private int REQUEST_ENABLE_BT = 99;
    private TextView tv_NoRooms;
    private TextView tv_homeName;
    private TextView tv_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = DashboardActivity.this;
        lv_rooms = (ListView) findViewById(R.id.lv_rooms);
        tv_NoRooms = (TextView) findViewById(R.id.tv_NoRooms);
        tv_homeName = (TextView) findViewById(R.id.tv_homeName);
        tv_header = (TextView) findViewById(R.id.tv_header);
        fab_add_room = (FloatingActionButton) findViewById(R.id.fab_add_room);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bindView();

    }

    private void bindView() {

        if (Util.fetchUserClass(mContext) != null) {
            tv_homeName.setText("Welcome to " + Util.fetchUserClass(mContext).getHomeName());
        }

        rooms = Util.fetchRooms(mContext);
        if (rooms != null && rooms.size() > 0) {
            tv_header.setVisibility(View.VISIBLE);
            roomAdapter = new RoomAdapter(mContext, rooms);
            lv_rooms.setAdapter(roomAdapter);
            tv_NoRooms.setVisibility(View.GONE);
            lv_rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Toast.makeText(mContext, "BluetoothName: " + rooms.get(i).getBluetoothNameDefault(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ManageRoomActivity.class);
                    intent.putExtra("room", rooms.get(i));
                    startActivity(intent);

                }
            });

        } else {
            /*Util.showCallBackMessageWithOkCancel(mContext, "No Rooms Found!\n\nPress OK to add one  OR\nPress CANCEL to exit.", new AlertDialogCallBack() {
                @Override
                public void onSubmit() {
                   *//* Intent intent = new Intent(mContext, AddRoomActivity.class);
                    startActivity(intent);*//*
                }

                @Override
                public void onCancel() {
                    finish();
                }
            });*/
            tv_header.setVisibility(View.INVISIBLE);
            tv_NoRooms.setVisibility(View.VISIBLE);
            Util.showMessageWithOk(DashboardActivity.this, "No Rooms Found!\n\nPress Add button to add one");
        }

        fab_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddRoomActivity.class);
                startActivity(intent);
            }
        });

        enableBluetoothOnDevice();
    }

    private void enableBluetoothOnDevice() {
        if (mBluetoothAdapter == null) {
            Log.e(LOG_TAG, "This device does not have a bluetooth adapter");
            Util.showCallBackMessageWithOkCallback(mContext, "This device does not have a bluetooth adapter", new AlertDialogCallBack() {
                @Override
                public void onSubmit() {
                    finish();
                }

                @Override
                public void onCancel() {

                }
            });
        }
        // Check to see if bluetooth is enabled. Prompt to enable it
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == 0) {
                // If the resultCode is 0, the user selected "No" when prompt to
                // allow the app to enable bluetooth.
                // You may want to display a dialog explaining what would happen if
                // the user doesn't enable bluetooth.
                Toast.makeText(this, "Bluetooth Access denied by user", Toast.LENGTH_LONG).show();
            } else
                Log.e(LOG_TAG, "User allowed bluetooth access!");
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}
