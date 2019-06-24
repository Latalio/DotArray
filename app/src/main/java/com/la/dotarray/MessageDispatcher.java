package com.la.dotarray;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MessageDispatcher extends Thread {
    private final String TAG = MessageDispatcher.class.getSimpleName();

    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    private Handler mHandler;

    private final int CHECKBUFFER_LENGTH = 2048;
    private CheckBuffer mCheckBuffer = new CheckBuffer();


    // Protocol
    private final byte STARTBYTE_COMMAND = 0x7A;
    private final byte STARTBYTE_TEXT = 0x7B;
    private final byte STARTBYTE_GRAPHICS = 0x7C;
    private final byte ENDBYTE = (byte) 0xAB;

    private final int LENGTH_COMMAND = 3;
    private final int LENGTH_TEXT = 258;
    private final int LENGTH_GRAPHICS = 0; // todo

    public MessageDispatcher(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }


    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity

                if (bytes>0) {
                    Log.d(TAG, "received bytes:" + bytes);
                    StringBuffer s = new StringBuffer();
                    s.append(buffer);
                    Log.d(TAG, Byte.toString(buffer[0]));
                    mCheckBuffer.add(buffer, bytes);
                    //// here not consider the transformation error
                    if (mCheckBuffer.check()) { // send message
                        Message msg = Message.obtain();
                        msg.what = 0; // msg type
                    }
                }
//

            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }


    private class CheckBuffer {
        private final byte[] buffer = new byte[CHECKBUFFER_LENGTH];

        private int mark = 0; // next position to write a byte.
        private int length = 0;

        // be careful that there may be a bug when mark out of range
        void add(byte[] src, int numBytes) {
            System.arraycopy(src, 0, buffer, mark, numBytes);
            length += numBytes;
        }

        boolean check() {
            switch (buffer[0]) {
                case STARTBYTE_COMMAND:
                    return (length==LENGTH_COMMAND)&&(buffer[LENGTH_COMMAND-1]==ENDBYTE);
                default:
                    return false;
            }
        }
    }
}