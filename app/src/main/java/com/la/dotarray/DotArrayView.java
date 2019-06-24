package com.la.dotarray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class DotArrayView extends View {
    private final String TAG = DotArrayView.class.getSimpleName();
    Paint mPaint;

    // dots parameters
    final int ORDER = 16;
    final int BLOCK_NUM = 8;
    final int rows = ORDER;
    final int cols = ORDER * BLOCK_NUM;

    // content
    boolean[][] nowDots = new boolean[rows][cols];
    boolean[][] cacheDots;

    // view parameters
    float width;
    float height;

    // location
    float[][] Xs = new float[rows][cols];
    float[][] Ys = new float[rows][cols];

    // dots parameters
    float mDotBlockWidth = 0;
    float mRadius = 0;

    public DotArrayView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);

        nowDots[0][0] = true;
        nowDots[rows-1][cols-1] = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        // mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Log.d(TAG, "width:" + width);
        Log.d(TAG, "height: " + height);
        width = getWidth();
        height = getHeight();
        calculate();
        // mCanvas.setBitmap(mBitmap);
        // drawBlock(canvas, mPaint,0,0,200);
        display(canvas);

    }

    private void calculate() {
        float width = this.width / BLOCK_NUM;
        float startX = 0;
        float startY = (this.height - width) / 2;
        float dotBlockWidth = width / ORDER;
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                Xs[row][col] = startX + dotBlockWidth/2 + col*dotBlockWidth;
                Ys[row][col] = startY + dotBlockWidth/2 + row*dotBlockWidth;
            }
        }
        float radiusPaddingRate = (float)0.1;
        float radiusPadding = radiusPaddingRate * dotBlockWidth;

        mDotBlockWidth = dotBlockWidth;
        mRadius = dotBlockWidth/2 - radiusPadding;
    }

    private void display(Canvas canvas) {
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                if (nowDots[row][col]) {
                    canvas.drawCircle(Xs[row][col], Ys[row][col], mRadius, mPaint);
                }
            }
        }
    }

    public void update(boolean[][] newDots) {
        cacheDots = new boolean[rows][];
        if (newDots.length > nowDots.length) {
            for (int row=0;row<rows;row++) {
                cacheDots[row] = Arrays.copyOf(newDots[row], newDots[row].length);
            }
        } else {
            for (int row=0;row<rows;row++) {
                cacheDots[row] = Arrays.copyOf(newDots[row], newDots[row].length);
            }
        }
        for (int row=0;row<rows;row++) {
            nowDots[row] = Arrays.copyOf(cacheDots[row], nowDots[row].length);
        }
        invalidate();
    }

    public void left() {

    }

    public void right() {

    }

    public void up() {

    }

    public void down() {

    }


}
