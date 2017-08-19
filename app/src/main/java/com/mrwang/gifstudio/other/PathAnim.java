package com.mrwang.gifstudio.other;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/4
 * Time: 下午5:23
 */
public class PathAnim implements AnimationInterface {

  private int screenWidth = 1080;
  private int screenHeight = 1920;
  private final Point start;
  private final Point midPoint;
  private final Point endPoint;
  private float y;
  private int x;
  private Paint paint;

  public PathAnim(Context context) {
    start = new Point(0, screenHeight);
    midPoint = new Point(screenWidth / 2, screenHeight / 2);
    endPoint = new Point(screenWidth, screenHeight);
    paint=new Paint();
    paint.setStrokeWidth(10);
    paint.setColor(Color.YELLOW);
    paint.setStrokeCap(Paint.Cap.ROUND);

    y = getY(start, endPoint, midPoint, 0);
    Log.e("PathAnim", "y=" + y);
    ValueAnimator valueAnimator=ValueAnimator.ofInt(100,screenWidth);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        x = (int) animation.getAnimatedValue();
        y = getY(start, endPoint, midPoint, x) ;
        Log.e("PathAnim", "y=" + y);
      }
    });
    valueAnimator.setDuration(5000L);
    valueAnimator.start();
  }

  @Override
  public void present(Canvas canvas, float deltaTime) {
    canvas.drawPoint(x,y,paint);
  }

  @Override
  public void update(float deltaTime) {

  }

  @Override
  public void clearDrawingAnimation() {

  }

  @Override
  public void stopAndClear() {

  }

  /**
   * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
   * y = ax² + bx + c
   *
   * @param x x
   * @return Y
   */
  private float getY(Point startPoint, Point endPoint, Point midPoint, int x) {
    float x1 = startPoint.x;
    float y1 = startPoint.y;
    float x2 = endPoint.x;
    float y2 = endPoint.y;
    float x3 = midPoint.x;
    float y3 = midPoint.y;
    float a, b, c;
    a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2))
        / (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2));
    b = (y1 - y2) / (x1 - x2) - a * (x1 + x2);
    c = y1 - (x1 * x1) * a - x1 * b;
    System.out.println("-a->" + a + " b->" + b + " c->" + c);
    return a * x * x + b * x + c;
  }
}
