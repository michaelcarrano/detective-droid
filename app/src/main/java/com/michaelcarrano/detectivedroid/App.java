package com.michaelcarrano.detectivedroid;

import com.michaelcarrano.detectivedroid.model.Libraries;
import com.michaelcarrano.detectivedroid.model.Library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by michaelcarrano on 8/30/14.
 */
public class App extends Application {

    private static App instance;

    private SharedPreferences mPreferences;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            parseLibrariesJson();
        } catch (JSONException e) {
            Log.i(this.getClass().getSimpleName(), "parseLibrariesJson: " + e.toString());
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void parseLibrariesJson() throws JSONException {
        String jsonString = Utils.convertStreamToString(
                getResources().openRawResource(R.raw.libraries));
        JSONArray json = new JSONArray(jsonString);
        Set<Library> libraries = new HashSet<Library>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            String path = jsonObject.getString("path");
            String source = jsonObject.getString("source");
            libraries.add(new Library(name, path, source));
        }
        Libraries.getInstance().setLibraries(libraries);
    }

    public int getPreferenceScanSystemApps() {
        return mPreferences.getBoolean("pref_skip_system_apps", true) ? 1 : 0;
    }
}