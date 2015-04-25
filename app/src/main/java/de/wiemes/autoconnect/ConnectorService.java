package de.wiemes.autoconnect;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import de.wiemes.autoconnect.Util.AppHelper;
import de.wiemes.autoconnect.Util.AppInfo;
import java.io.DataOutputStream;

public class ConnectorService extends Service
{
    // Attributes
    private BluetoothConnectionReceiver bluetoothConnectReceiver;
    private AppHelper appHelperInstance;

    // Constructor
    public ConnectorService() {}

    // Methods
    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.appHelperInstance = AppHelper.getInstance();

        registerBluetoothReceiver();
    }

    @Override
    public void onDestroy()
    {
        Log.d(this.getClass().getName(), "Service stopped...");
    }

    public void killApps (BluetoothDevice device)
    {
        for (AppInfo appInfo : appHelperInstance.getInstalledApps(getApplicationContext(), false))
        {
            if (appHelperInstance.loadPreference(getApplicationContext(),
                    device.getName() + appInfo.getAppname(), false))
            {
                try
                {
                    Process suProcess = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

                    os.writeBytes("adb shell" + "\n");
                    os.flush();
                    os.writeBytes("am force-stop " + appInfo.getPackageName() + "\n");
                    os.flush();

                    Log.d(this.getClass().getName(), "App wird beendet...");
                }
                catch (Exception e)
                {
                    Log.e(this.getClass().getName(), "Could not kill the app...");
                }
            }
        }
    }

    public void startApps (Context context, BluetoothDevice device)
    {
        for (AppInfo appInfo : appHelperInstance.getInstalledApps(getApplicationContext(), false))
        {
            if (appHelperInstance.loadPreference(getApplicationContext(),
                    device.getName() + appInfo.getAppname(), false))
            {
                try
                {
                    Intent i = context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
                    context.startActivity(i);

                    Log.d(this.getClass().getName(), "App wird gestartet...");
                }
                catch (Exception e)
                {
                    Log.e(this.getClass().getName(), "Could not start the app...");
                }
            }
        }
    }

    private void registerBluetoothReceiver ()
    {
        this.bluetoothConnectReceiver = new BluetoothConnectionReceiver(this);

        final IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");

        this.registerReceiver(bluetoothConnectReceiver, filter);
    }
}
