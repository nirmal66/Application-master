package com.yourapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.yourapp.businesslogic.Utilities;
import com.yourapp.myapplication.R;

public class TutorialFragment extends Fragment {

    private WebView tutorial;
    private Button checkTutorial;
    Utilities ut = new Utilities();
    private CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tutorial = (WebView)view.findViewById(R.id.webview_tutorial);
        checkTutorial= (Button)view.findViewById(R.id.bn_tutorial);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_layout);
        tutorial.getSettings().setJavaScriptEnabled(true);


        checkTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(ut.isConnectingToInternet(getActivity()))
                 {
                     tutorial.loadUrl("https://www.youtube.com/watch?v=FVcnopBjE3c");
                 }
                else
                 {
                     Snackbar snackbar = Snackbar
                             .make(coordinatorLayout, R.string.internet_connection, Snackbar.LENGTH_LONG)
                             .setAction("Settings", new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     startActivity(new Intent(Settings.ACTION_SETTINGS ));
                                 }
                             });

                     snackbar.show();
                 }
            }
        });
    }



}
