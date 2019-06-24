package com.la.dotarray;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MessageResponser {
    Handler mHandler;

    public MessageResponser() {
        Looper.prepare();
        mHandler = new MessageHandler();

    }

    private static final int MSGTYPE_ORDER = 0x10;
    private static final int MSGTYPE_PAYLOAD = 0x12;

    private static final int ORDERTYPE_LEFT = 0x20;
    private static final int ORDERTYPE_RIGHT = 0x21;
    private static final int ORDERTYPE_UP = 0x22;
    private static final int ORDERTYPE_DOWN = 0x23;

    private static final int PAYLOADTYPE_BASE = 0x30;

    public class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSGTYPE_ORDER:
                    handleOrder();
                    break;
                case MSGTYPE_PAYLOAD:
                    handlePayload();
                    break;
                default:
                    break;
            }
        }
    }

    private void handleOrder() {

    }

    private void handlePayload() {

    }


    
}
