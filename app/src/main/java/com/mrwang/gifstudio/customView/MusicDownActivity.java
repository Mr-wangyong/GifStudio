package com.mrwang.gifstudio.customView;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/30
 * Time: 下午7:09
 */
public class MusicDownActivity extends AppCompatActivity {

  private DownProgress progress;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.music_down);
    progress = (DownProgress) findViewById(R.id.progress);
    progress.setMax(100);
    progress.setProgress(100);
    progress.setText("50%");

  }

  @Override
  protected void onResume() {
    super.onResume();
    final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
    valueAnimator.setDuration(5000);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        progress.setProgress(value);
      }
    });
    valueAnimator.start();
  }

  @Override
  protected void onStart() {
    super.onStart();
  }
}
