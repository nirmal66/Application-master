package com.yourapp.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BatteryStatus {

    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private String prefName = "BatteryPref";
    private String key_PLUGIN = "plugin";
    private String key_PLUGOUT = "plugout";

    public BatteryStatus(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        pref = context.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void lastPlugin(String lastplugin) {
        editor.putString(key_PLUGIN, lastplugin);
        editor.commit();
    }

    public void lastPlugout(String lastPlugout) {
        editor.putString(key_PLUGOUT, lastPlugout);
        editor.commit();
    }

    public String getlastPlugin() {
        return pref.getString(key_PLUGIN, "unknown");
    }

    public String getlastPlugout() {
        return pref.getString(key_PLUGOUT, "unknown");
    }

}
