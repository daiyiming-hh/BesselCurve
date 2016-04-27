package com.example.daiyiming.besselcurve.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.daiyiming.besselcurve.utils.BesselTools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by daiyiming on 2016/4/27.
 */
public class BesselView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder = null;
    private List<BesselTools.Point> mPoints = new ArrayList<>();
    private Paint mPaintLine = null;
    private Paint mPaintPoint = null;
    private float mRadius = 30;
    private Path mPath = new Path();
    private int mSelectPosition = -1;
    private Random random = new Random();

    public BesselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLACK);
        mPaintLine.setDither(true);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(4);
        mPaintLine.setStrokeCap(Paint.Cap.ROUND);

        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.RED);
        mPaintPoint.setDither(true);
        mPaintPoint.setAntiAlias(true);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setAlpha(120);
        mPaintPoint.setStrokeWidth(4);

        mPoints.add(new BesselTools.Point(250, 250));
        mPoints.add(new BesselTools.Point(100, 400));
        mPoints.add(new BesselTools.Point(400, 400));
    }

    private void draw() {
        Canvas canvas = mHolder.lockCanvas();

        canvas.drawColor(Color.WHITE);
        mPath.reset();
        for (int i = 0; i < 100; i ++) {
            BesselTools.Point res = BesselTools.caculatePoint(mPoints, (float) i / 100);
            if (i == 0) {
                mPath.moveTo(res.x, res.y);
            } else {
                mPath.lineTo(res.x, res.y);
            }
        }
        canvas.drawPath(mPath, mPaintLine);

        for (BesselTools.Point point : mPoints) {
            canvas.drawCircle(point.x, point.y, mRadius, mPaintPoint);
        }

        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                for (int i = 0; i < mPoints.size(); i ++) {
                    BesselTools.Point point = mPoints.get(i);
                    if (isInner(event.getX(), event.getY(), point.x, point.y, mRadius)) {
                        mSelectPosition = i;
                        break;
                    }
                }
            } break;
            case MotionEvent.ACTION_MOVE: {
                if (mSelectPosition == -1) {
                    return true;
                }
                mPoints.get(mSelectPosition).reset(event.getX(), event.getY());
                draw();
            } break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                mSelectPosition = -1;
            } break;
        }
        return true;
    }


    private boolean isInner(float downX, float downY, float centerX, float centerY, float radius) {
        if (Math.sqrt(Math.pow(downX - centerX, 2) + Math.pow(downY - centerY, 2)) <= radius) {
            return true;
        }
        return false;
    }

    public void add() {
        random.setSeed(System.currentTimeMillis());
        mPoints.add(mPoints.size() - 1, new BesselTools.Point(random.nextInt(getWidth()), random.nextInt(getHeight())));
        draw();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
