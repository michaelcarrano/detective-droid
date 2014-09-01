package com.michaelcarrano.detectivedroid.model;

import java.util.Locale;

/**
 * Created by michaelcarrano on 8/21/14.
 */
public class Library {

    private String name;

    private String path;

    private String source;

    public Library(String name, String path, String src) {
        this.name = name;
        this.path = path;
        this.source = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean matches(String component) {
        return component.toLowerCase(Locale.US).startsWith(path.toLowerCase(Locale.US));
    }

    @Override
    public String toString() {
        return name;
    }

}