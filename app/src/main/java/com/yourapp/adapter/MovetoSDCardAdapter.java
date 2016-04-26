package com.yourapp.adapter;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.businesslogic.OnClickInterface;
import com.yourapp.myapplication.R;

import java.io.File;
import java.util.List;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class MovetoSDCardAdapter extends RecyclerView.Adapter<MovetoSDCardAdapter.CustomViewHolder> {

    private List<PackageInfo> mappList;
    private Context context;
    private OnClickInterface mOnClickInterface;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MovetoSDCardAdapter(List<PackageInfo> mappList, Context context,OnClickInterface onClickInterface) {
        this.mappList = mappList;
        this.context = context;
        this.mOnClickInterface= onClickInterface;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icon;
        public TextView title;
        public TextView packageName;
        public Button move;
        public Button backup;
        public CheckBox notMoved;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            packageName = (TextView) itemView.findViewById(R.id.package_name);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            move = (Button) itemView.findViewById(R.id.move);
            notMoved = (CheckBox) itemView.findViewById(R.id.not_moved_chkbox);
        }
    }

    @Override
    public MovetoSDCardAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movetosdcard_app, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovetoSDCardAdapter.CustomViewHolder holder, final int position) {
        final CharSequence applicationPackage;
        applicationPackage = mappList.get(position).packageName;
        try {
            if(getApkFile(applicationPackage.toString()).startsWith("/mnt/") || getApkFile(applicationPackage.toString()).startsWith("/sdcard/")) {
              //application is installed in sdcard(external memory)
                holder.notMoved.setChecked(true);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.packageName.setText(applicationPackage);
        try {
            holder.title.setText(context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(mappList.get(position).packageName, PackageManager.GET_META_DATA)));
            holder.icon.setImageDrawable(context.getPackageManager().getApplicationIcon(mappList.get(position).packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickInterface.onItemClick(position,applicationPackage.toString(),"move","test");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mappList.size();
    }
    public String getApkFile(String packageName) throws PackageManager.NameNotFoundException {
        return (new File(context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir).toString());
    }

}
