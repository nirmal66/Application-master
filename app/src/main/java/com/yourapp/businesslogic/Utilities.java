package com.yourapp.businesslogic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;

import com.yourapp.fragment.BackupRestoreFragment;
import com.yourapp.myapplication.DialogCaller;
import com.yourapp.myapplication.HomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirmal on 15-02-2016.
 */
public class Utilities {

    //http://javatechig.com/android/how-to-get-list-of-installed-apps-in-android
    //http://stackoverflow.com/a/9359389
    public List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        List<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
               /* if (getActivity().getPackageManager().getLaunchIntentForPackage(info.packageName) != null &&
                        !getActivity().getPackageManager().getLaunchIntentForPackage(info.packageName).equals("")) {
                    applist.add(info);
                }*/
                //http://stackoverflow.com/a/30468372
                if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                    // user installed apps
                    applist.add(info);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }

    public List<PackageInfo> movetosdCard(List<PackageInfo> list) {
        //http://stackoverflow.com/a/11150657
        List<PackageInfo> applist = new ArrayList<PackageInfo>();
        for (PackageInfo pInfo : list) {
            if (pInfo.installLocation ==0||pInfo.installLocation ==2) {
                // then it can be moved to the SD card
               // Log.d("test",""+pInfo.installLocation);
                applist.add(pInfo);
            }
        }
        return applist;
    }

    public void deletePackage(String packageName, Context context) {
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(intent);
        ((HomeActivity) context).fragment(new BackupRestoreFragment(), "App_Fragment");
    }

    public void backupAPK(File file, String fileName, final Context context) throws IOException {
        File createFolder = new File(Environment.getExternalStorageDirectory().toString() + "/BatteryMaster");
        if (!createFolder.exists()) {
            createFolder.mkdir();
        }
        createFolder = new File(createFolder.getPath() + "/" + fileName + ".apk");
        if (!createFolder.exists()) {
            createFolder.createNewFile();
        } else {
            createFolder.delete();
            createFolder.createNewFile();
        }
        DialogCaller.showDialog(context, "Backup", "Here is your apk file: /BatteryMaster/" + fileName + ".apk", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Log.d("all details", context.getPackageManager().getPackageArchiveInfo(Environment.getExternalStorageDirectory().toString() + "/BatteryMaster/Shoppinglist.apk", 0).toString());
                        ((HomeActivity) context).fragment(new BackupRestoreFragment(), "App_Fragment");

            }
        });

        FileChannel in = new FileInputStream(file).getChannel();
        FileChannel out = new FileOutputStream(createFolder).getChannel();
        out.transferFrom(in, 0, in.size());
        in.close();
        out.close();
    }

    public File getApkFile(String packageName, Context context) throws PackageManager.NameNotFoundException {
        return (new File(context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir));
    }

    public boolean isConnectingToInternet(Context _context){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
/*

    these code i have wrote for get the permission for particular appsss
    PackageInfo packageInfo = null;

    try {
        packageInfo = getActivity().getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
    } catch (PackageManager.NameNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    String[] requestedPermissions = packageInfo.requestedPermissions;
    Log.d("test****************",""+requestedPermissions.length);
    for(String test :requestedPermissions )
    {
        Log.d("test****************",""+test);

    }*/
}
