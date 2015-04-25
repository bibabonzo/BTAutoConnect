package de.wiemes.autoconnect.Util;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by joachimwiemes on 08.01.15.
 */
public class AppInfo
{
    private String appname = "";
    private String pname = "";
    private String versionName = "";
    private int versionCode = 0;
    private Drawable icon;
    private boolean selected;

    public void prettyPrint()
    {
        Log.v(this.getClass().getName(), getAppname() + "\t" + getPackageName() + "\t" + getVersionName() + "\t" + getVersionCode());
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackageName() {
        return pname;
    }

    public void setPackageName(String pname) {
        this.pname = pname;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSelected ()
    {
        return selected;
    }

    public void setSelected (boolean _selected)
    {
        this.selected = _selected;
    }
}
