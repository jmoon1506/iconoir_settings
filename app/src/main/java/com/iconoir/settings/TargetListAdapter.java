package com.iconoir.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TargetListAdapter extends BaseAdapter {
    List<PackageInfo> packageList;
    TargetActivity context;
    Boolean showSystemPackages;
    PackageManager packageManager;

    public TargetListAdapter(TargetActivity context, List<PackageInfo> packageList) {
        super();
        this.packageManager = context.getPackageManager();
        this.context = context;
        this.packageList = packageList;
    }

    public void setShowAll(Boolean showSystemPackages) {
        this.showSystemPackages = showSystemPackages;
        notifyDataSetChanged();
    }

    public int getCount() {
        return packageList.size();
    }

    public PackageInfo getItem(int position) {
        return packageList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        View packageItem;
        TextView text;
        ImageView icon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.package_item, null);
            holder = new ViewHolder();

            holder.packageItem = convertView.findViewById(R.id.listItem);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.text.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position=(Integer)v.getTag();
                    String targetPkg = getItem(position).packageName;
                    context.onBackPressed(targetPkg);
//                    Toast.makeText(context,
//                            targetPkg, Toast.LENGTH_SHORT)
//                            .show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set data
        final PackageInfo packageInfo = (PackageInfo) getItem(position);
        holder.text.setTag(position);
        holder.text.setText(packageManager.getApplicationLabel(
                packageInfo.applicationInfo).toString());
        holder.icon.setImageDrawable(packageManager
                .getApplicationIcon(packageInfo.applicationInfo));
        return convertView;
    }
}