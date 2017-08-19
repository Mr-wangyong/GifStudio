package com.mrwang.gifstudio.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/2
 * Time: 下午6:43
 */
public class CustomView2 extends View {

  private BitmapDrawable bitmapDrawable;

  public CustomView2(Context context) {
    super(context);
    init();
  }

  public CustomView2(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.count_down);
    bitmapDrawable = new BitmapDrawable(getResources(),
      bitmap);
    bitmapDrawable.setBounds(50,10,bitmap.getWidth(),bitmap.getHeight());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    bitmapDrawable.draw(canvas);

  }
}
