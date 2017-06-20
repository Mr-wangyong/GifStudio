package com.mrwang.gifstudio.TexasProgress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/15
 * Time: 上午11:54
 */
public class TexasProgressActivity extends AppCompatActivity {

  private TexasProgress progress;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.texas_progress);
    progress = (TexasProgress) findViewById(R.id.progress);
    progress.setMax(100000);
    progress.setStart(1000);
//    progress.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,1);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//          @Override
//          public void onAnimationUpdate(ValueAnimator animation) {
//            float animatedFraction = animation.getAnimatedFraction();
//            progress.setProgress((int) (animatedFraction*100));
//          }
//        });
//
//        valueAnimator.setDuration(5000);
//        valueAnimator.start();
//      }
//    });
  }
}
