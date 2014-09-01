package com.michaelcarrano.detectivedroid.task;

import com.michaelcarrano.detectivedroid.model.AppSource;
import com.michaelcarrano.detectivedroid.model.AppSources;
import com.michaelcarrano.detectivedroid.model.Libraries;
import com.michaelcarrano.detectivedroid.model.Library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * An AsyncTask which iterates through every installed app and finds any that use a Library.
 *
 * Created by michaelcarrano on 8/30/14.
 */
public class DetectorAsyncTask extends AsyncTask<Void, Void, List<AppSource>> {

    private static Context mContext;

    private final Callbacks mCallbacks;

    private final PackageManager mPackageManager;

    public DetectorAsyncTask(Context context, PackageManager packageManager, Callbacks callbacks) {
        mPackageManager = packageManager;
        mCallbacks = callbacks;
        mContext = context;
    }

    /**
     * Detects all libraries by trying to load the pattern classpath in a given Application
     */
    private static AppSource detect(PackageInfo pkg) {
        ArrayList<Library> libraries = new ArrayList<Library>();
        // Loop through known libraries
        for (Library library : Libraries.getInstance().getLibraries()) {
            try {
                Context ctx = mContext.createPackageContext(pkg.packageName,
                        Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
                Class<?> clazz = Class.forName(library.getPath(), false, ctx.getClassLoader());

                // Detected a library!!!
                if (clazz != null) {
                    libraries.add(library);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Only return AppSource if app has a library
        return libraries.size() > 0 ? new AppSource(pkg, libraries) : null;
    }

    @Override
    protected List<AppSource> doInBackground(Void... voids) {
        List<AppSource> appSources = new ArrayList<AppSource>();
        List<ApplicationInfo> installedApplications = mPackageManager.getInstalledApplications(0);
        for (int i = 0; i < installedApplications.size(); i++) {
            ApplicationInfo appInfo = installedApplications.get(i);
            try {
                PackageInfo pkgInfo = mPackageManager.getPackageInfo(appInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                AppSource src = detect(pkgInfo);
                if (src != null) {
                    appSources.add(src);
                }

            } catch (PackageManager.NameNotFoundException e) {
                Log.i(this.getClass().getSimpleName(), "doInBackground: " + e.toString());
            }
        }
        return appSources;
    }

    @Override
    protected void onPostExecute(List<AppSource> sources) {
        AppSources.getInstance().setSources(sources);
        mCallbacks.onTaskFinished();
    }

    public static interface Callbacks {

        public void onTaskFinished();
    }

}
