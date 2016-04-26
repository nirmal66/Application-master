package com.yourapp.fragment;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourapp.adapter.MyRunningAppAdapter;
import com.yourapp.adapter.NetworkUsageAdapter;
import com.yourapp.businesslogic.CustomNetworkUsage;
import com.yourapp.myapplication.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NetworkUsageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ApplicationInfo> mappList;
    private NetworkUsageAdapter mnetworkUsageAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mappList = getActivity().getPackageManager().getInstalledApplications(0);
        mnetworkUsageAdapter= new NetworkUsageAdapter();
        Log.d("oncreate", "create running");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_app);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mnetworkUsageAdapter);
        updateList(mappList);
    }

    public void updateList(List<ApplicationInfo> appList)
    {
      List<CustomNetworkUsage> customNetworkUsageList = new ArrayList<>();
        for(int i=0; i<appList.size();i++)
        {
            CustomNetworkUsage customNetworkUsage = new CustomNetworkUsage();
            try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(mappList.get(i).packageName, PackageManager.GET_PERMISSIONS);
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    for (String permissionName : requestedPermissions) {
                        if(permissionName.equals("android.permission.INTERNET"))
                        {
                            customNetworkUsage.applicationInfo=mappList.get(i);
                            customNetworkUsage.title=mappList.get(i).loadLabel(getActivity().getPackageManager()).toString();
                            customNetworkUsage.appIcon=mappList.get(i).loadIcon(getActivity().getPackageManager());
                            customNetworkUsage.internetStatus = String.format("%.2f", getNetworkUsage(mappList.get(i).uid)) + " MB";
                            customNetworkUsage.networkusage= (int) getNetworkUsage(mappList.get(i).uid);
                            if(customNetworkUsage.networkusage!=0) {
                                customNetworkUsageList.add(customNetworkUsage);
                            }
                        }
                    }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                customNetworkUsage.appIcon = getActivity()
                        .getDrawable(R.mipmap.ic_default_app_launcher);
            }
        }
        mnetworkUsageAdapter.setCustomUsageStatsList(customNetworkUsageList);
    }

    public double getNetworkUsage(int uid)
    {
        double received = (double) TrafficStats.getUidRxBytes(uid) / (1024 * 1024);
        double sent = (double) TrafficStats.getUidTxBytes(uid) / (1024 * 1024);
        double total = received + sent;
        Log.d("getNetworkUsage", String.format("%.2f", total) + " MB");

        return total;
    }
}