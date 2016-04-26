package com.yourapp.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yourapp.adapter.MySlidingAdapter;
import com.yourapp.fragment.AppUsageStatisticsFragment;
import com.yourapp.fragment.ApplicationFragment;
import com.yourapp.fragment.BackupRestoreFragment;
import com.yourapp.fragment.BatteryFragment;
import com.yourapp.fragment.MovetoSDCardFragment;
import com.yourapp.fragment.NetworkUsageFragment;
import com.yourapp.fragment.RunningAppFragment;


public class HomeActivity extends ActionBarActivity implements MySlidingAdapter.OnSlidingClickInterface {

    private Toolbar mToolbar;
    private String tag;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String menuItem;
    String[] mTitles;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("backFragment", tag);
        if (tag.equals("Main_Activity")) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            finish();
        } else {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragment(new BatteryFragment(), "Main_Activity");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
        {
            mTitles = new String[]{"Backup and Restore", "Applications",  "Move to SD Card", "Apps Usage", "Data Usage"};
        }
        else
        {
            mTitles = new String[]{"Backup and Restore", "Applications", "Running Apps", "Move to SD Card",  "Data Usage"};

        }
//Action bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mDrawerList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mDrawerList.setLayoutManager(mLayoutManager);
        mAdapter = new MySlidingAdapter(mTitles, this, this);
        mDrawerList.setAdapter(mAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (menuItem != null) {
                    if (menuItem.equals("Backup and Restore")) {
                        fragment(new BackupRestoreFragment(), "App_Fragment");
                    } else if (menuItem.equals("Applications")) {
                        fragment(new ApplicationFragment(), "App_Fragment");
                    } else if (menuItem.equals("Running Apps")) {
                        fragment(new RunningAppFragment(), "App_Fragment");
                    } else if (menuItem.equals("Move to SD Card")) {
                        fragment(new MovetoSDCardFragment(), "App_Fragment");
                    } else if (menuItem.equals("Apps Usage")) {
                        fragment(new AppUsageStatisticsFragment(), "App_Fragment");
                    } else if (menuItem.equals("Data Usage")) {
                        fragment(new NetworkUsageFragment(), "App_Fragment");
                    }
                }
                menuItem = "exit";
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {
            fragment(new BatteryFragment(), "Main_Activity");

        }
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            tag = savedInstanceState.getString("tag");
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getApplicationContext(), SettingsPreference.class);
            startActivity(settings);
            return true;
        }
        if (id == R.id.action_home) {
            fragment(new BatteryFragment(), "Main_Activity");
            return true;
        }
        if (id == R.id.action_updates) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tag", tag);
    }

    public void fragment(Fragment fragment, String transaction) {
        tag = transaction;

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_transaction, fragment, transaction);
        fragmentTransaction.addToBackStack(transaction);
        fragmentTransaction.commit();

        Log.d("backFragment", tag);
    }

    @Override
    public void onSlidingItemClick(String menuName) {
        Log.d("onSlidingItemClick", menuName);
        menuItem = menuName;
        mDrawerLayout.closeDrawer(mDrawerList);
    }


}

