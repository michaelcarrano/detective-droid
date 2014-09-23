package com.michaelcarrano.detectivedroid.view;

import com.michaelcarrano.detectivedroid.App;
import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.adapter.LibraryAdapter;
import com.michaelcarrano.detectivedroid.model.AppSource;
import com.michaelcarrano.detectivedroid.model.AppSources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class AppDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The AppSource this fragment is presenting.
     */
    private AppSource mAppSource;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public AppDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mAppSource = AppSources.getInstance().getSources()
                    .get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_detail, container, false);

        LibraryAdapter adapter = new LibraryAdapter(App.getInstance().getBaseContext(),
                mAppSource.getLibraries());
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create intent to launch a browser
                String url = mAppSource.getLibraries().get(position).getSource();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return rootView;
    }
}
