package com.la.dotarray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.jar.Attributes;

public class DotArrayView extends View {
    private final String TAG = DotArrayView.class.getSimpleName();
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint;
    final int DOT_ARRAY_N = 16;
    final int DOT_ARRAY_NUM = 8;
    boolean[][] nowDotPaint = new boolean[DOT_ARRAY_NUM][DOT_ARRAY_N*DOT_ARRAY_N];
    float[][] nowDotX = new float[DOT_ARRAY_NUM][DOT_ARRAY_N];
    float[][] nowDotY = new float[DOT_ARRAY_NUM][DOT_ARRAY_N];

    public DotArrayView(Context context, int width, int height) {
        super(context);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        // mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float width = getWidth();
        float height = getHeight();
        Log.d(TAG, "width:" + width);
        Log.d(TAG, "height: " + height);
        // mCanvas.setBitmap(mBitmap);
        // drawBlock(canvas, mPaint,0,0,200);
        drawPanelRow(canvas, mPaint, 0,0,width);

    }

    void drawPanelRow(Canvas canvas, Paint paint, float startX, float startY, float width) {
        int n = 8;
        float innerPaddingRate = (float)0.02;
        float blockWidth = width / (n+(n-1)*innerPaddingRate);
        float padding = blockWidth*innerPaddingRate;
        drawBlock(canvas, paint, startX, startY, blockWidth);
        for(int i=1;i<n;i++) {
            drawBlock(canvas, paint, startX + i*(blockWidth+padding), startY, blockWidth);
        }
    }

    void drawBlock(Canvas canvas, Paint paint, float startX, float startY, float width) {
        Log.d(TAG, "[BEGIN] drawBlock");
        int n = 16;
        float globalPaddingRate = 0;
        float radiusPaddingRate = (float)0.1;
        float padding = width*globalPaddingRate;
        float realX = startX + padding;
        float realY = startY + padding;
        float realLength = width - 2*padding;

        float delta = realLength/n;
        float radius = (delta/2)*(1-radiusPaddingRate);
        float subStartX = realX + delta/2;
        float subStartY = realY + delta/2;
        float[] cXs = new float[n];
        float[] cYs = new float[n];
        for (int i=0;i<n;i++) {
            cXs[i] = subStartX + delta*i;
            cYs[i] = subStartY + delta*i;
        }
        for (float cx:cXs) {
            for (float cy:cYs) {
                canvas.drawCircle(cx, cy, radius, paint);
            }
        }
        Log.d(TAG, "[END] drawBlock");
    }

    float[] calcBlock(float startX, float startY, float width) {
        final int N = DOT_ARRAY_N;
        float globalPaddingRate = 0;
        float padding = width*globalPaddingRate;
        float realX = startX + padding;
        float realY = startY + padding;
        float realLength = width - 2*padding;

        float delta = realLength/N;
        float subStartX = realX + delta/2;
        float subStartY = realY + delta/2;
        float[] cXYs = new float[2*N];
        for (int i=0;i<N;i++) {
            cXYs[i]   = subStartX + delta*i;
            cXYs[N+i] = subStartY + delta*i;
        }

        return cXYs;
    }

    void calcPanelRow(float startX, float startY, float width) {
        int n = DOT_ARRAY_NUM;
        float innerPaddingRate = (float)0.02;
        float blockWidth = width / (n+(n-1)*innerPaddingRate);
        float padding = blockWidth*innerPaddingRate;

        float[] blockXYs;
        blockXYs = calcBlock(0,0,width);
        System.arraycopy(blockXYs, 0, nowDotX[0], 0, DOT_ARRAY_N);
        System.arraycopy(blockXYs, DOT_ARRAY_N, nowDotY[0], DOT_ARRAY_N, DOT_ARRAY_N);
        for(int i=1;i<n;i++) {
            blockXYs = calcBlock((padding+blockWidth)*i,0,width);
            System.arraycopy(blockXYs, 0, nowDotX[i], 0, DOT_ARRAY_N);
            System.arraycopy(blockXYs, DOT_ARRAY_N, nowDotY[i], DOT_ARRAY_N, DOT_ARRAY_N);
        }
    }
}
