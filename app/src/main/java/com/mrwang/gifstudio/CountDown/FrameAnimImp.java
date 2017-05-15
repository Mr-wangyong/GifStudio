package com.mrwang.gifstudio.CountDown;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/10
 * Time: 下午2:44
 */
public class FrameAnimImp implements AnimationInterface {

  private TranslateAnimation animation;
  private View view;

  @Override
  public void createAnim(View view) {
    this.view = view;
    animation = new TranslateAnimation(0, 5000, 0, 0);
    animation.setDuration(1000);
  }

  @Override
  public void startAnim() {
    view.setAnimation(animation);
    view.startAnimation(animation);
  }

  @Override
  public void endAnim() {
    view.clearAnimation();
    animation.cancel();
  }
}
