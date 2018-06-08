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
 * Created by ritwik on 03-04-2018.
 */

public class RoomAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomAdapter(Context mContext, ArrayList<Room> rooms) {
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
            hView = mInflater.inflate(R.layout.listview_item_room, null);
            ViewHolder holder = new ViewHolder();
            holder.tv_roomName = (TextView) hView.findViewById(R.id.tv_roomName);
           /* holder.classGrade = (TextView) hView.findViewById(R.id.tv_class);
            holder.section = (TextView) hView.findViewById(R.id.tv_section);*/
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();
        holder.tv_roomName.setText(rooms.get(position).getRoomName());
      /*  holder.classGrade.setText(rooms.get(position).classGrade);
        holder.section.setText(rooms.get(position).sectionName);*/
        Log.e("MacID", "Mac ID: " + rooms.get(position).getMacID());
        return hView;
    }

    class ViewHolder {
        TextView tv_roomName;
        /*TextView classGrade;
        TextView section;*/


    }
}
