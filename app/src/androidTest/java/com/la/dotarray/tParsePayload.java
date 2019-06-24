package com.la.dotarray;

public class tParsePayload {
    static boolean[][] parsePayload(byte[] payload) {
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

    public static void main(String[] args) {
        byte[] t = {0x01,0x02,0x03,0x04,0x01,0x02,0x03,0x04,
                0x01,0x02,0x03,0x04,0x01,0x02,0x03,0x04,
                0x01,0x02,0x03,0x04,0x01,0x02,0x03,0x04,
                0x01,0x02,0x03,0x04,0x01,0x02,0x03,0x21};
        boolean[][] parsed = parsePayload(t);

        for (int row=0;row<16;row++) {
            System.out.println("r");
            for (int col=0;col<16;col++) {
                System.out.println(parsed[row][col]);
            }
        }
    }
}
