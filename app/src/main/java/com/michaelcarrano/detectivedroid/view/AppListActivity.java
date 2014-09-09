package com.michaelcarrano.detectivedroid.view;

import com.michaelcarrano.detectivedroid.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AppListActivity extends FragmentActivity implements AppListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        if (findViewById(R.id.app_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/values-large and res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the 'activated' state when touched.
            ((AppListFragment) getSupportFragmentManager().findFragmentById(
                    R.id.app_list)).setActivateOnItemClick(true);
        }
    }

    /**
     * Callback method from {@link AppListFragment.Callbacks} indicating that the item with the
     * given ID was selected.
     */
    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by adding or replacing the
            // detail fragment using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(AppDetailFragment.ARG_ITEM_ID, id);
            AppDetailFragment fragment = new AppDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.app_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity for the selected item ID.
            Intent detailIntent = new Intent(this, AppDetailActivity.class);
            detailIntent.putExtra(AppDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
