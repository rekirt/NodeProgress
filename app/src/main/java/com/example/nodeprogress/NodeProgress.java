package com.example.nodeprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
//http://www.mamicode.com/info-detail-1017561.html
public class NodeProgress extends View{

	private int nodesNum ;
	private Drawable progressingDrawable;
	private Drawable unprogressingDrawable;
	private Drawable progresFailDrawable;
	private Drawable progresSuccDrawable;
	private int nodeRadius;
	private int processingLineColor;
	private float textSize;
//	private int progressLineHeight;
	private int currNodeNO;
	private int currNodeState;
	Context mContext;

	int mWidth,mHeight;
	private Paint mPaint;
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private ArrayList<Node> nodes;

	private int DEFAULT_LINE_COLOR = Color.BLUE;
	public NodeProgress(Context context) {
		this(context,null);

	}
	public NodeProgress(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	public NodeProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.MutiProgress);
		nodesNum = mTypedArray.getInteger(R.styleable.MutiProgress_nodesNum, 1);
		nodeRadius = mTypedArray.getDimensionPixelSize(R.styleable.MutiProgress_nodeRadius, 10);
		textSize = mTypedArray.getDimension(R.styleable.MutiProgress_textSize,20);
		progressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progressingDrawable);
		unprogressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_unprogressingDrawable);
		progresFailDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresFailDrawable);
		progresSuccDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresSuccDrawable);
		processingLineColor = mTypedArray.getColor(R.styleable.MutiProgress_processingLineColor, DEFAULT_LINE_COLOR);
		currNodeState = mTypedArray.getInt(R.styleable.MutiProgress_currNodeState, 1);
		currNodeNO = mTypedArray.getInt(R.styleable.MutiProgress_currNodeNO, 1);

	}


	public Drawable getUnprogressingDrawable() {
		return unprogressingDrawable;
	}

	public void setUnprogressingDrawable(Drawable unprogressingDrawable) {
		this.unprogressingDrawable = unprogressingDrawable;
	}

	public Drawable getProgressingDrawable() {
		return progressingDrawable;
	}

	public void setProgressingDrawable(Drawable progressingDrawable) {
		this.progressingDrawable = progressingDrawable;
	}

	public Drawable getProgresFailDrawable() {
		return progresFailDrawable;
	}

	public void setProgresFailDrawable(Drawable progresFailDrawable) {
		this.progresFailDrawable = progresFailDrawable;
	}

	public Drawable getProgresSuccDrawable() {
		return progresSuccDrawable;
	}

	public void setProgresSuccDrawable(Drawable progresSuccDrawable) {
		this.progresSuccDrawable = progresSuccDrawable;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		
		mBitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		mPaint = new Paint();
		mPaint.setColor(processingLineColor);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mCanvas = new Canvas(mBitmap);
		nodes = new ArrayList<Node>();
		float nodeWidth = ((float)mWidth)/(nodesNum+1);
		for(int i=0;i<nodesNum;i++)
		{
			Node node = new Node();
			node.level = "V"+(i+10);
			node.score = (i+1)*100;
			if(i==0)
				node.mPoint = new Point(((int)nodeWidth*(i+1))-nodeRadius,mHeight/2-nodeRadius);
			else if(i==(nodesNum-1))
				node.mPoint = new Point(((int)nodeWidth*(i+1))-nodeRadius,mHeight/2-nodeRadius);
			else
				node.mPoint = new Point(((int)nodeWidth*(i+1))-nodeRadius,mHeight/2-nodeRadius);
			if(currNodeNO == i)
				node.type = 1;
			else
				node.type = 0;
			nodes.add(node);
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		DrawProgerss();
		Log.v("ondraw", "mBitmap="+mBitmap);
		if(mBitmap!=null)
		{
			canvas.drawBitmap(mBitmap, new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), mPaint);
		}
		for(int i=0;i<nodes.size();i++)
		{
			Node node = nodes.get(i);
			Log.v("ondraw", node.mPoint.x +";y="+node.mPoint.y);
			mPaint.setTextSize(textSize);
			if(i<currNodeNO)
			{
				progressingDrawable.setBounds(node.mPoint.x, node.mPoint.y, node.mPoint.x + nodeRadius * 2,node.mPoint.y + nodeRadius*2);
				Rect rect = progressingDrawable.getBounds();
				progressingDrawable.draw(canvas);
				float textWidth = mPaint.measureText(node.level);
				float textHeight = mPaint.measureText("V");
				float scoreWidth = mPaint.measureText(node.score+"");
				canvas.drawText(node.level, rect.centerX() - textWidth / 2, rect.centerY() + textHeight / 2, mPaint);
				canvas.drawText(node.score + "", rect.centerX() - scoreWidth / 2, rect.centerY() + rect.width(), mPaint);
			}
			else if(i==currNodeNO)
			{
				if(currNodeState == 1)
				{
					progresSuccDrawable.setBounds(node.mPoint.x,  node.mPoint.y , node.mPoint.x + nodeRadius*2,node.mPoint.y + nodeRadius*2);
					Rect rect = progresSuccDrawable.getBounds();
					progresSuccDrawable.draw(canvas);
					float textWidth = mPaint.measureText(node.level);
					float textHeight = mPaint.measureText("V");
					float scoreWidth = mPaint.measureText(node.score + "");
					canvas.drawText(node.level, rect.centerX() - textWidth / 2, rect.centerY() + textHeight / 2, mPaint);
					canvas.drawText(node.score + "", rect.centerX() - scoreWidth / 2, rect.centerY() + rect.width(), mPaint);

				}
				else
				{
					progresFailDrawable.setBounds(node.mPoint.x, node.mPoint.y, node.mPoint.x + nodeRadius * 2, node.mPoint.y + nodeRadius * 2);
					Rect rect = progresFailDrawable.getBounds();
					progresFailDrawable.draw(canvas);
					float textWidth = mPaint.measureText(node.level);
					float textHeight = mPaint.measureText("V");
					float scoreWidth = mPaint.measureText(node.score + "");
					canvas.drawText(node.level, rect.centerX() - textWidth / 2, rect.centerY() + textHeight / 2, mPaint);
					canvas.drawText(node.score + "", rect.centerX() - scoreWidth / 2, rect.centerY() + rect.width(), mPaint);
				}
			}
			else
			{
				unprogressingDrawable.setBounds(node.mPoint.x, node.mPoint.y, node.mPoint.x + nodeRadius * 2, node.mPoint.y + nodeRadius * 2);
				Rect rect = unprogressingDrawable.getBounds();
				unprogressingDrawable.draw(canvas);
				float textWidth = mPaint.measureText(node.level);
				float textHeight = mPaint.measureText("V");
				float scoreWidth = mPaint.measureText(node.score+"");
				canvas.drawText(node.level,rect.centerX()-textWidth/2,rect.centerY()+textHeight/2,mPaint);
				canvas.drawText(node.score+"",rect.centerX()-scoreWidth/2,rect.centerY()+rect.width(),mPaint);
			}
		}
	}
	private void DrawProgerss()
	{
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.parseColor("#FFFFFF"));
		mCanvas.drawRect(0, 0, mWidth, mHeight, bgPaint);
		mPaint.setStrokeWidth(nodeRadius/2);
		mCanvas.drawLine(nodeRadius, mHeight/2, nodes.get(currNodeNO).mPoint.x + nodeRadius, nodes.get(currNodeNO).mPoint.y + nodeRadius, mPaint);  //�߶�2��ȥ��nodeRadius
		mPaint.setColor(Color.parseColor("#dddddd"));
		mCanvas.drawLine(nodes.get(currNodeNO).mPoint.x +nodeRadius, nodes.get(currNodeNO).mPoint.y + nodeRadius, mWidth-nodeRadius, mHeight/2, mPaint);  //�߶�2��ȥ��nodeRadius
	}
	class Node
	{
		Point mPoint;
		int type;
		String level;
		int score;
	}
}
