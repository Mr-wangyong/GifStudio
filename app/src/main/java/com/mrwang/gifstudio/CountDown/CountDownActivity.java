package com.mrwang.gifstudio.CountDown;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/18
 * Time: 下午10:18
 */
public class CountDownActivity extends AppCompatActivity {

  private CountDownView view;
  private ValueAnimator valueAnimator;
  private boolean isClick = true;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.count_down);
    // view = (CountDownView) findViewById(R.id.count_view);
    // view.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    // view.isRect(!view.isRect);
    // valueAnimator.reverse();
    // valueAnimator.start();
    // }
    // });
    // view.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    // valueAnimator.cancel();
    // }
    // });
    // SparseArray<String> sparseArray=new SparseArray<>();
    // sparseArray.put(0,"100");
    // sparseArray.put(10,"200");
    // sparseArray.put(30,"300");
    // sparseArray.put(20,"400");
    //
    // for (int i = 0; i < sparseArray.size(); i++) {
    // int key = sparseArray.keyAt(i);
    // if (key==10){
    // sparseArray.removeAt(i);
    // //sparseArray.remove(key);
    // }
    // }
    // System.out.println("sparseArray="+sparseArray.toString());

//    final TestView testView = (TestView) findViewById(R.id.test_view);
//
//    testView.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        testView.setAnim(isClick ? new ValueAnimImp() : new FrameAnimImp());
//        Log.i("TAG", "testView Run");
//        isClick = !isClick;
//        testView.startAnim();
//      }
//    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.i("TAG", "testView onResume");
    // valueAnimator = ValueAnimator.ofInt(0, 100);
    // valueAnimator.setDuration(10000L);
    // valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    // @Override
    // public void onAnimationUpdate(ValueAnimator animation) {
    // float animatedFraction = animation.getAnimatedFraction();
    // //System.out.println("animatedFraction=" + animatedFraction + " progress=" + i);
    // view.setProgress(animatedFraction);
    // }
    // });
    // valueAnimator.addListener(new AnimatorListenerAdapter() {
    // @Override
    // public void onAnimationCancel(Animator animation) {
    // super.onAnimationCancel(animation);
    // }
    //
    // @Override
    // public void onAnimationEnd(Animator animation) {
    // System.out.println("onAnimationEnd=");
    // super.onAnimationEnd(animation);
    // }
    // });
    // valueAnimator.start();
  }
}
