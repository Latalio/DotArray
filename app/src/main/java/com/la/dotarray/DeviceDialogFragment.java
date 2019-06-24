package com.la.dotarray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DeviceDialogFragment extends DialogFragment {
    private final String TAG = DeviceDialogFragment.class.getSimpleName();

    private ArrayAdapter<String> mAdapterDevPaired;
    private ArrayAdapter<String> mAdapterDevAvailable;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        LinearLayout rootLayout = (LinearLayout)inflater.inflate(R.layout.listdev, null);

        builder.setView(rootLayout)
                .setNeutralButton(R.string.scanning, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // todo
                    }
                });

        Context context = getContext();
        if (context == null) {
            Log.e(TAG, "<onCreateDialog>context is null.");

        }

        ListView listDevPaired = rootLayout.findViewById(R.id.list_dev_paired);
        mAdapterDevPaired = new ArrayAdapter<>(context, R.layout.listdev_item);
        listDevPaired.setAdapter(mAdapterDevPaired);

        ListView listDevAvailable = rootLayout.findViewById(R.id.list_dev_available);
        mAdapterDevAvailable = new ArrayAdapter<>(context, R.layout.listdev_item);
        listDevAvailable.setAdapter(mAdapterDevAvailable);

        Log.d(TAG, "<onCreateDialog>Fragment completed.");
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void addDevPaired(String dev) {
        mAdapterDevPaired.add(dev);
    }

    public void addDevAvailable(String dev) {
        mAdapterDevAvailable.add(dev);
    }
}
