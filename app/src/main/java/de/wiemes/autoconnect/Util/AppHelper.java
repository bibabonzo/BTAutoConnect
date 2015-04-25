package de.wiemes.autoconnect.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class AppHelper
{
    // Attributes
    private static AppHelper Instance;
    private String selectedDevice;

    // Constructor
    private AppHelper () {}

    // Methods
    public static AppHelper getInstance ()
    {
        if (Instance == null)
        {
            Instance = new AppHelper ();
        }

        return Instance;
    }

    public ArrayList<AppInfo> getInstalledApps(Context context, boolean getSysPackages)
    {
        ArrayList<AppInfo> res = new ArrayList<>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);

        for(int i=0;i<packs.size();i++)
        {
            android.content.pm.PackageInfo p = packs.get(i);

            // Check if app is a system app or own app
            boolean isSystemApp = (p.applicationInfo.flags
                    & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0;

            if (((!getSysPackages) && (isSystemApp)) || p.packageName.equals(context.getPackageName()))
            {
                continue ;
            }

            AppInfo newInfo = new AppInfo();

            newInfo.setAppname(p.applicationInfo.loadLabel(context.getPackageManager()).toString());
            newInfo.setPackageName(p.packageName);
            newInfo.setVersionName(p.versionName);
            newInfo.setVersionCode(p.versionCode);
            newInfo.setIcon(p.applicationInfo.loadIcon(context.getPackageManager()));

            res.add(newInfo);
        }
        return res;
    }

    public List<BluetoothDevice> getPairedBtDevices ()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        List<?> list = new ArrayList(bluetoothAdapter.getBondedDevices());

        return new ArrayList(bluetoothAdapter.getBondedDevices());
    }

    public void savePreference (Context context, String key, Boolean value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

        Log.d(this.getClass().getName(), "Preference saved: " + key + " --> " + value);
    }

    public boolean loadPreference (Context context, String key, Boolean value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public String getSelectedDevice()
    {
        return selectedDevice;
    }

    public void setSelectedDevice(String selectedDevice)
    {
        this.selectedDevice = selectedDevice;
    }
}
