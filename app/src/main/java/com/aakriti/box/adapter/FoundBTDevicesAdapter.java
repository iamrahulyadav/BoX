package com.aakriti.box.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aakriti.box.R;
import com.aakriti.box.model.Room;

import java.util.ArrayList;

/**
 * Created by ritwik on 05-05-2018.
 */

public class FoundBTDevicesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Room> rooms = new ArrayList<>();

    public FoundBTDevicesAdapter(Context mContext, ArrayList<Room> rooms) {
        Log.d("ListAdapter", "Size: " + rooms.size());
        System.out.println("ListAdapter Size: " + rooms.size());
        this.mContext = mContext;
        this.rooms = rooms;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View hView = convertView;
        if (convertView == null) {
            hView = mInflater.inflate(R.layout.listview_item_found_devices, null);
            FoundBTDevicesAdapter.ViewHolder holder = new FoundBTDevicesAdapter.ViewHolder();
            holder.tv_roomName = (TextView) hView.findViewById(R.id.tv_roomName);
            holder.tv_macID = (TextView) hView.findViewById(R.id.tv_macID);
            holder.tv_rssi = (TextView) hView.findViewById(R.id.tv_rssi);
            holder.tv_bondState = (TextView) hView.findViewById(R.id.tv_bondState);
            hView.setTag(holder);
        }

        FoundBTDevicesAdapter.ViewHolder holder = (FoundBTDevicesAdapter.ViewHolder) hView.getTag();
        holder.tv_roomName.setText(rooms.get(position).getBluetoothNameDefault() + " / " + rooms.get(position).getMacID());
        holder.tv_macID.setText(rooms.get(position).getMacID());
        holder.tv_rssi.setText("" + rooms.get(position).getBluetooth_rssi());
        holder.tv_bondState.setText("" + rooms.get(position).getBluetoothState());
        Log.e("MacID", "Mac ID: " + rooms.get(position).getMacID());
        return hView;
    }

    class ViewHolder {
        TextView tv_roomName;
        TextView tv_macID;
        TextView tv_rssi;
        TextView tv_bondState;
    }
}

