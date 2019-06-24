package com.la.dotarray;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private final String TAG = ConnectThread.class.getSimpleName();
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final UUID mUUID;

    private MessageDispatcher mMsgDispatcher;

    public ConnectThread(BluetoothDevice device, UUID uuid) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        mUUID = uuid;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        Log.d(TAG, "init completed.");
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
//            mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            Log.d(TAG, "[BEGIN]Connection");
            mmSocket.connect();
            Log.d(TAG, "Connected.");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        MessageDispatcher dispatcher = new MessageDispatcher(mmSocket, null);
        dispatcher.run();

    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
