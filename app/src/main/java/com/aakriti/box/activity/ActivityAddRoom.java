package com.aakriti.box.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aakriti.box.R;
import com.aakriti.box.adapter.FoundBTDevicesAdapter;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.AlertDialogCallBack;
import com.aakriti.box.util.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ritwik.rai on 04-04-2018.
 */

public class ActivityAddRoom extends AppCompatActivity {

    private Context mContext;

    private String LOG_TAG = "ActivityAddRoom";
    private int REQUEST_ENABLE_BT = 99;
    private int REQUEST_PAIR_BT = 77;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ListView lv_rooms, lv_pairedDevices;
    private ProgressDialog mProgressDialog;
    private AdapterView.OnItemClickListener onItemClickListener;
    private TextView tv_pairedDeviceMessage;
    private ArrayList<BluetoothDevice> unpairedBTDevices = new ArrayList<>();
    private ArrayList<BluetoothDevice> pairedBTDevices = new ArrayList<>();
    private BluetoothDevice selectedDeviceForPairing;
    String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rooms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Room");
        mContext = ActivityAddRoom.this;
        lv_rooms = (ListView) findViewById(R.id.lv_rooms);
        lv_pairedDevices = (ListView) findViewById(R.id.lv_pairedDevices);
        tv_pairedDeviceMessage = (TextView) findViewById(R.id.tv_pairedDeviceMessage);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mProgressDialog = new ProgressDialog(ActivityAddRoom.this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Looking for available Devices! Please wait...");
        enableBluetoothOnDevice();
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(mContext, "" + rooms.get(i).getBluetoothNameDefault(), Toast.LENGTH_SHORT).show();
                //PAIR DEVICE
                //pairDevice(unpairedBTDevices.get(i));
                // pairDevice2(unpairedBTDevices.get(i));
                Intent intent = new Intent(mContext, ActivitySetUpRoom.class);
                intent.putExtra("room", rooms.get(i));
                startActivity(intent);
            }
        };
    }

    private void enableBluetoothOnDevice() {
        if (mBluetoothAdapter == null) {
            Util.showCallBackMessageWithOkCallback(mContext, "This device does not have a bluetooth adapter", new AlertDialogCallBack() {
                @Override
                public void onSubmit() {
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
        } else if (mBluetoothAdapter.isEnabled()) {
            mProgressDialog.show();
            displayListOfFoundDevices();
            if (getArrayOfAlreadyPairedBluetoothDevices() != null) {
                tv_pairedDeviceMessage.setVisibility(View.GONE);
                FoundBTDevicesAdapter adapter = new FoundBTDevicesAdapter(mContext, getArrayOfAlreadyPairedBluetoothDevices());
                lv_pairedDevices.setAdapter(adapter);
                //lv_pairedDevices.setClickable(false);

            } else {
                tv_pairedDeviceMessage.setVisibility(View.VISIBLE);
            }
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
                Toast.makeText(this, "The user decided to deny bluetooth access", Toast.LENGTH_LONG).show();
            } else {
                Log.e(LOG_TAG, "User allowed bluetooth access!");
            }
        } else if (requestCode == REQUEST_PAIR_BT) {
            Log.e("Pair", "ResultCode: " + resultCode);
            //  Log.e("Pair", ""+data.getData());
        }
    }

    BroadcastReceiver mReceiver;

    private void displayListOfFoundDevices() {
        rooms = new ArrayList<Room>();
        unpairedBTDevices = new ArrayList<>();
        // start looking for bluetooth devices
        mBluetoothAdapter.startDiscovery();

        // Discover new devices
        // Create a BroadcastReceiver for ACTION_FOUND
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    //Toast.makeText(mContext, "Found", Toast.LENGTH_SHORT).show();
                    // Get the bluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    // Get the "RSSI" to get the signal strength as integer,
                    // but should be displayed in "dBm" units
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

                    // Create the device object and add it to the arrayList of devices
                    Room bluetoothObject = new Room();
                    bluetoothObject.setBluetoothNameDefault(device.getName());
                    Log.e("Device", "Device: " + device.getName());
                    bluetoothObject.setMacID(device.getAddress());
                    bluetoothObject.setBluetoothState(device.getBondState());
                    // bluetoothObject.setBluetooth_type(device.getType());    // requires API 18 or higher
                    //bluetoothObject.setBluetooth_uuids(device.getUuids());
                    bluetoothObject.setBluetooth_rssi(rssi);

                    ArrayList<Room> pairedRooms = getArrayOfAlreadyPairedBluetoothDevices();
                    for (Room searchDevice : pairedRooms) {
                        if (!searchDevice.getMacID().equalsIgnoreCase(bluetoothObject.getMacID())) {
                            rooms.add(bluetoothObject);
                            unpairedBTDevices.add(device);
                        }
                    }


                    // 1. Pass context and data to the custom adapter
                    FoundBTDevicesAdapter adapter = new FoundBTDevicesAdapter(getApplicationContext(), rooms);

                    // 2. setListAdapter
                    //setListAdapter(adapter);
                    lv_rooms.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    lv_rooms.setOnItemClickListener(onItemClickListener);
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    private ArrayList<Room> getArrayOfAlreadyPairedBluetoothDevices() {

        ArrayList<Room> arrayOfAlreadyPairedBTDevices = null;

        // Query paired devices
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are any paired devices
        if (pairedDevices.size() > 0) {
            arrayOfAlreadyPairedBTDevices = new ArrayList<Room>();
            pairedBTDevices = new ArrayList<>();

            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Create the device object and add it to the arrayList of devices
                Room bluetoothObject = new Room();
                bluetoothObject.setBluetoothNameDefault(device.getName());
                bluetoothObject.setMacID(device.getAddress());
                bluetoothObject.setBluetoothState(device.getBondState());
                bluetoothObject.setBluetooth_type(device.getType());    // requires API 18 or higher
                bluetoothObject.setBluetooth_uuids(device.getUuids());

                arrayOfAlreadyPairedBTDevices.add(bluetoothObject);
                pairedBTDevices.add(device);
            }
        }

        return arrayOfAlreadyPairedBTDevices;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBluetoothAdapter.cancelDiscovery();
    }

    public void onRefreshClick(View view) {
        mBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
        enableBluetoothOnDevice();
    }

    public void pairDevice(BluetoothDevice device) {

        Toast.makeText(mContext, "Connecting to " + device.getName(), Toast.LENGTH_SHORT).show();
    }

    //For Pairing
    private void pairDevice2(BluetoothDevice device) {
        try {
            Log.e("pairDevice()", "Start Pairing...");
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.e("pairDevice()", "Pairing finished.");
        } catch (Exception e) {
            Log.e("pairDevice()", e.getMessage());
        }
    }


    //For UnPairing
    private void unpairDevice(BluetoothDevice device) {
        try {
            Log.d("unpairDevice()", "Start Un-Pairing...");
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.d("unpairDevice()", "Un-Pairing finished.");
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
    }

    public static class PairingRequest extends BroadcastReceiver {
        public PairingRequest() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                try {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    int pin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0);
                    //the pin in case you need to accept for an specific pin
                    Log.e("PIN", " " + intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0));
                    //maybe you look for a name or address
                    Log.e("Bonded", device.getName());
                    byte[] pinBytes;
                    pinBytes = ("" + pin).getBytes("UTF-8");
                    device.setPin(pinBytes);
                    //setPairing confirmation if neeeded
                    device.setPairingConfirmation(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
