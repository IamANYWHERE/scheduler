package com.toplyh.android.scheduler.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by 田雍恺 on 2017/12/28.
 */

public class MyProgressBar extends ProgressBar{
    private String text_progress;
    private Paint mPaint;

    public MyProgressBar(Context context){
        super(context);
        initPaint();
    }
    public MyProgressBar(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        initPaint();
    }
    public MyProgressBar(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initPaint();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        setText_progress(progress);
    }
    @Override
    protected synchronized  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text_progress, 0, this.text_progress.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2 ) - rect.centerY();
        canvas.drawText(this.text_progress,x,y,this.mPaint);
    }

    private void initPaint(){
        this.mPaint=new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(45);
    }
    private void setText_progress(int progress) {
        int i=(int)((progress * 1.0f / this.getMax()) * 100);
        this.text_progress = String.valueOf(i) + "%";
    }
}
