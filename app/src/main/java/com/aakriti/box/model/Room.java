package com.aakriti.box.model;

import android.os.ParcelUuid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ritwik on 03-05-2018.
 */

public class Room implements Serializable {

    private String roomName = "";

    private String macID = "";

    private String bluetoothNameDefault = "";

    private String bluetoothNameCustom = "";

    private int bluetoothState;

    private int bluetooth_type;

    private ParcelUuid[] bluetooth_uuids;

    private int bluetooth_rssi;


    private ArrayList<Appliance> appliances = new ArrayList<>();

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMacID() {
        return macID;
    }

    public void setMacID(String macID) {
        this.macID = macID;
    }

    public String getBluetoothNameDefault() {
        return bluetoothNameDefault;
    }

    public void setBluetoothNameDefault(String bluetoothNameDefault) {
        this.bluetoothNameDefault = bluetoothNameDefault;
    }

    public String getBluetoothNameCustom() {
        return bluetoothNameCustom;
    }

    public void setBluetoothNameCustom(String bluetoothNameCustom) {
        this.bluetoothNameCustom = bluetoothNameCustom;
    }

    public int getBluetoothState() {
        return bluetoothState;
    }

    public void setBluetoothState(int bluetoothState) {
        this.bluetoothState = bluetoothState;
    }

    public int getBluetooth_type() {
        return bluetooth_type;
    }

    public void setBluetooth_type(int bluetooth_type) {
        this.bluetooth_type = bluetooth_type;
    }

    public ParcelUuid[] getBluetooth_uuids() {
        return bluetooth_uuids;
    }

    public void setBluetooth_uuids(ParcelUuid[] bluetooth_uuids) {
        this.bluetooth_uuids = bluetooth_uuids;
    }

    public int getBluetooth_rssi() {
        return bluetooth_rssi;
    }

    public void setBluetooth_rssi(int bluetooth_rssi) {
        this.bluetooth_rssi = bluetooth_rssi;
    }

    public ArrayList<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(ArrayList<Appliance> appliances) {
        this.appliances = appliances;
    }
}
