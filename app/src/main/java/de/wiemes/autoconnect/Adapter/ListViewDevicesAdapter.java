package de.wiemes.autoconnect.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.wiemes.autoconnect.R;
import de.wiemes.autoconnect.Util.AppInfo;
import java.util.List;
import java.util.Set;

/**
 * Created by joachimwiemes on 08.01.15.
 */
public class ListViewDevicesAdapter extends ArrayAdapter<BluetoothDevice>
{
    // Attributes
    private Context context;
    private List<BluetoothDevice> devices;

    public ListViewDevicesAdapter(Context _context, int _textViewResourceId, List<BluetoothDevice> _devices)
    {
        super(_context, R.layout.custom_row_apps);

        this.context = _context;
        this.devices = _devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        String name = devices.get(position).getName();

        return view;
    }

    @Override
    public int getCount()
    {
        return devices.size();
    }
}
