package com.aakriti.box.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aakriti.box.R;
import com.aakriti.box.model.Appliance;

import java.util.ArrayList;

/**
 * Created by ritwik on 03-04-2018.
 */

public class ApplianceManagementAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Appliance> appliances = new ArrayList<>();
    private OnSwitchClick onSwitchClick;

    public ApplianceManagementAdapter(Context mContext, ArrayList<Appliance> appliances) {
        Log.d("ListAdapter", "Size: " + appliances.size());
        System.out.println("ListAdapter Size: " + appliances.size());
        this.mContext = mContext;
        this.appliances = appliances;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return appliances.size();
    }

    @Override
    public Object getItem(int position) {
        return appliances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View hView = convertView;
        if (convertView == null) {
            hView = mInflater.inflate(R.layout.listview_item_room_controls, null);
            final ViewHolder holder = new ViewHolder();
            holder.tv_applianceName = (TextView) hView.findViewById(R.id.tv_applianceName);
            holder.iv_applianceImage = (ImageView) hView.findViewById(R.id.iv_applianceImage);
            holder.iv_power_on_off = (ImageView) hView.findViewById(R.id.iv_power_on_off);
            holder.tv_on_off_status = (TextView) hView.findViewById(R.id.tv_on_off_status);

           /* holder.classGrade = (TextView) hView.findViewById(R.id.tv_class);
            holder.section = (TextView) hView.findViewById(R.id.tv_section);*/
            holder.iv_power_on_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSwitchClick.onTutorialClick(view, appliances.get(position), position);
                    if (appliances.get(position).isOn()) {
                        appliances.get(position).setOn(false);
                        holder.iv_power_on_off.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_switch_off));
                        holder.tv_on_off_status.setText("OFF");
                    } else {
                        appliances.get(position).setOn(true);
                        holder.iv_power_on_off.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_switch_on));
                        holder.tv_on_off_status.setText("ON");
                    }
                }
            });
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();
        holder.tv_applianceName.setText(appliances.get(position).getApplianceName());
        holder.iv_applianceImage.setImageDrawable(mContext.getResources().getDrawable(appliances.get(position).getImageId()));
        if (appliances.get(position).isOn()) {
            holder.tv_on_off_status.setText("ON");
            appliances.get(position).setOn(true);
            holder.iv_power_on_off.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_switch_on));
        } else if (!appliances.get(position).isOn()) {
            holder.tv_on_off_status.setText("OFF");
            appliances.get(position).setOn(false);
            holder.iv_power_on_off.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_switch_off));
        }

      /*  holder.classGrade.setText(appliances.get(position).classGrade);
        holder.section.setText(appliances.get(position).sectionName);*/
        Log.e("ApplianceName", "Appliance Name: " + appliances.get(position).getApplianceName());
        return hView;
    }

    class ViewHolder {
        TextView tv_applianceName;
        ImageView iv_applianceImage;
        ImageView iv_power_on_off;
        TextView tv_on_off_status;
        /*TextView classGrade;
        TextView section;*/


    }

    public interface OnSwitchClick {
        void onTutorialClick(View view, Appliance obj, int position);
    }

    public void setOnSwitchClickListener(final OnSwitchClick onSwitchClick) {
        this.onSwitchClick = onSwitchClick;
    }

    public void resetApplianceState() {
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).setOn(false);
        }
        notifyDataSetChanged();
    }
}
