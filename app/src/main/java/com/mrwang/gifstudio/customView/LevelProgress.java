package com.mrwang.gifstudio.customView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;


public class LevelProgress extends ProgressBar {

  private static final int DEFAULT_TEXT_SIZE = 15;
  private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
  private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 20;
  private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
  private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

  /**
   * painter of all drawing things
   */
  protected Paint mPaint = new Paint();
  /**
   * color of progress number
   */
  protected int mTextColor = Color.GREEN;
  /**
   * size of text (sp)
   */
  protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);

  /**
   * offset of draw progress
   */
  protected int mTextOffset = dp2px(DEFAULT_SIZE_TEXT_OFFSET);

  /**
   * height of reached progress bar
   */
  protected float mReachedProgressBarHeight;

  /**
   * color of reached bar
   */
  protected int mReachedBarColor;
  /**
   * height of unreached progress bar
   */
  protected int mUnReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
  /**
   * view width except padding
   */
  protected int mRealWidth;

  protected boolean mIfDrawText = true;

  protected static final int VISIBLE = 0;
  private String text;
  private boolean canCanvasText = false;
  private int boundWidth;

  public LevelProgress(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LevelProgress(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
    obtainStyledAttributes(context, attrs, defStyle);
    mPaint.setTextSize(mTextSize);
    mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    mPaint.setTextAlign(Paint.Align.CENTER);
  }

  public void setTextColor(@ColorRes int color) {
    mTextColor = color;
  }

  public void setText(String text) {
    this.canCanvasText = true;
    this.text = text;
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec,
      int heightMeasureSpec) {

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }


  public void setBoundWidth(int boundWidth) {
    this.boundWidth = dp2px(boundWidth);
  }

  /**
   * get the styled attributes
   *
   * @param attrs
   */
  private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyle) {
    mReachedProgressBarHeight = 36;
    mReachedBarColor = Color.YELLOW;
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {

    canvas.save();
    canvas.translate(getPaddingLeft(), getHeight() / 2);

    float radio = getProgress() * 1.0f / getMax();
    float progressPosX = (int) (mRealWidth * radio);
    float endX = progressPosX - mTextOffset / 2;
    if (endX > 0) {
      mPaint.setColor(mReachedBarColor);
      mPaint.setStrokeJoin(Paint.Join.ROUND);
      mPaint.setStrokeCap(Paint.Cap.ROUND);
      mPaint.setStrokeWidth(mReachedProgressBarHeight);
      canvas.drawLine(boundWidth, 0, endX - boundWidth, 0, mPaint);
    }
    if (canCanvasText) {
      // 拿到字体的宽度和高度
      mPaint.setColor(mTextColor);
      float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
      canvas.drawText(text, mRealWidth / 2, -textHeight, mPaint);
    }
    canvas.restore();
  }

  /**
   * dp 2 px
   *
   * @param dpVal
   */
  protected int dp2px(int dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dpVal, getResources().getDisplayMetrics());
  }

  /**
   * sp 2 px
   *
   * @param spVal
   * @return
   */
  protected int sp2px(int spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        spVal, getResources().getDisplayMetrics());

  }

}
