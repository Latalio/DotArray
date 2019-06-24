package com.la.dotarray;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends FragmentActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    DotArrayView mDotArrayView;
    FrameLayout mMainLayout;
    LinearLayout mLinearLayout;
    ListView mListDev;
    LayoutInflater mInflater;
    FrameLayout mListDevWarpper;
    DeviceDialogFragment mDevFrag;

    BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 9088;

    private BluetoothDevice mDevice;
    private final String TARGET_DEVICE = "HC-02";
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

//        Log.d(TAG, "[1] onCreate");
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
//        mDotArrayView = new DotArrayView(this,
//                displayMetrics.widthPixels, displayMetrics.heightPixels);
        // mLinearLayout = findViewById(R.id.layout);
        // mLinearLayout = new LinearLayout(this);
        // mLinearLayout.addView(mDotArrayView);
//        setContentView(mDotArrayView);
//        Log.d(TAG, "[END] onCreate");
        mMainLayout = findViewById(R.id.mainlayout);

//        mDevFrag = new DeviceDialogFragment();
//        mDevFrag.show(getSupportFragmentManager(), "devfrag");





//        mInflater = LayoutInflater.from(this);
//
//        mListDevWarpper = (FrameLayout) mInflater.inflate(R.layout.listdev, mMainLayout);
//        mListDev = mListDevWarpper.findViewById(R.id.listdev);
////        ListView t = (ListView) mInflater.inflate(R.layout.listdev, null).findViewById(R.id.listdev);
////        if (t.equals(mListDev)) Log.d(TAG, "the same");
////        else Log.d(TAG, "not the same");
////        ListView.LayoutParams lp = new ListView.LayoutParams(new ViewGroup.LayoutParams(200,200));
////        mListDev.setLayoutParams(lp);
//        mArrayAdapter = new ArrayAdapter<>(this, R.layout.listdev_item);
//        mListDev.setAdapter(mArrayAdapter);

//        Button btnT = (Button) mInflater.inflate(R.layout.btn_t, null);


//        mListDevWarpper.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    Log.d(TAG, "<onFocusChange>ListDev lose focus.");
//                    mMainLayout.removeView(mListDevWarpper);
//                }
//            }
//        });













    }

//    // Create a BroadcastReceiver for ACTION_FOUND
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            // When discovery finds a device
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Get the BluetoothDevice object from the Intent
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // Add the name and address to an array adapter to show in a ListView
//                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        }
//    };
//    // Register the BroadcastReceiver
//    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
////    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy


    @Override
    protected void onResume() {
        super.onResume();


//        mDevFrag.addDevPaired("p1");
//        mDevFrag.addDevPaired("p2");
//
//
//        mDevFrag.addDevAvailable("t1");
//        mDevFrag.addDevAvailable("t1");
//        mDevFrag.addDevAvailable("t1");
//        mDevFrag.addDevAvailable("t1");
//        mDevFrag.addDevAvailable("t1");
//        mDevFrag.addDevAvailable("t6");

        // 获取适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth");
        }
        Log.d(TAG, "Device support Bluetooth");
//
        // 启用蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        Log.d(TAG, "bluetooth adapter enabled.");

        // 选择HC-02
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
//                mDevFrag.addDevPaired(device.getName() + "\n" + device.getAddress());
                if (device.getName().equals(TARGET_DEVICE)) {
                    mDevice = device;
                    Log.d(TAG, "Device founded.");
                    break;
                }
            }
        }

        if (mDevice==null) return;
        ConnectThread connectThread = new ConnectThread(mDevice, MY_UUID);
        connectThread.run();
    }









}
