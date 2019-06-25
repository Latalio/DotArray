package com.la.dotarray;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MessageResponser extends Thread {
    Handler mHandler;
    DotArrayView mView;


    public MessageResponser(Looper looper, DotArrayView view) {
        Looper.prepare();
        mHandler = new MessageHandler(looper);
        mView = view;
    }



    public static final int MSGTYPE_ORDER = 0x10;
    public static final int MSGTYPE_PAYLOAD = 0x12;

    private static final int ORDERTYPE_LEFT = 0x20;
    private static final int ORDERTYPE_RIGHT = 0x21;
    private static final int ORDERTYPE_UP = 0x22;
    private static final int ORDERTYPE_DOWN = 0x23;

    private static final int PAYLOADTYPE_BASE = 0x30;


    private static final int PROCESS_UPDATE = 0x40;
    private static final int PROCESS_LEFT = 0x41;
    private static final int PROCESS_RIGHT = 0x42;
    private static final int PROCESS_UP = 0x43;
    private static final int PROCESS_DOWN = 0x44;

    private static final int PROCESS_STOP = -1;
    private static final int PROCESS_IDLE = 0;
    private static final int PROCESS_TIMER = 1;
    private int currProcess = 0;
    private byte[] currBytes;

    public class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSGTYPE_ORDER:
                    handleOrder(msg);
                    break;
                case MSGTYPE_PAYLOAD:
                    handlePayload(msg);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleOrder(Message msg) {
        switch (msg.arg1) {
            case ORDERTYPE_LEFT:
                currProcess = PROCESS_LEFT;
                break;
            case ORDERTYPE_RIGHT:
                currProcess = PROCESS_RIGHT;
                break;
            case ORDERTYPE_UP:
                currProcess = PROCESS_UP;
                break;
            case ORDERTYPE_DOWN:
                currProcess = PROCESS_DOWN;
                break;
        }

    }

    private void handlePayload(Message msg) {
        currProcess = PROCESS_UPDATE;
        currBytes = (byte[])msg.obj;
    }

    @Override
    public void run() {
        int processKeeper = PROCESS_IDLE;
        while(true) {
            switch (currProcess) {
                case PROCESS_UPDATE:
                    boolean[][] parsed = parsePayload(currBytes);
                    mView.update(parsed);
                    currProcess = PROCESS_IDLE;
                    break;
                case PROCESS_LEFT:
                    mView.left();
                    processKeeper = currProcess;
                    currProcess = PROCESS_TIMER;
                    break;
                case PROCESS_RIGHT:
                    mView.right();
                    processKeeper = currProcess;
                    currProcess = PROCESS_TIMER;
                    break;
                case PROCESS_UP:
                    mView.up();
                    processKeeper = currProcess;
                    currProcess = PROCESS_TIMER;
                    break;
                case PROCESS_DOWN:
                    mView.down();
                    processKeeper = currProcess;
                    currProcess = PROCESS_TIMER;
                    break;
                case  PROCESS_TIMER:
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    currProcess = processKeeper;
                    break;
                case PROCESS_IDLE:
                    break;
                case PROCESS_STOP:
                    return;
            }
        }
    }

    boolean[][] parsePayload(byte[] payload) {
        int num = payload.length / 32;
        boolean[][] parsed = new boolean[16][num*16];
        for (int i=0;i<num;i++) {
            for (int j=0;j<32;j++) {
                int row = j/2;
                int col = (j%2==0)?0:8;
                for (int k=0;k<8;k++) {
                    parsed[row][i*32+col+k] = (payload[i*32+j] & (0x01<<7-k))>0;
                }
            }
        }
        return parsed;
    }





    
}
