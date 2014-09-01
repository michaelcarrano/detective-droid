package com.michaelcarrano.detectivedroid.adapter;

import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.model.AppSource;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by michaelcarrano on 8/31/14.
 */
public class AppSourceAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<AppSource> mAppSources;

    public AppSourceAdapter(Context ctx, List<AppSource> appSources) {
        mContext = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        mAppSources = appSources;
    }

    @Override
    public int getCount() {
        return mAppSources.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppSources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        AppSource source = (AppSource) getItem(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_card, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.name = (TextView) convertView.findViewById(R.id.text_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PackageManager pm = mContext.getPackageManager();
        PackageInfo pkg = source.getPackageInfo();

        holder.icon.setImageDrawable(pkg.applicationInfo.loadIcon(pm));
        holder.name.setText(pm.getApplicationLabel(pkg.applicationInfo));

        return convertView;
    }

    public static class ViewHolder {

        public ImageView icon;

        public TextView name;
    }
}
