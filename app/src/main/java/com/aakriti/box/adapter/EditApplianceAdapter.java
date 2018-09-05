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

public class EditApplianceAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Appliance> appliances = new ArrayList<>();
    private OnDeleteEditListener onDeleteEditListener;

    public EditApplianceAdapter(Context mContext, ArrayList<Appliance> appliances) {
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
            hView = mInflater.inflate(R.layout.listview_item_edit_appliance, null);
            final ViewHolder holder = new ViewHolder();
            holder.tv_applianceName = (TextView) hView.findViewById(R.id.tv_applianceName);
            holder.iv_applianceImage = (ImageView) hView.findViewById(R.id.iv_applianceImage);
            holder.iv_edit = (ImageView) hView.findViewById(R.id.iv_edit);
            holder.iv_delete = (ImageView) hView.findViewById(R.id.iv_delete);
            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteEditListener.onEditClick(view, appliances.get(position), position);
                }
            });
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteEditListener.onDeleteClick(view, appliances.get(position), position);
                }
            });
            hView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) hView.getTag();
        holder.tv_applianceName.setText(appliances.get(position).getApplianceName());
        holder.iv_applianceImage.setImageDrawable(mContext.getResources().getDrawable(appliances.get(position).getImageId()));
      /*  holder.classGrade.setText(appliances.get(position).classGrade);
        holder.section.setText(appliances.get(position).sectionName);*/
        Log.e("ApplianceName", "Appliance Name: " + appliances.get(position).getApplianceName());
        return hView;
    }

    class ViewHolder {
        TextView tv_applianceName;
        ImageView iv_applianceImage;
        ImageView iv_edit;
        ImageView iv_delete;
        /*TextView classGrade;
        TextView section;*/


    }

    public interface OnDeleteEditListener {
        void onDeleteClick(View view, Appliance obj, int position);

        void onEditClick(View view, Appliance obj, int position);
    }

    public void setOnDeleteClickListener(final OnDeleteEditListener onDeleteEditClick) {
        this.onDeleteEditListener = onDeleteEditClick;
    }


    public void resetApplianceState() {
        for (int i = 0; i < appliances.size(); i++) {
            appliances.get(i).setOn(false);
        }
        notifyDataSetChanged();
    }
}
