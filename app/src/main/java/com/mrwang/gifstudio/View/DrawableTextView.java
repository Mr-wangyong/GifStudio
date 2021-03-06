package com.mrwang.gifstudio.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/24
 * Time: 下午5:36
 */
public class DrawableTextView extends AppCompatTextView {

  public static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

  public DrawableTextView(Context context) {
    this(context, null);
  }

  public DrawableTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    if (attrs != null) {
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
      int mWidth = a
          .getDimensionPixelSize(R.styleable.DrawableTextView_drawable_width, 0);
      int mHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_drawable_height,
          0);
      Drawable mDrawable = a.getDrawable(R.styleable.DrawableTextView_drawable_src);
      int mLocation = a.getInt(R.styleable.DrawableTextView_drawable_location, LEFT);
      a.recycle();
      drawDrawable(mDrawable, mWidth, mHeight, mLocation);
    }
  }

  /**
   * 绘制Drawable宽高,位置
   */
  public void drawDrawable(Drawable mDrawable, int mWidth, int mHeight, int mLocation) {
    if (mDrawable != null) {
      if (mWidth != 0 && mHeight != 0) {
        mDrawable.setBounds(0, 0, mWidth, mHeight);
      }
      switch (mLocation) {
        case LEFT:
          this.setCompoundDrawables(mDrawable, null,
              null, null);
          break;
        case TOP:
          this.setCompoundDrawables(null, mDrawable,
              null, null);
          break;
        case RIGHT:
          this.setCompoundDrawables(null, null,
              mDrawable, null);
          break;
        case BOTTOM:
          this.setCompoundDrawables(null, null, null,
              mDrawable);
          break;
      }
    }
  }
}
