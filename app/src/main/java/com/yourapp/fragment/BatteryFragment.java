package com.yourapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.yourapp.adapter.MyBatteryAdapter;
import com.yourapp.myapplication.BatteryStatus;
import com.yourapp.myapplication.HomeActivity;
import com.yourapp.myapplication.R;

public class BatteryFragment extends Fragment {

    private Toolbar mToolbar;
    private DonutProgress donutProgress;
    private int level;
    private FloatingActionButton settings;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String title[] = {"Connected Via:", "Status:", "Voltage:", "Temperature:", "Last plugin status:", "Last plugout status:"};
    private String detail[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        donutProgress = (DonutProgress) view.findViewById(R.id.donut_progress);
        settings = (FloatingActionButton) view.findViewById(R.id.fab);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity) getActivity()).fragment(new AppManagerFragment(), "App_Fragment");
            }
        });

        //Register battery receiver
        getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));


    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            BatteryStatus bs = new BatteryStatus(ctxt);
//battery level
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            donutProgress.setProgress(level);

            //battery info
            int plugged = intent.getIntExtra("plugged", -1);
            int health = intent.getIntExtra("health", 0);
            int status = intent.getIntExtra("status", 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);


            detail = new String[]{getPlugTypeString(plugged), getStatusString(status), (float)voltage/1000 + " V", (float)temperature/10 + " C", bs.getlastPlugin(), bs.getlastPlugout()};

            // specify an adapter (see also next example)
            mAdapter = new MyBatteryAdapter(title, detail);
            mRecyclerView.setAdapter(mAdapter);

        }
    };


    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                break;
        }
        return plugType;
    }

    private String getHealthString(int health) {
        String healthString = "Unknown";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "Over Heat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "Failure";
                break;
        }
        return healthString;
    }

    private String getStatusString(int status) {
        String statusString = "Unknown";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "Discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "Not Charging";
                break;
        }
        return statusString;
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //unregister battery receiver
        try {
            getActivity().unregisterReceiver(mBatInfoReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
