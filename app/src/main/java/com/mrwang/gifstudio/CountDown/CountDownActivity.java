package com.mrwang.gifstudio.CountDown;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/18
 * Time: 下午10:18
 */
public class CountDownActivity extends AppCompatActivity {

  private CountDownView view;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.count_down);
    view = (CountDownView) findViewById(R.id.count_view);
  }

  @Override
  protected void onResume() {
    super.onResume();
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
    valueAnimator.setDuration(5000L);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float animatedFraction = animation.getAnimatedFraction();
        int i = (int) (animatedFraction * 360);
        System.out.println("animatedFraction=" + animatedFraction + " progress=" + i);
        view.setProgress(i);
      }
    });
    valueAnimator.start();
  }
}
