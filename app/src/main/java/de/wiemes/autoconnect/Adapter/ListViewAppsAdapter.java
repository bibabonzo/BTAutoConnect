package de.wiemes.autoconnect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import de.wiemes.autoconnect.R;
import de.wiemes.autoconnect.Util.AppHelper;
import de.wiemes.autoconnect.Util.AppInfo;
import java.util.List;

/**
 * Created by joachimwiemes on 08.01.15.
 */
public class ListViewAppsAdapter extends ArrayAdapter<AppInfo>
{
    // Attributes
    private Context context;
    private List<AppInfo> installedApps;;

    public ListViewAppsAdapter(Context _context, int _textViewResourceId, List<AppInfo> _installedApps)
    {
        super(_context, R.layout.custom_row_apps);

        this.context = _context;
        this.installedApps = _installedApps;
    }

    static class ViewHolder
    {
        protected ImageView appIcon;
        protected TextView appName;
        protected CheckBox appChecked;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        View view = null;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_row_apps, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.appIcon = (ImageView) view.findViewById(R.id.imgListviewAppIcon);
            viewHolder.appName = (TextView) view.findViewById(R.id.lblListViewAppName);
            viewHolder.appChecked = (CheckBox) view.findViewById(R.id.cbSelection);
            viewHolder.appChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    AppInfo appInfo = (AppInfo) viewHolder.appChecked.getTag();
                    appInfo.setSelected(buttonView.isChecked());

                    AppHelper.getInstance().savePreference(context,
                            AppHelper.getInstance().getSelectedDevice() + viewHolder.appName.getText().toString(),
                            buttonView.isChecked());
                }
            });

            view.setTag(viewHolder);
            viewHolder.appChecked.setTag(installedApps.get(position));
        }
        else
        {
            view = convertView;
            ((ViewHolder) view.getTag()).appChecked.setTag(installedApps.get(position));
        }

        // Read shared preferences
        boolean checkedState = AppHelper.getInstance().loadPreference(context,
                AppHelper.getInstance().getSelectedDevice() + installedApps.get(position).getAppname(),
                false);

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.appName.setText(installedApps.get(position).getAppname());
        holder.appChecked.setChecked(checkedState);
        holder.appIcon.setImageDrawable(installedApps.get(position).getIcon());

        return view;
    }

    @Override
    public int getCount()
    {
        return installedApps.size();
    }
}
