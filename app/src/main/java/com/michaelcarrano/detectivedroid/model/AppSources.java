package com.michaelcarrano.detectivedroid.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelcarrano on 8/30/14.
 */
public class AppSources {

    private static AppSources instance;

    private List<AppSource> sources = new ArrayList<AppSource>();

    public static AppSources getInstance() {
        if (instance == null) {
            instance = new AppSources();
        }
        return instance;
    }

    public List<AppSource> getSources() {
        return sources;
    }

    public void setSources(List<AppSource> sources) {
        this.sources = sources;
    }
}
