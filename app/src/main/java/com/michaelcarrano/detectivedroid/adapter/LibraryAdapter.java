package com.michaelcarrano.detectivedroid.adapter;

import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.model.Library;

import android.content.Context;
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
public class LibraryAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    private List<Library> mAppSources;

    public LibraryAdapter(Context ctx, List<Library> appSources) {
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
        Library library = (Library) getItem(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_card, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.name = (TextView) convertView.findViewById(R.id.text_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setVisibility(View.GONE);
        holder.name.setText(library.getName());

        return convertView;
    }

    public static class ViewHolder {

        public ImageView icon;

        public TextView name;
    }
}
