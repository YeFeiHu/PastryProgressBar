package com.yefeihu.pastryprogressbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YeFeiHu on 2016/6/2.
 */
public class PastryProgressBar extends View implements Handler.Callback{

    public PastryProgressBar(Context context) {
        super(context);
        initPaint(context);
    }

    public PastryProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public PastryProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PastryProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint(context);

    }

    private Paint mBgPaint;
    private Paint mPastryPaint;
    private int mBgColor;
    private int mPastryColor;
    private float mDensity;
    private int mStartColor;
    private int mPreProgress;

    private void initPaint(Context context){
        mBgColor = Color.RED;
        int a = Color.alpha(mBgColor);
        mStartColor = Color.argb((a+1)/3,Color.red(mBgColor),Color.green(mBgColor),Color.blue(mBgColor));
        mBgPaint = new Paint(mBgColor);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);

        mPastryColor = Color.GREEN;
        mPastryPaint = new Paint(mPastryColor);
        mPastryPaint.setAntiAlias(true);
        mPastryPaint.setStyle(Paint.Style.FILL);

        mDensity = context.getResources().getDisplayMetrics().density;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mMaxProgress == mNowPregress && mMaxProgress == mPreProgress){
            return;
        }
        if(mNowPregress < mMaxProgress/20){
            mNowPregress = mMaxProgress/20;
            mPreProgress = mNowPregress;
        }

        if(mNowPregress > mPreProgress){
            int speed = 1;
            if(mNowPregress-mNowPregress > 70){
                speed = 9;
            }if(mNowPregress-mNowPregress > 60){
                speed = 8;
            }if(mNowPregress-mNowPregress > 50){
                speed = 7;
            }else if(mNowPregress-mNowPregress > 40){
                speed = 6;
            }else if(mNowPregress-mNowPregress > 30){
                speed = 5;
            }else if(mNowPregress-mNowPregress > 20){
                speed = 4;
            }else if(mNowPregress-mNowPregress > 10) {
                speed = 3;
            }else if(mNowPregress - mNowPregress > 5){
                speed = 2;
            }
            mPreProgress+= speed;
            if(mNowPregress == mMaxProgress){
                mPreProgress = mMaxProgress;
            }

        }else if(mNowPregress < mPreProgress){
            mPreProgress = 1;
        }

        int curWidth = (int) ((mPreProgress*1.000f/mMaxProgress)*getWidth());
        LinearGradient mGradient = new LinearGradient(0,0,curWidth,0, new int[]{mStartColor,mBgColor,mBgColor,mStartColor},new float[] {0.0f, 0.40f, 0.90f,1 }, Shader.TileMode.REPEAT);



        mBgPaint.setColor(mBgColor);
        mBgPaint.setShader(mGradient);
       canvas.drawRect(0,0, curWidth,getHeight(),mBgPaint);

        if(mPostDraw == null){
            mPostDraw = new DrawRunnable();
        }
        postOnAnimationDelayed(mPostDraw,20);

    }
    private DrawRunnable mPostDraw;

    private class DrawRunnable implements Runnable {
        @Override
        public void run() {
            postInvalidate();
        }
    }

    private int mWidth;
    private int mHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST || widthSpecMode == MeasureSpec.EXACTLY){
            mWidth = widthSpecSize;
        }else{
            mWidth = 0;
        }

        if(heightSpecMode == MeasureSpec.UNSPECIFIED || heightSpecMode == MeasureSpec.AT_MOST){
            mHeight = (int) (15*mDensity);
        }else{
            mHeight = heightSpecSize;
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int mMaxProgress;
    private int mNowPregress;
    public void setNowProgress(int progress){
        this.mNowPregress = progress;
        this.invalidate();
    }
    public void setMaxPregress(int pregress){
        this.mMaxProgress = pregress;
    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
