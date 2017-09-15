package com.mrwang.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/19
 * Time: 下午5:07
 */
public class ShapeView extends View {

  public ShapeView(Context context) {
    this(context, null);
  }

  public ShapeView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView,
        defStyle, 0);

    int shape = typedArray.getInt(R.styleable.ShapeView_shape, 0);

    float cornersRadius = typedArray.getDimension(R.styleable.ShapeView_cornersRadius, 0);
    float cornersTopLeftRadius =
        typedArray.getDimension(R.styleable.ShapeView_cornersTopLeftRadius, 0);
    float cornersTopRightRadius =
        typedArray.getDimension(R.styleable.ShapeView_cornersTopRightRadius, 0);
    float cornersBottomLeftRadius =
        typedArray.getDimension(R.styleable.ShapeView_cornersBottomLeftRadius, 0);
    float cornersBottomRightRadius =
        typedArray.getDimension(R.styleable.ShapeView_cornersBottomRightRadius, 0);

    float strokeWidth = typedArray.getDimension(R.styleable.ShapeView_strokeWidth, 0);
    int strokeColor = typedArray.getColor(R.styleable.ShapeView_strokeColor, 0);
    float strokeDashGap = typedArray.getDimension(R.styleable.ShapeView_strokeDashGap, 0);
    float strokeDashWidth = typedArray.getDimension(R.styleable.ShapeView_strokeDashWidth, 0);


    int gradientType = typedArray.getInt(R.styleable.ShapeView_gradientType, -1);

    int gradientStartColor = typedArray.getColor(R.styleable.ShapeView_gradientStartColor, 0);
    int gradientEndColor = typedArray.getColor(R.styleable.ShapeView_gradientEndColor, 0);
    int gradientCenterColor = typedArray.getColor(R.styleable.ShapeView_gradientCenterColor, 0);
    int gradientAngle = typedArray.getInt(R.styleable.ShapeView_gradientAngle, 0);
    float gradientCenterX = typedArray.getFloat(R.styleable.ShapeView_gradientCenterX, 0);
    float gradientCenterY = typedArray.getFloat(R.styleable.ShapeView_gradientCenterY, 0);
    float gradientRadius = typedArray.getDimension(R.styleable.ShapeView_gradientRadius, 0);
    boolean useLevel = typedArray.getBoolean(R.styleable.ShapeView_gradientUseLevel, false);

    int solidColor = typedArray.getColor(R.styleable.ShapeView_solidColor, 0);
    typedArray.recycle();

    GradientDrawable drawable;
    if (hasValue(gradientStartColor)
        && hasValue(gradientStartColor)) {
      drawable = new GradientDrawable(getAlgle(gradientAngle),
          new int[] {gradientStartColor, gradientCenterColor, gradientEndColor});
    } else {
      drawable = new GradientDrawable();
    }

    if (hasValue(gradientType)) {
      setGradientType(drawable, gradientType);
      if (hasValue(gradientCenterX)
          || hasValue(gradientCenterY)) {
        drawable.setGradientCenter(gradientCenterX, gradientCenterY);
      }
      if (hasValue(gradientEndColor)) {
        drawable.setGradientRadius(gradientRadius);
      }
      drawable.setUseLevel(useLevel);
    }

    setShape(drawable, shape);
    if (hasValue(cornersRadius)) {
      drawable.setCornerRadius(cornersRadius);
    } else if (hasValue(cornersTopLeftRadius)
        || hasValue(cornersTopRightRadius)
        || hasValue(cornersBottomLeftRadius)
        || hasValue(cornersBottomRightRadius)) {
      drawable.setCornerRadii(new float[] {0, 0, 0, 0, cornersTopLeftRadius, cornersTopRightRadius,
          cornersBottomLeftRadius,
          cornersBottomRightRadius});
    }

    if (hasValue(strokeWidth)) {
      if (hasValue(strokeDashGap)
          || hasValue(strokeDashWidth)) {
        drawable.setStroke(Math.round(strokeWidth), strokeColor, strokeDashWidth, strokeDashGap);
      } else {
        drawable.setStroke(Math.round(strokeWidth), strokeColor);
      }
    }

    if (hasValue(solidColor)) {
      drawable.setColor(solidColor);
    }
    ViewCompat.setBackground(this, drawable);
  }


  private boolean hasValue(float value) {
    return value > 0;
  }

  public void setShape(GradientDrawable drawable, int shape) {
    switch (shape) {
      case GradientDrawable.RECTANGLE:
        drawable.setShape(GradientDrawable.RECTANGLE);
        break;
      case GradientDrawable.OVAL:
        drawable.setShape(GradientDrawable.OVAL);
        break;
      case GradientDrawable.LINE:
        drawable.setShape(GradientDrawable.LINE);
        break;
      case GradientDrawable.RING:
        drawable.setShape(GradientDrawable.RING);
        break;
    }
  }

  public void setGradientType(GradientDrawable drawable, int gradientType) {
    switch (gradientType) {
      case GradientDrawable.LINEAR_GRADIENT:
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        break;
      case GradientDrawable.RADIAL_GRADIENT:
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        break;
      case GradientDrawable.SWEEP_GRADIENT:
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        break;
    }
  }

  public GradientDrawable.Orientation getAlgle(int angle) {
    switch (angle) {
      case 0:
        return GradientDrawable.Orientation.LEFT_RIGHT;
      case 45:
        return GradientDrawable.Orientation.BL_TR;
      case 90:
        return GradientDrawable.Orientation.BOTTOM_TOP;
      case 135:
        return GradientDrawable.Orientation.BR_TL;
      case 180:
        return GradientDrawable.Orientation.RIGHT_LEFT;
      case 225:
        return GradientDrawable.Orientation.TR_BL;
      case 270:
        return GradientDrawable.Orientation.TOP_BOTTOM;
      case 315:
        return GradientDrawable.Orientation.TL_BR;
    }
    return GradientDrawable.Orientation.TOP_BOTTOM;
  }

}
