package com.aakriti.box.controller;

import android.content.Context;
import android.util.Log;

import com.aakriti.box.model.Appliance;
import com.aakriti.box.model.Room;
import com.aakriti.box.util.Util;

import java.util.ArrayList;

public class EditRoom {

    public static void addRoom(Room room) {

    }

    public static boolean deleteRoom(Context mContext, Room room) {
        ArrayList<Room> rooms = Util.fetchRooms(mContext);
        boolean status = false;
        Log.e("rooms", "" + rooms.size());
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomID().equals(room.getRoomID())) {
                Log.e("Room", "Room Identified");
                rooms.remove(i);
                status = true;
            }
        }
        Util.saveRooms(mContext, rooms);
        return status;

    }

    public static boolean setRoomName(Context mContext, Room room) {
        ArrayList<Room> rooms = Util.fetchRooms(mContext);
        ArrayList<Room> editedRooms = new ArrayList<>();
        boolean status = false;
        for (int i = 0; i < rooms.size(); i++) {
            Room editedRoom = rooms.get(i);
            if (rooms.get(i).getRoomID().equals(room.getRoomID())) {
                Log.e("Room", "Room Identified");
                editedRoom.setRoomName(room.getRoomName());
                status = true;
            }
            editedRooms.add(editedRoom);
        }
        Util.saveRooms(mContext, editedRooms);
        return status;
    }

    public static void setApplianceName(Room room, Appliance appliance) {

    }

    public static void setApplianceIcon(Room room, Appliance appliance) {

    }

    public static void setAppliancePort(Room room, Appliance appliance, int portNumber) {

    }
}
