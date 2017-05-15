package com.mrwang.gifstudio.CountDown;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/10
 * Time: 下午2:30
 */
public class ValueAnimImp implements AnimationInterface {

  private ValueAnimator animator;

  @Override
  public void createAnim(final View view) {
    animator = ValueAnimator.ofInt(0, 5000);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        view.setTranslationX((int) animation.getAnimatedValue());
        //view.setScaleX(animation.getAnimatedFraction());
      }

    });
    animator.setDuration(1000);
  }

  @Override
  public void startAnim() {
    animator.start();
  }

  @Override
  public void endAnim() {
    animator.end();
    animator.cancel();
  }
}
