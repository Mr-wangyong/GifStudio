package com.mrwang.gifstudio.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/10
 * Time: 下午4:58
 */

public class LiveSelectView extends FrameLayout {

  private LiveItemView cell1;
  private LiveItemView cell2;
  private int startX;
  private int startY;

  public LiveSelectView(Context context) {
    this(context, null);
  }

  public LiveSelectView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LiveSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public LiveSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    cell1 = new LiveItemView(getContext());
    cell2 = new LiveItemView(getContext());
  }

  public void setStartLocation(int startX, int startY) {
    this.startX = startX;
    this.startY = startY;

    cell1.setStart(startX, startY);

    if (getChildCount() > 0) {
      removeAllViews();
    }
    setClipChildren(false);
    FrameLayout.LayoutParams params1 =
        new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params1.leftMargin = startX;
    params1.topMargin = startY;

    FrameLayout.LayoutParams params2 =
        new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params2.leftMargin = startX;
    params2.topMargin = startY;
    addView(cell1, params1);
    addView(cell2, params2);

    cell1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("TAG", "cell1 click");
      }
    });

    cell2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("TAG", "cell1 click");
      }
    });
  }


  public void startAnim() {
    if (startX != 0 || startY != 0) {
      ValueAnimator imageAnim = ValueAnimator.ofInt(1, 100);
      imageAnim.setDuration(3000L);
      imageAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          float animatedFraction = animation.getAnimatedFraction();
          float x = -animatedFraction * 300;
          float y = -animatedFraction * 300;
          cell1.setPivotX(cell1.getImageCenterX());
          cell2.setPivotX(cell2.getImageCenterX());
          cell1.setPivotY(cell1.getImageCenterY());
          cell2.setPivotY(cell2.getImageCenterY());
          cell1.setRotation(360 * (1 - animatedFraction));
          cell2.setRotation(-360 * (1 - animatedFraction));
          cell1.setTranslationX(x);
          cell1.setTranslationY(y);
          cell1.setScale(animatedFraction);

          cell2.setTranslationX(-x);
          cell2.setTranslationY(y);

          cell2.setScale(animatedFraction);
        }
      });
      imageAnim.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          Log.i("TAG", "onAnimationEnd");
          cell1.setTextShow(true);
          cell2.setTextShow(true);
        }

        @Override
        public void onAnimationStart(Animator animation) {
          Log.i("TAG", "onAnimationStart");
          cell1.setTextShow(false);
          cell2.setTextShow(false);
        }
      });

      ValueAnimator textAnim = ValueAnimator.ofInt(1, 100);
      textAnim.setInterpolator(new OvershootInterpolator());
      textAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          Log.i("TAG", "textAnim onAnimationUpdate");
          cell1.setTextScale(animation.getAnimatedFraction());
          cell2.setTextScale(animation.getAnimatedFraction());
        }
      });
      textAnim.setDuration(500);
      textAnim.setStartDelay(100);

      AnimatorSet set = new AnimatorSet();
      set.playSequentially(imageAnim, textAnim);
      set.start();

    }
  }
}
