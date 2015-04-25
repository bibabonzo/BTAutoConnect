package de.wiemes.autoconnect;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;

public class BluetoothConnectionReceiver extends BroadcastReceiver
{
    // Attributes
    private ConnectorService service;

    // Constructor
    public BluetoothConnectionReceiver(ConnectorService _service)
    {
        this.service = _service;
    }

    // Methods
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action))
        {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            service.startApps(context, device);
        }
        else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action))
        {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            service.killApps(device);
        }
    }
}
