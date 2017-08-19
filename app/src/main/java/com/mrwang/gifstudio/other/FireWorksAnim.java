package com.mrwang.gifstudio.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import com.mrwang.gifstudio.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 烟花动画
 * <p>
 * <p>
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/4
 * Time: 下午4:04
 */
public class FireWorksAnim implements AnimationInterface {

  private Bitmap fireWorks;
  private float scale = 0.2f;
  private int screenWidth = 1080;
  private int screenHeight = 1920;
  private List<Fire> fires;
  private int pointNum = 1;

  FireWorksAnim(Context applicationContext) {
    init(applicationContext);
  }

  public void init(Context context) {

    // ValueAnimator animator = ValueAnimator.ofInt(1, 10);
    // animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    // @Override
    // public void onAnimationUpdate(ValueAnimator animation) {
    // scale = animation.getAnimatedFraction()+0.2f;
    // }
    // });
    // animator.setDuration(1000L);


    int x = screenWidth / 2;

    fires = new ArrayList<>();
    Random random = new Random();

    fireWorks = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireworks);
    for (int i = 0; i < pointNum; i++) {
      Fire fire = new Fire();
      if (i > pointNum / 2) {
        fire.num = -5;
      }
      fire.bitmapDrawable = new BitmapDrawable(context.getResources(), fireWorks);
      int r = x - 50 + random.nextInt(100);
      float pointY = screenHeight - createPoint(fire, r - 300, r + 600, r);
      fire.x = r;
      fire.y = pointY;
      fire.freshBound();
      fires.add(fire);
    }

    // animator.start();

  }

  private float createPoint(Fire fire, int startX, int endX, int x) {
    fire.startPoint = new Point(startX, screenHeight+startX);
    fire.endPoint = new Point(endX, screenHeight+startX);
    fire.midPoint = new Point((endX - startX) / 2, screenHeight / 2);
    return getY(fire.startPoint, fire.endPoint, fire.midPoint, x);
  }

  @Override
  public void present(Canvas canvas, float deltaTime) {
    if (fires != null) {
      for (Fire fire : fires) {
        canvas.save();
        canvas.translate(fire.x, fire.y);
        fire.bitmapDrawable.draw(canvas);
        canvas.restore();
      }
    }
  }

  @Override
  public void update(float deltaTime) {
    if (fires != null) {
      for (Fire fire : fires) {
        fire.freshY();
      }
    }
  }

  @Override
  public void clearDrawingAnimation() {

  }

  @Override
  public void stopAndClear() {

  }

  private class Fire {
    float x;
    float y;
    BitmapDrawable bitmapDrawable;
    Point endPoint;
    Point startPoint;
    Point midPoint;
    int num = 2;
    private float stateTime;
    private int width;
    private int height;

    void freshBound() {
      if (width == 0) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
      }
      bitmapDrawable.setBounds(0, 0, (int) (width * scale), (int) (height * scale));
    }

    void freshY() {
      stateTime += 0.05f;
      if (stateTime > 1.0f) {
        x += num;
        scale += 0.01f;
        y = getY(startPoint, endPoint, midPoint, x);
        freshBound();
      }
      // if (stateTime > 1.0f) {
      // scale += 0.1f;
      // stateTime = 0;
      // x += num;
      // y = getY(startPoint, endPoint, midPoint, x);
      // freshBound();
      // }
    }
  }

  /**
   * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
   * y = ax² + bx + c
   *
   * @param x x
   * @return Y
   */
  private float getY(Point startPoint, Point endPoint, Point midPoint, float x) {
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
