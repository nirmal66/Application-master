package com.yourapp.adapter;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.myapplication.R;

import java.util.List;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class MyRunningAppAdapter extends RecyclerView.Adapter<MyRunningAppAdapter.CustomViewHolder> {

    private List<ActivityManager.RunningAppProcessInfo> mappList;
    private Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRunningAppAdapter(List<ActivityManager.RunningAppProcessInfo> mappList, Context context) {
        this.mappList = mappList;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icon;
        public TextView title;
        public TextView packageName;
        public TextView packageSize;
        public Button uninstall;
        public Button backup;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            packageName = (TextView) itemView.findViewById(R.id.package_name);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);

        }
    }

    @Override
    public MyRunningAppAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_running_app, viewGroup,false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRunningAppAdapter.CustomViewHolder holder, final int position) {
        holder.packageName.setText(mappList.get(position).processName);
        try {
            holder.title.setText(context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(mappList.get(position).processName, PackageManager.GET_META_DATA)));
            holder.icon.setImageDrawable(context.getPackageManager().getApplicationIcon(mappList.get(position).processName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mappList.size();
    }

}
