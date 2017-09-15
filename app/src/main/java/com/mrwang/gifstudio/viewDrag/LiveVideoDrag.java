package com.mrwang.gifstudio.viewDrag;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/21
 * Time: 下午2:01
 */
public class LiveVideoDrag extends FrameLayout {
  private int height=1920;
  private int width=1080;
  private boolean isDrag;

  public LiveVideoDrag(Context context) {
    this(context, null);
  }

  public LiveVideoDrag(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LiveVideoDrag(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private ViewDragHelper mDragger;

  private View mDragView;

  private int bottomViewHeight = 1920 - 500;


  public void init() {
    mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {



      @Override
      public boolean tryCaptureView(View child, int pointerId) {
        isDrag = child == mDragView;
        return isDrag;
      }

      @Override
      public int clampViewPositionHorizontal(View child, int left, int dx) {
        int leftBound = getPaddingLeft();
        int rightBound = width - getPaddingRight() - child.getWidth();
        left = Math.min(Math.max(left, leftBound), rightBound);
        return left;
      }

      @Override
      public int clampViewPositionVertical(View child, int top, int dy) {
        int topBound = getPaddingTop();
        int bottomBound = height - getPaddingBottom() - child.getHeight();
        top = Math.min(Math.max(top, topBound), bottomBound);
        return top;
      }


      // 手指释放的时候回调
      @Override
      public void onViewReleased(View releasedChild, float xvel, float yvel) {
        Log.i("TAG", "onViewReleased xvel=" + xvel + " yvel=" + yvel);
        if (releasedChild == mDragView && releasedChild.getBottom() > bottomViewHeight) {
          mDragger.settleCapturedViewAt(mDragView.getLeft(),
            bottomViewHeight - mDragView.getHeight());
          invalidate();
        }
      }
    });
  }


  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return mDragger.shouldInterceptTouchEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    mDragger.processTouchEvent(event);
    return isDrag;
  }

  @Override
  public void computeScroll() {
    if (mDragger.continueSettling(true)) {
      invalidate();
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    mDragView = getChildAt(0);
    // mAutoBackView = getChildAt(1);
    // mEdgeTrackerView = getChildAt(2);
  }
}
