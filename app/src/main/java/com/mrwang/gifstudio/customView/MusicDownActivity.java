package com.mrwang.gifstudio.customView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mrwang.gifstudio.R;
import com.mrwang.gifstudio.customView.FireWorks.FireworkLayout;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/30
 * Time: 下午7:09
 */
public class MusicDownActivity extends AppCompatActivity {

  private DownProgress2 progress;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.music_down);
    // progress = (DownProgress2) findViewById(R.id.progress);
    // progress.setMax(100);
    // progress.setProgress(100);
    // progress.setText("50%");
    // SystemClock.sleep(1000);

    LinearLayout content = (LinearLayout) findViewById(R.id.content);
    FireworkLayout fireworkLayout = new FireworkLayout(getApplicationContext());
    content.addView(fireworkLayout, new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
  }

  // @Override
  // protected void onResume() {
  // super.onResume();
  // final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
  // valueAnimator.setDuration(5000);
  // valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
  // @Override
  // public void onAnimationUpdate(ValueAnimator animation) {
  // int value = (int) animation.getAnimatedValue();
  // progress.setProgress(value);
  // }
  // });
  // valueAnimator.start();
  // }
  //
  // @Override
  // protected void onStart() {
  // super.onStart();
  // }
  //
  // @Override
  // public void finish() {
  // super.finish();
  // overridePendingTransition(R.anim.slide_in_exit_left, R.anim.slide_out_right);
  // }
}
