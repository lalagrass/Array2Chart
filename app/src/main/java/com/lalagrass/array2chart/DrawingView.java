package com.lalagrass.array2chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class DrawingView extends SurfaceView implements SurfaceHolder.Callback {

    private double[] data;
    private static int spaceWidth;
    private Paint boundPaint;
    private Paint readyPaint;
    private int _width;
    private int _height;
    private int availableWidth;
    private int availableHeight;
    private static int virtualWidth;

    public DrawingView(Context context) {
        super(context);
        init(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void SetupScreen() {
        SurfaceHolder holder = getHolder();
        if (holder != null) {
            Canvas c = holder.lockCanvas();
            if (c != null) {
                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                DrawBound(c);
                DrawData(c);
                holder.unlockCanvasAndPost(c);
            }
        }
    }

    private void init(Context context) {
        boundPaint = new Paint();
        boundPaint.setARGB(255, 250, 50, 50);
        boundPaint.setStyle(Paint.Style.STROKE);
        boundPaint.setStrokeWidth(0);
        readyPaint = new Paint();
        readyPaint.setARGB(255, 50, 250, 50);
        readyPaint.setStyle(Paint.Style.STROKE);
        readyPaint.setStrokeWidth(0);
        this.getHolder().addCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h > 0 && _height != h && w > 0 && _width != w) {
            spaceWidth = w > h ? h / 8 : w / 8;
            _width = w;
            _height = h;
            availableWidth = _width - 2 * spaceWidth;
            availableHeight = _height - 2 * spaceWidth;
            ViewGroup.LayoutParams params = this.getLayoutParams();
            this.setLayoutParams(params);
        }
    }

    public void UpdateSpectrum(double[] retX) {
        if (retX == null || retX.length == 0)
            return;
        data = retX;
        SurfaceHolder holder = getHolder();
        if (holder != null) {
            Canvas c = holder.lockCanvas();
            if (c != null) {
                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                DrawBound(c);
                DrawData(c);
                holder.unlockCanvasAndPost(c);
                data = null;
            }
        }
    }

    private void DrawBound(Canvas c) {
        c.drawRect(spaceWidth, spaceWidth, _width - spaceWidth, _height - spaceWidth, boundPaint);
        int middle = _height / 2;
        c.drawLine(spaceWidth, middle, _width - spaceWidth, middle, boundPaint);
    }

    private void DrawData(Canvas c) {
        if (data != null && data.length > 0) {
            double scaleH = (double)availableHeight / 2;
            float middle = (float)_height / 2;
            double scaleW = (double)availableWidth / data.length;
            float startX = spaceWidth;
            float startY = (float) middle;
            float endX = _width - spaceWidth;
            float endY = (float) middle;
            float nx1 = (float) (spaceWidth);
            float ny1 = (float) (middle + data[0] * scaleH);
            c.drawLine(startX, startY, nx1, ny1, readyPaint);
            for (int i = 0; i < data.length - 1; i++) {
                float x1 = nx1;
                float x2 = (float) (spaceWidth + scaleW * (i + 1));
                float y1 = ny1;
                float y2 = (float) (middle + data[i + 1] * scaleH);
                c.drawLine(x1, y1, x2, y2, readyPaint);
                nx1 = x2;
                ny1 = y2;
            }
            c.drawLine(nx1, ny1, endX, endY, readyPaint);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        SetupScreen();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
