package com.mrwang.gifstudio.other;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 三阶贝塞尔曲线
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2016-05-13
 * Time: 13:55
 */
public class ThreeBezierEvaluator implements TypeEvaluator<PointF> {

  private final PointF P1;
  private final PointF P2;

  public ThreeBezierEvaluator(PointF middle1, PointF middle2) {
    this.P1 = middle1;
    this.P2 = middle2;
  }

  @Override
  public PointF evaluate(float fraction, PointF startValue,
      PointF endValue) {
    float oneMinusT = 1.0f - fraction;
    float x = (float) (startValue.x * Math.pow(oneMinusT, 3)
        + 3 * P1.x * fraction * Math.pow(oneMinusT, 2)
        + 3 * P2.x * Math.pow(fraction, 2) * oneMinusT + endValue.x * Math.pow(fraction, 3));
    float y = (float) (startValue.y * Math.pow(oneMinusT, 3)
        + 3 * P1.y * fraction * Math.pow(oneMinusT, 2)
        + 3 * P2.y * Math.pow(fraction, 2) * oneMinusT + endValue.y * Math.pow(fraction, 3));

    return new PointF(x, y);
  }
}
