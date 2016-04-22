package com.aaron.aarondemo.activitys.systems;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.aarondemo.R;
import com.aaron.aarontools.tools.SystemTools;

import java.util.List;

/**
 * Created by toughegg on 16/4/21.
 */
public class AppListActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView app_list_lv;
    private AppListAdapter mAppListAdapter;

    private List<PackageInfo> mPackageInfoList;

    private int type = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_applist);

        TextView back = (TextView) findViewById (R.id.back);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        type = getIntent ().getIntExtra ("type", 0);

        app_list_lv = (ListView) findViewById (R.id.app_list_lv);
        app_list_lv.setOnItemClickListener (this);
    }

    @Override
    protected void onResume () {
        super.onResume ();
        if (type == 0) {
            mPackageInfoList = SystemTools.getAppList (this);
        } else if (type == 1) {
            mPackageInfoList = SystemTools.getNotSystemAppList (this);
        }

        if (mAppListAdapter == null) {
            mAppListAdapter = new AppListAdapter (this, mPackageInfoList);
            app_list_lv.setAdapter (mAppListAdapter);
        } else {
            mAppListAdapter.setPackageInfoList (mPackageInfoList);
        }

    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        final PackageInfo pi = mPackageInfoList.get (position);
        if (SystemTools.isSystemApp (pi)) {
            Toast.makeText (this, "系统应用", Toast.LENGTH_SHORT).show ();
            return;
        }
        Dialog dialog = new AlertDialog.Builder (this).setIcon (
                android.R.drawable.btn_star).setTitle (pi.applicationInfo.loadLabel (getPackageManager ()).toString ())
                .setMessage ("请选择")
                .setPositiveButton ("取消", null)
                .setNegativeButton ("卸载", new OnClickListener () {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        SystemTools.uninstallApp (AppListActivity.this, pi.packageName);
                    }
                })
                .setNeutralButton ("启动", new OnClickListener () {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        SystemTools.startApp (AppListActivity.this, pi.packageName);
                    }

                }).create ();
        dialog.show ();
    }
}
