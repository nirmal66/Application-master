package com.yourapp.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yourapp.businesslogic.AnalyticsApplication;
import com.yourapp.fragment.AppUsageStatisticsFragment;
import com.yourapp.fragment.ApplicationFragment;
import com.yourapp.fragment.BackupRestoreFragment;
import com.yourapp.fragment.BatteryFragment;
import com.yourapp.fragment.MovetoSDCardFragment;
import com.yourapp.fragment.NetworkUsageFragment;
import com.yourapp.fragment.RunningAppFragment;
import com.yourapp.fragment.StorageFragment;
import com.yourapp.fragment.TutorialFragment;

//MySlidingAdapter.OnSlidingClickInterface
public class HomeActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressSpinner;
    private Toolbar mToolbar;
    private String tag;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String menuItem;
    String[] mTitles;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        mTracker.setScreenName("Home Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Log.d("backFragment", tag);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        drawer.openDrawer(GravityCompat.START);
/*        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);

        if (tag.equals("Main_Activity")) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                fragment(new BatteryFragment(), "Main_Activity");
            } else {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                finish();
            }
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment(new BatteryFragment(), "Main_Activity");
            }
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //  mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mTitles = new String[]{"Backup and Restore", "Applications", "Move to SD Card", "Apps Usage", "Data Usage"};
            Menu menuNav = navigationView.getMenu();
            MenuItem navItem = menuNav.findItem(R.id.nav_running_apps);
            navItem.setVisible(false);
            navItem.setEnabled(false);
        } else {
            mTitles = new String[]{"Backup and Restore", "Applications", "Running Apps", "Move to SD Card", "Data Usage"};
            Menu menuNav = navigationView.getMenu();
            MenuItem navItem = menuNav.findItem(R.id.nav_app_usage);
            navItem.setVisible(false);
            navItem.setEnabled(false);
        }

//Action bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
/*        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mDrawerList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mDrawerList.setLayoutManager(mLayoutManager);
        mAdapter = new MySlidingAdapter(mTitles, this, this);
        mDrawerList.setAdapter(mAdapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            *//** Called when a drawer has settled in a completely closed state. *//*
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

            *//** Called when a drawer has settled in a completely open state. *//*
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);*/


        if (savedInstanceState == null) {
            fragment(new BatteryFragment(), "Main_Activity");

        }
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            tag = savedInstanceState.getString("tag");
        }

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
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

        if (id == R.id.action_tutorial) {
            fragment(new TutorialFragment(), "App_Fragment");
            return true;
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_backup_and_restore) {
            loader(new BackupRestoreFragment());
        } else if (id == R.id.nav_storge) {
            loader(new StorageFragment());
        } else if (id == R.id.nav_application) {
            loader(new ApplicationFragment());
        } else if (id == R.id.nav_move_to_sd_card) {
            loader(new MovetoSDCardFragment());
        } else if (id == R.id.nav_app_usage) {
            loader(new AppUsageStatisticsFragment());
        } else if (id == R.id.nav_data_usage) {
            loader(new NetworkUsageFragment());
        } else if (id == R.id.nav_running_apps) {
            loader(new RunningAppFragment());
        } else if (id == R.id.nav_exit) {
            finish();
        }
        return true;
    }

    /*  @Override
      public void onSlidingItemClick(String menuName) {
          Log.d("onSlidingItemClick", menuName);
          menuItem = menuName;
          mDrawerLayout.closeDrawer(mDrawerList);
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
      }*/
    private void loader(final Fragment fragment) {
        Handler handler = new Handler();
        progressSpinner = new ProgressDialog(HomeActivity.this);
        progressSpinner.setMessage("Loading Data. Please wait for a second...");
        progressSpinner.setIndeterminate(false);
        progressSpinner.setMax(100);
        progressSpinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressSpinner.setCancelable(true);
        progressSpinner.show();
        // run a thread after 1 seconds to start the home screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //code written here will execute after 1000ms(1 seconds)
                // write your intent to move from MainActivity.this to XMLParsingActivity.class
                fragment(fragment, "App_Fragment");
                progressSpinner.dismiss();

            }
        }, 1000);
    }

}

