package com.mrwang.gifstudio.other;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 二阶贝塞尔曲线
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2016-05-13
 * Time: 20:37
 */
public class TwoBezierEvaluator implements TypeEvaluator<PointF> {

  private final PointF middlePointF;

  public TwoBezierEvaluator(PointF middlePointF) {
    this.middlePointF = middlePointF;

  }

  @Override
  public PointF evaluate(float fraction, PointF startValue,
      PointF endValue) {
    float oneMinusT = 1.0f - fraction;
    PointF P1 = middlePointF;

    float x = (float) (startValue.x * Math.pow(oneMinusT, 2) + 2 * fraction * oneMinusT * P1.x
        + Math.pow(fraction, 2) * endValue.x);
    float y = (float) (startValue.y * Math.pow(oneMinusT, 2) + 2 * fraction * oneMinusT * P1.y
        + Math.pow(fraction, 2) * endValue.y);

    return new PointF(x, y);
  }
}
