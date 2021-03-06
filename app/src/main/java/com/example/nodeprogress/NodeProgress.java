package com.example.nodeprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

//http://www.mamicode.com/info-detail-1017561.html
//lc修改
public class NodeProgress extends View{

	private float textSize;//字体大小
	private int nodeCount;//节点个数
	private int nodeRadius;//节点半径
	private Drawable progressingDrawable;//有进度的进度条
	private Drawable unprogressingDrawable;//默认底层进度条
	private Drawable progresFailDrawable;//失败的进度条
	private Drawable progresSuccDrawable;//成功的进度条
	private int processingLineColor;//
//	private int progressLineHeight;
	private int currNodeNO;
	private int currNodeState;
	Context mContext;
	int mWidth,mHeight;
	private Paint mPaint;
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private ArrayList<Node> nodes;//节点个数
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
		nodeCount = mTypedArray.getInteger(R.styleable.MutiProgress_nodesNum, 6);
		currNodeNO = mTypedArray.getInt(R.styleable.MutiProgress_currNodeNO,1);
		nodeRadius = mTypedArray.getDimensionPixelSize(R.styleable.MutiProgress_nodeRadius, 10);
		textSize = mTypedArray.getDimension(R.styleable.MutiProgress_textSize,20);
		progressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progressingDrawable);
		unprogressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_unprogressingDrawable);
		progresFailDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresFailDrawable);
		progresSuccDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresSuccDrawable);
		processingLineColor = mTypedArray.getColor(R.styleable.MutiProgress_processingLineColor, DEFAULT_LINE_COLOR);
		currNodeState = mTypedArray.getInt(R.styleable.MutiProgress_currNodeState, 1);

	}

	/**
	 * 设置总节点个数
	 * @param nCount
	 */
	public void setNodeCount(int nCount){
		nodeCount = nCount;
	}

	/**
	 * 设置节点半径
	 * @param radius
	 */
	public void setNodeRadius(int radius){
		nodeRadius = radius;
	}


	/**
	 * 设置当前进度
	 * @param nodeNo
	 */
	public void setCurrentNodeNo(int nodeNo){
		currNodeNO = nodeNo;
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
		//根据布局大小创建一个画板
		mBitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		mPaint = new Paint();
		mPaint.setColor(processingLineColor);//画笔颜色
		mPaint.setAntiAlias(true);//添加上抗锯齿,图形边缘更清晰
		mPaint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子，Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
		mPaint.setStrokeCap(Paint.Cap.ROUND);//设置笔触的风格
		mCanvas = new Canvas(mBitmap);
		nodes = new ArrayList<Node>();
		float nodeWidth = ((float)mWidth)/(nodeCount+1);
		for(int i=0;i< nodeCount;i++)
		{
			Node node = new Node();
			node.level = "V"+(i+10);
			node.score = (i+1)*100;
			if(i==0)
				node.mPoint = new Point(((int)nodeWidth*(i+1))-nodeRadius,mHeight/2-nodeRadius);
			else if(i==(nodeCount -1))
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
		//将画板中的数据画到视图上
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
			}else if(i==currNodeNO)
			{
				if(currNodeState == 1){
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

	/**
	 * 向画板上面画内容
	 */
	private void DrawProgerss()
	{
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.parseColor("#FFFFFF"));
		mCanvas.drawRect(0, 0, mWidth, mHeight, bgPaint);//画背景色
		mPaint.setStrokeWidth(nodeRadius / 2);//设置画笔的粗细
		mPaint.setColor(Color.parseColor("#dddddd"));
		mCanvas.drawLine(nodeRadius/2, mHeight/2, mWidth-nodeRadius/2, mHeight / 2, mPaint);//没有进度的颜色
		mPaint.setColor(processingLineColor);
		mCanvas.drawLine(nodeRadius/2, mHeight/2, nodes.get(currNodeNO).mPoint.x-nodeRadius/2 + nodeRadius, nodes.get(currNodeNO).mPoint.y + nodeRadius, mPaint);//有进度的线条颜色
	}

	class Node
	{
		Point mPoint;
		int type;
		String level;
		int score;
	}
}
