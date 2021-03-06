package com.yourapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yourapp.adapter.BackupAppAdapter;
import com.yourapp.businesslogic.AnalyticsApplication;
import com.yourapp.businesslogic.OnClickInterface;
import com.yourapp.businesslogic.Utilities;
import com.yourapp.myapplication.R;

import java.io.File;

public class BackupAppFragment extends Fragment implements OnClickInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView backup;
    private ImageView backupIcon;
    private Tracker mTracker;
    private Utilities utilities = new Utilities();
    File folder;
    File[] mappList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_backup, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(utilities.isExternalStorageWritable())
        {
            folder  = new File(Environment.getExternalStorageDirectory().toString() + "/BatteryMaster/");
        }
        else
        {
            folder  = new File(Environment.getDataDirectory().toString() + "/BatteryMaster/");
        }
        mappList =  folder.listFiles();
        mAdapter = new BackupAppAdapter(mappList, getActivity(), this);
        Log.d("oncreate", "create running");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_app);
        backup = (TextView) view.findViewById(R.id.no_backup);
        backupIcon = (ImageView) view.findViewById(R.id.no_backup_ic);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (mappList != null) {
            mRecyclerView.setAdapter(mAdapter);
            backup.setVisibility(View.GONE);
            backupIcon.setVisibility(View.GONE);

        } else {
            backup.setVisibility(View.VISIBLE);
            backupIcon.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onResume() {
        mTracker.setScreenName("Restore Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    @Override
    public void onItemClick(int position, String fileName, String message, String applicationLabel) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (utilities.isExternalStorageWritable()) {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/BatteryMaster/" + fileName)), "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getDataDirectory() + "/BatteryMaster/" + fileName)), "application/vnd.android.package-archive");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}