package com.yourapp.fragment;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yourapp.adapter.ApplicationAdapter;
import com.yourapp.businesslogic.AnalyticsApplication;
import com.yourapp.myapplication.R;

import java.util.Collections;
import java.util.List;

public class InstalledAllAppFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ApplicationInfo> mappList;
    private Tracker mTracker;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mappList = getActivity().getPackageManager().getInstalledApplications(0);
        //http://stackoverflow.com/a/8174637
        Collections.sort(mappList, new ApplicationInfo.DisplayNameComparator(getActivity().getPackageManager()));

        Log.d("oncreate", "create installed");
        mAdapter = new ApplicationAdapter(mappList, getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_app);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onResume() {
        mTracker.setScreenName("All Apps Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }
}