package com.mrwang.gifstudio.CountDown;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/8
 * Time: 下午2:51
 */
public class BullAnim {
  private static AnimatorSet set;

  private static void startBullAnim(final ImageView bullPokerBg, final ImageView bullPokerView) {
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
    valueAnimator.setDuration(500);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        bullPokerBg.setAlpha(value / 100f);
        bullPokerBg.setTranslationX(value - 100);
      }
    });

    ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0.0f, 1.5f, 2.0f, 1.5f, 1.0f);
    valueAnimator2.setDuration(1000);
    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        if (value <= 1.0f) {
          bullPokerView.setAlpha(value);
        }
        bullPokerView.setScaleX(value);
        bullPokerView.setScaleY(value);
      }
    });
    set = new AnimatorSet();
    set.playSequentially(valueAnimator, valueAnimator2);
    set.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation) {
        bullPokerBg.setVisibility(View.VISIBLE);
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
      }
    });
    set.start();
  }

  private static void startBullAnimInFrame(final ImageView bullPokerBg,
      final ImageView bullPokerView) {

    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
    valueAnimator.setDuration(500);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        bullPokerBg.setAlpha(value / 100f);
        bullPokerBg.setTranslationX(value - 100);
      }
    });

    AnimationSet bgSet = new AnimationSet(true);

    TranslateAnimation translateAnimation = new TranslateAnimation(-100, 0, 0, 0);

    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);

    bgSet.addAnimation(translateAnimation);
    bgSet.addAnimation(alphaAnimation);
    bgSet.setFillAfter(true);
    bgSet.setDuration(500);




    ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 2.0f, 0.0f, 2.0f);
    scaleAnimation.setInterpolator(new OvershootInterpolator());


    ScaleAnimation scaleAnimation2 = new ScaleAnimation(2.0f, 0.0f, 2.0f, 0.0f);
    scaleAnimation.setInterpolator(new OvershootInterpolator());

    ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0.0f, 1.5f, 2.0f, 1.5f, 1.0f);
    valueAnimator2.setDuration(1000);
    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        if (value <= 1.0f) {
          bullPokerView.setAlpha(value);
        }
        bullPokerView.setScaleX(value);
        bullPokerView.setScaleY(value);
      }
    });



    // set = new AnimatorSet();
    // set.playSequentially(valueAnimator, valueAnimator2);
    // set.addListener(new AnimatorListenerAdapter() {
    // @Override
    // public void onAnimationStart(Animator animation) {
    // bullPokerBg.setVisibility(View.VISIBLE);
    // }
    //
    // @Override
    // public void onAnimationEnd(Animator animation) {
    // super.onAnimationEnd(animation);
    // }
    // });
    // set.start();
  }
}
