package com.michaelcarrano.detectivedroid.view;

import com.michaelcarrano.detectivedroid.App;
import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.adapter.AppSourceAdapter;
import com.michaelcarrano.detectivedroid.model.AppSources;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class AppListFragment extends ListFragment implements DetectorTaskFragment.Callbacks {

    /**
     * The serialization (saved instance state) Bundle key representing the activated item position.
     * Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * A implementation of the {@link com.michaelcarrano.detectivedroid.view.AppListFragment.Callbacks}
     * interface that does nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        }
    };

    /**
     * The fragment's current callback object, which is notified of list item clicks.
     */
    private Callbacks mCallbacks = sCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public AppListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If there already exists fragments doing work, reattach to them
        FragmentManager fm = getFragmentManager();
        DetectorTaskFragment taskFragment = (DetectorTaskFragment) fm
                .findFragmentByTag(DetectorTaskFragment.TAG);
        if (taskFragment != null) {
            taskFragment.setTargetFragment(this, DetectorTaskFragment.TASK_REQUEST_CODE);
        } else {
            if (AppSources.getInstance().getSources().isEmpty()) {
                startDetectionTask();
            } else {
                setListView();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDividerHeight(0);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the fragment is attached to one)
        // that an item has been selected.
        mCallbacks.onItemSelected(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be given the
     * 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private void startDetectionTask() {
        DetectorTaskFragment taskFragment = new DetectorTaskFragment();
        taskFragment.setTargetFragment(this, DetectorTaskFragment.TASK_REQUEST_CODE);
        taskFragment.show(getFragmentManager(), DetectorTaskFragment.TAG);
    }

    private void setListView() {
        if (!AppSources.getInstance().getSources().isEmpty()) {
            AppSourceAdapter adapter = new AppSourceAdapter(App.getInstance().getBaseContext(),
                    AppSources.getInstance().getSources());
            setListAdapter(adapter);

            // Set default active position if no item is selected in two pane
            boolean mTwoPane = false;
            if (getActivity().findViewById(R.id.app_detail_container) != null) {
                mTwoPane = true;
            }
            if (mActivatedPosition == ListView.INVALID_POSITION && mTwoPane) {
                setActivatedPosition(0);
                mCallbacks.onItemSelected(0);
            }
        } else {
            getActivity().setContentView(R.layout.activity_app_list_no_results);
        }
    }

    /**
     * Scanning for libraries has completed, show the results.
     */
    @Override
    public void onTaskFinished() {
        setListView();
    }

    @Override
    public void onTaskCancelled() {
        setListView();
    }

    /**
     * A callback interface that all activities containing this fragment must implement. This
     * mechanism allows activities to be notified of item selections.
     */
    public interface Callbacks {

        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(int id);
    }
}
