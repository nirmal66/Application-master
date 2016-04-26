package com.yourapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.businesslogic.OnClickInterface;
import com.yourapp.myapplication.R;

import java.io.File;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class BackupAppAdapter extends RecyclerView.Adapter<BackupAppAdapter.CustomViewHolder> {

    private File[] mappList;
    private Context context;
    private OnClickInterface mClickInterface;


    // Provide a suitable constructor (depends on the kind of dataset)
    public BackupAppAdapter(File[] mappList, Context context,OnClickInterface ClickInterface) {
        this.mappList = mappList;
        this.context = context;
        this.mClickInterface=ClickInterface;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView packageSize;
        public Button Install;
        public ImageView icon;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            packageSize = (TextView)itemView.findViewById(R.id.package_size);
            Install = (Button) itemView.findViewById(R.id.backup);
            icon = (ImageView)itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public BackupAppAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_backup_app, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BackupAppAdapter.CustomViewHolder holder, final int position) {
        final String fileName = mappList[position].getName();
        holder.title.setText(fileName.substring(0, fileName.lastIndexOf(".")));
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(Environment.getExternalStorageDirectory().toString() + "/BatteryMaster/"+fileName, 0);
        holder.packageSize.setText("Version:" + info.versionName +"");
        holder.icon.setImageDrawable(info.applicationInfo.loadIcon(context.getPackageManager()));

        holder.Install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mClickInterface != null){
                    mClickInterface.onItemClick(position, fileName,"install",fileName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mappList.length;
    }

}
