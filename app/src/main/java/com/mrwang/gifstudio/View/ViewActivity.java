package com.mrwang.gifstudio.View;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/7
 * Time: 下午3:53
 */
public class ViewActivity extends AppCompatActivity {

  private LiveSelectView iv;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view);

    iv = (LiveSelectView) findViewById(R.id.iv);
  }

  @Override
  protected void onResume() {
    super.onResume();
    iv.postDelayed(new Runnable() {
      @Override
      public void run() {
        iv.setStartLocation(1080/2,1920/2);
        iv.startAnim();
      }
    },2000L);
  }

  public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
    final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
    DrawableCompat.setTintList(wrappedDrawable, colors);
    return wrappedDrawable;
  }
}
