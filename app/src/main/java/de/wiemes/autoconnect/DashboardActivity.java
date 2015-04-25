package de.wiemes.autoconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.*;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.wiemes.autoconnect.Adapter.ListViewAppsAdapter;
import de.wiemes.autoconnect.Util.AppHelper;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends Activity
{
    // Attributes
    private AppHelper appHelperInstance;
    private ListView lstInstalledApps;
    private TextView lblDevice;
    private ListViewAppsAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.appHelperInstance = AppHelper.getInstance();

        this.lblDevice = (TextView) findViewById(R.id.lblDevice);
        this.lstInstalledApps = (ListView) findViewById(R.id.lstInstalledApps);
    }

    @Override
    protected void onResume()
    {
        // Fill list with installed apps
        fillAppList();

        // Check paired bluetooth devices
        checkPairedDevices();

        super.onResume();
    }

    public void showDeviceDialog (View view)
    {
        final ArrayList<String> devices = new ArrayList<>();

        for (BluetoothDevice bt : appHelperInstance.getPairedBtDevices())
        {
            devices.add(bt.getName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a device...");
        builder.setItems(devices.toArray(new String[devices.size()]), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int selectedDevice)
            {
                setSelectedDevice(devices.get(selectedDevice));
                appHelperInstance.setSelectedDevice(devices.get(selectedDevice));
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkPairedDevices ()
    {
        List<BluetoothDevice> pairedDevices =  appHelperInstance.getPairedBtDevices();

        if (pairedDevices.isEmpty())
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("No BT devices found...");
            alertDialogBuilder.setMessage("Please pair a bluetooth device before using the app!");
            alertDialogBuilder.setNeutralButton("Quit",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int id)
                {
                    finish();
                }
            });
        }
        else
        {
            setSelectedDevice(pairedDevices.get(0).getName());
        }
    }

    private void setSelectedDevice (String selectedDevice)
    {
        lblDevice.setText(selectedDevice);
        appHelperInstance.setSelectedDevice(selectedDevice);
        appListAdapter.notifyDataSetChanged();
    }

    private void fillAppList ()
    {
        this.appListAdapter = new ListViewAppsAdapter(this, R.layout.custom_row_apps, appHelperInstance.getInstalledApps(this, false));
        lstInstalledApps.setAdapter(appListAdapter);
    }
}
