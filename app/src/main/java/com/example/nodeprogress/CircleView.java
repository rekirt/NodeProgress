package com.example.nodeprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lc on 16-1-17.
 */
public class CircleView extends View {

    private Paint mPaint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint();;
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆环
        int centre = getWidth()/2;
        int radius = (int)(centre - 5/2);
        mPaint.setColor(Color.BLACK);//设置圆环颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeWidth(5);//设置圆环的宽度
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius, mPaint);
        //画数字
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float textWidth = mPaint.measureText("V");
        canvas.drawText("V",centre-textWidth/2,centre+textWidth/2,mPaint);

    }
























}
