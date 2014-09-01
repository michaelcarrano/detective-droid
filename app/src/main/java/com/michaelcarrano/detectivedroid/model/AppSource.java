package com.michaelcarrano.detectivedroid.model;

import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Identifies a single application which uses libraries
 *
 * Created by michaelcarrano on 8/30/14.
 */
public class AppSource {

    // The application
    private PackageInfo packageInfo;

    // List of libraries found in an application
    private List<Library> libraries;

    public AppSource(PackageInfo packageInfo, ArrayList<Library> libraries) {
        this.packageInfo = packageInfo;
        this.libraries = libraries;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }
}
