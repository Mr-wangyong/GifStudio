package com.mrwang.gifstudio.ViewFlip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.mrwang.gifstudio.R;

import java.util.List;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/20
 * Time: 下午6:03
 */
public class UPMarqueeView extends ViewFlipper {

  private Context mContext;
  private boolean isSetAnimDuration = false;
  private int interval = 2000;
  private Animation animIn;
  private Animation animOut;


  public UPMarqueeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    this.mContext = context;
    //setFlipInterval(interval);
    animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
    setInAnimation(animIn);
    animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
    setOutAnimation(animOut);
    //setAutoStart(false);
  }





  public void setInterval(int interval) {
    this.interval = interval;
    setFlipInterval(interval);
  }


  public void stop(){
    stopFlipping();
  }

  /**
   * 设置循环滚动的View数组
   *
   * @param views
   */
  public void setViews(List<View> views) {
    if (views == null || views.size() == 0) return;
    removeAllViews();
    for (int i = 0; i < views.size(); i++) {
      addView(views.get(i));
    }
    startFlipping();
  }

  @Override
  protected void onDetachedFromWindow() {
    stop();
    super.onDetachedFromWindow();
  }

  
}
