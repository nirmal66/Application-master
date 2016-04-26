package com.yourapp.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourapp.businesslogic.OnClickInterface;
import com.yourapp.fragment.AppManagerFragment;
import com.yourapp.myapplication.DialogCaller;
import com.yourapp.myapplication.HomeActivity;
import com.yourapp.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Nirmal on 27-10-2015.
 */
public class MyApplicationAdapter extends RecyclerView.Adapter<MyApplicationAdapter.CustomViewHolder> {

    private List<ApplicationInfo> mappList;
    private Context context;
    private OnClickInterface mClickInterface;

 // Provide a suitable constructor (depends on the kind of dataset)
    public MyApplicationAdapter(List<ApplicationInfo> mappList, Context context, OnClickInterface clickInterface) {
        this.mappList = mappList;
        this.context = context;
        this.mClickInterface=clickInterface;
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
            packageSize = (TextView) itemView.findViewById(R.id.package_size);
            uninstall = (Button) itemView.findViewById(R.id.uninstall);
            backup = (Button) itemView.findViewById(R.id.backup);
        }
    }

    @Override
    public MyApplicationAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_app, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyApplicationAdapter.CustomViewHolder holder, final int position) {

        final CharSequence applicationLabel;
        final CharSequence applicationPackage;

        applicationLabel = mappList.get(position).loadLabel(context.getPackageManager());
        applicationPackage = mappList.get(position).packageName;

        Log.d("insapp", mappList.get(position).toString());

        holder.icon.setImageDrawable(mappList.get(position).loadIcon(context.getPackageManager()));
        holder.title.setText(applicationLabel);
        holder.packageName.setText(applicationPackage);
        float sizeApp = 0;
        try {
            sizeApp = getApkSize(mappList.get(position).packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.packageSize.setText("APK Size: " + sizeApp + "MB");


        holder.uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                if(mClickInterface != null){
                    mClickInterface.onItemClick(position, applicationPackage.toString(),"delete",applicationLabel.toString());
                }
            }
        });

        holder.backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickInterface != null){
                    mClickInterface.onItemClick(position, applicationPackage.toString(),"backup",applicationLabel.toString());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mappList.size();
    }

    //http://stackoverflow.com/a/21037676
    public float getApkSize(String packageName) throws PackageManager.NameNotFoundException {
        Log.d("test", new File(context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir) + "");
        return (new File(context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir).length()) / (1024 * 1024);
    }
}
