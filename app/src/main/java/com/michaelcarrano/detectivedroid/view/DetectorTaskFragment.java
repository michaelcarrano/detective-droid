package com.michaelcarrano.detectivedroid.view;

import com.michaelcarrano.detectivedroid.R;
import com.michaelcarrano.detectivedroid.task.DetectorAsyncTask;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by michaelcarrano on 9/13/14.
 */
public class DetectorTaskFragment extends DialogFragment implements DetectorAsyncTask.Callbacks {

    public static final String TAG = "DetectorTaskFragment";

    public static final int TASK_REQUEST_CODE = 0;

    DetectorAsyncTask mDetectorTask;

    ProgressBar mProgressBar;

    TextView mProgressText;

    public void setTask(DetectorAsyncTask task) {
        mDetectorTask = task;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Stop the dialog from being destroyed on orientation change
        setRetainInstance(true);

        mDetectorTask = new DetectorAsyncTask(getActivity().getPackageManager(), this);
        mDetectorTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detector_task, container);
        mProgressText = (TextView) view.findViewById(R.id.progress_message_1);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        getDialog().setTitle(R.string.progress_dialog_title);
        getDialog().setCanceledOnTouchOutside(false);

        return view;
    }

    // This is to work around what is apparently a bug. If you don't have it
    // here the dialog will be dismissed on rotation, so tell it not to dismiss.
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }

        super.onDestroyView();
    }

    // Also when we are dismissed we need to cancel the task.
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mDetectorTask != null) {
            mDetectorTask.cancel(false);

            Fragment target = getTargetFragment();
            if (target != null) {
                if (!(target instanceof Callbacks)) {
                    throw new IllegalStateException(
                            "DetectorTaskFragment target must implement its callbacks");
                }

                ((Callbacks) target).onTaskCancelled();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // If the task finished while we were outside of our activity, dismiss ourselves
        if (mDetectorTask == null) {
            dismiss();
        }
    }

    // DetectorAsyncTask callback
    @Override
    public void onProgressUpdate(int max, int percent, CharSequence app) {
        CharSequence progressText = getString(R.string.progress_dialog_message_1, app);
        mProgressText.setText(progressText);
        mProgressBar.setMax(max);
        mProgressBar.setProgress(percent);
    }

    // This is also called by the DetectorAsyncTask.
    @Override
    public void onTaskFinished() {
        // We want to dismiss ourself here and indicate to the parent fragment that we've succeeded
        // However, dismissing ourselves will cause a crash if we aren't actually active
        // We'll get around this by setting mTask to null in that case - onResume will then call dismiss()

        // Setting mTask to null also ensures that we don't declare the task cancelled on dismiss
        mDetectorTask = null;

        if (isResumed()) {
            dismiss();
        }

        // Tell the fragment that we are done.
        Fragment target = getTargetFragment();
        if (target != null) {
            if (!(target instanceof Callbacks)) {
                throw new IllegalStateException(
                        "DetectorTaskFragment target must implement its callbacks");
            }

            ((Callbacks) target).onTaskFinished();
        }
    }

    public static interface Callbacks {

        public void onTaskFinished();

        public void onTaskCancelled();
    }

}
