package com.yourapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yourapp.adapter.ViewPagerAdapter;
import com.yourapp.myapplication.R;

public class ApplicationFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PlaystoreappFragment playstoreApp = new PlaystoreappFragment();
    private InstalledAllAppFragment installedApp = new InstalledAllAppFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("on create view pager", "create");
        return inflater.inflate(R.layout.fragment_appmanager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //http://stackoverflow.com/a/25677688
        viewPager.getAdapter().notifyDataSetChanged();
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("on resume view pager", "resume");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new PlaystoreappFragment(), "Playstore Apps");
        adapter.addFragment(new InstalledAllAppFragment(), "All Apps");
        viewPager.setAdapter(adapter);
    }


}