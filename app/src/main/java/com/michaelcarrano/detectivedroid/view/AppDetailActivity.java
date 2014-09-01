package com.michaelcarrano.detectivedroid.view;

import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.model.AppSources;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class AppDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        int position = getIntent().getExtras().getInt(AppDetailFragment.ARG_ITEM_ID);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the ActionBar title, icon and up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PackageManager pm = getPackageManager();
        PackageInfo pkg = AppSources.getInstance().getSources().get(position).getPackageInfo();
        getSupportActionBar().setIcon(pkg.applicationInfo.loadIcon(pm));
        getSupportActionBar().setTitle(pm.getApplicationLabel(pkg.applicationInfo).toString());

        // savedInstanceState is non-null when there is fragment state saved from previous
        // configurations of this activity (e.g. when rotating the screen from portrait to
        // landscape). In this case, the fragment will automatically be re-added to its container
        // so we don't need to manually add it. For more information, see the Fragments API guide
        // at: http://developer.android.com/guide/components/fragments.html
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(AppDetailFragment.ARG_ITEM_ID, position);
            AppDetailFragment fragment = new AppDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.app_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, AppListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
