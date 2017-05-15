package com.mrwang.gifstudio.CountDown;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/6
 * Time: 上午11:23
 */
public class TestView extends android.support.v7.widget.AppCompatTextView {
  private static final String TAG = "TestView";
  private AnimationInterface anim;

  public TestView(Context context) {
    super(context);
  }

  public TestView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setAnim(AnimationInterface anim) {
    this.anim = anim;
    anim.createAnim(this);
  }

  public void startAnim() {
    anim.startAnim();
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw
        + "], oldh = [" + oldh + "]");
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec
        + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    Log.d(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = ["
        + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    super.onLayout(changed, left, top, right, bottom);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
    super.onDraw(canvas);
  }
}
