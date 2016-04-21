package com.aaron.aarondemo.activitys.systems;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.aarondemo.R;

import java.util.List;

/**
 * Created by toughegg on 16/4/21.
 */
public class AppListAdapter extends BaseAdapter {

    private Context mContext;
    private PackageManager pm;
    private LayoutInflater mInflater;
    private List<PackageInfo> mPackageInfoList;

    public AppListAdapter (Context context, List<PackageInfo> packageInfoList) {
        mContext = context;
        pm = context.getPackageManager ();
        mInflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        mPackageInfoList = packageInfoList;
    }

    public void setPackageInfoList (List<PackageInfo> packageInfoList) {
        mPackageInfoList = packageInfoList;
        notifyDataSetChanged ();
    }

    @Override
    public int getCount () {
        return mPackageInfoList.size ();
    }

    @Override
    public PackageInfo getItem (int position) {
        return mPackageInfoList.get (position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate (R.layout.adapter_applist, null);
            holder = new ViewHolder ();
            holder.appicon_iv = (ImageView) convertView.findViewById (R.id.appicon_iv);
            holder.appname_tv = (TextView) convertView.findViewById (R.id.appname_tv);
            holder.packagename_tv = (TextView) convertView.findViewById (R.id.packagename_tv);
            holder.versionname_tv = (TextView) convertView.findViewById (R.id.versionname_tv);
            holder.versioncode_tv = (TextView) convertView.findViewById (R.id.versioncode_tv);
            convertView.setTag (holder);
        } else {
            holder = (ViewHolder) convertView.getTag ();
        }

        PackageInfo pi = getItem (position);
        Drawable iconDrawable = pi.applicationInfo.loadIcon (pm);
        holder.appicon_iv.setImageDrawable (iconDrawable);
        holder.appname_tv.setText (pi.applicationInfo.loadLabel (pm).toString ());
        holder.packagename_tv.setText (pi.packageName);
        holder.versionname_tv.setText (pi.versionName);
        holder.versioncode_tv.setText (pi.versionCode + "");
        return convertView;
    }

    class ViewHolder {
        TextView appname_tv, packagename_tv, versionname_tv, versioncode_tv;
        ImageView appicon_iv;
    }
}
