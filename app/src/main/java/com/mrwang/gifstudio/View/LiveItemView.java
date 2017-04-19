package com.mrwang.gifstudio.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/10
 * Time: 下午4:59
 */
public class LiveItemView extends LinearLayout {
  private ImageView iv;
  private TextView tv;
  private float imageCenterX;
  private float imageCenterY;

  public LiveItemView(Context context) {
    super(context);
  }

  public LiveItemView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public LiveItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public LiveItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  {
    LayoutInflater inflater =
        (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.live_item, this, true);
    setOrientation(VERTICAL);
    setGravity(Gravity.CENTER);
    iv = (ImageView) findViewById(R.id.iv);
    tv = (TextView) findViewById(R.id.tv);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Log.i("TAG", "onSizeChanged w=" + w + " h=" + h);
  }

  public void setStart(int startX, int startY) {

  }

  public void setTextShow(boolean textShow) {
    tv.setVisibility(textShow ? VISIBLE : INVISIBLE);
    tv.setScaleX(0);
    tv.setScaleY(0);
  }

  public void setTextScale(float textScale) {
    tv.setScaleX(textScale);
    tv.setScaleY(textScale);
  }

  public void setScale(float fraction) {
    setScaleX(fraction);
    setScaleY(fraction);
  }

  public float getImageCenterX() {
    imageCenterX = iv.getWidth() / 2;
    return imageCenterX;
  }

  public float getImageCenterY() {
    imageCenterY = iv.getHeight() / 2;
    return imageCenterY;
  }
}
