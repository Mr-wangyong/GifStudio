package com.mrwang.gifstudio;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 礼物送经验动画
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/27
 * Time: 下午2:08
 */
public class ExpAnim implements AnimationInterface {
  public final int startColor;
  public final int endColor;
  private int screenWidth = 1080;
  private int screenHeight = 1920;
  private Random random;
  private List<Sun> suns;
  private float stateTime;
  private Paint clipPaint;
  private int maxSize;
  private static final int START = 1;
  private static final int ONE = 2;
  private static final int TWO = 3;
  private static final int THREE = 4;
  private static final int FORE = 5;
  private static final int END = 0;
  private int state = END;
  private Bitmap plusExpBitmap;
  private int expX;
  private int expY;



  public ExpAnim(int startColor, int endColor) {
    this.startColor = startColor;
    this.endColor = endColor;
    init();
  }

  private void init() {
    random = new Random();
    suns = new LinkedList<>();
    maxSize = screenWidth / 8;
    state = START;
    initState(state);
    clipPaint = new Paint();
    clipPaint.setAntiAlias(true);
    clipPaint.setStyle(Paint.Style.STROKE);
  }

  private boolean isRunning = true;

  @Override
  public void present(Canvas canvas, float deltaTime) {
    clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    canvas.drawPaint(clipPaint);
    if (!suns.isEmpty()) {
      for (Sun sun : suns) {
        sun.draw(canvas);
      }
    }
  }

  @Override
  public void update(float deltaTime) {
    if (!suns.isEmpty() && state != END) {
      stateTime = stateTime + 0.02f;
      if (stateTime > 0.0f && stateTime < 5.0f) {
        switch (state) {
          case START:
            state = ONE;
            break;
          case ONE:
            if (stateTime > 0.5f) {
              initState(state);
              state = TWO;
            }
            break;
          case TWO:
            if (stateTime > 1.0f) {
              initState(state);
              state = THREE;
            }
            break;
          case THREE:
            if (stateTime > 1.5f) {
              initState(state);
              state = FORE;
            }
            break;
          case FORE:
            if (stateTime % 2.0f < 0.1f) {
              initState(state);
              state = THREE;
            }
            break;
        }
        float speed = stateTime / 5.0f;
        Iterator<Sun> iterator = suns.iterator();
        while (iterator.hasNext()) {
          Sun sun = iterator.next();
          sun.update(speed);
          if (sun.y <= -maxSize && sun.x <= -maxSize) {
            iterator.remove();
          }
        }
      } else {
        suns.clear();
        state = END;
      }
    } else {
      stateTime = 0;
      state = END;
    }
  }

  private void initState(int state) {
    PointF[] pointFs = new PointF[4];
    ThreeBezierEvaluator oneEvaluator;
    switch (state) {
      case START:
        // 绘制第一个点
        pointFs[0] = new PointF(getWidthValue(7 / 16f), getHeightValue(1 / 2f));
        pointFs[1] =
            new PointF(getWidthValue(6 / 16f), getHeightValue(1 / 2f - 1 / 32f));
        pointFs[2] =
            new PointF(getWidthValue(3 / 16f),
                getHeightValue(1 / 2f - 6 / 32f));

        pointFs[3] =
            new PointF(-maxSize, getHeightValue(3 / 8f));
        oneEvaluator = new ThreeBezierEvaluator(pointFs[1], pointFs[2]);
        suns.add(new Sun(startColor, endColor, pointFs, oneEvaluator));
        break;
      case TWO:
        pointFs[0] = new PointF(getWidthValue(1 / 4f + 1 / 8f), getHeightValue(1 / 2f - 1 / 8f));
        pointFs[1] =
            new PointF(getWidthValue(1 / 4f + 5 / 32f), getHeightValue(1 / 2f - 5 / 32f));
        pointFs[2] =
            new PointF(getWidthValue(1 / 4f + 6 / 32f),
                getHeightValue(1 / 2f - 6 / 32f));

        pointFs[3] =
            new PointF(getWidthValue(1 / 2f), getHeightValue(1 / 4f));
        oneEvaluator = new ThreeBezierEvaluator(pointFs[1], pointFs[2]);
        suns.add(new Sun(startColor, endColor, pointFs, oneEvaluator));
        break;
      case THREE:
        // 绘制第二个点
        pointFs[0] = new PointF(getWidthValue(1 / 4f) - 100 + random.nextInt(100),
            getHeightValue(1 / 4f) - 100 + random.nextInt(100));
        pointFs[1] =
            new PointF(pointFs[0].x - random.nextInt((int) getWidthValue(1 / 8f)),
                pointFs[0].y + random.nextInt((int) getWidthValue(1 / 8f)));
        pointFs[2] =
            new PointF(pointFs[1].x - random.nextInt((int) getWidthValue(1 / 8f)),
                pointFs[1].y + random.nextInt((int) getWidthValue(1 / 8f)));
        pointFs[3] =
            new PointF(random.nextInt((int) getWidthValue(1 / 4f)),
                -maxSize - random.nextInt(100));
        oneEvaluator = new ThreeBezierEvaluator(pointFs[1], pointFs[2]);
        suns.add(new Sun(startColor, endColor, pointFs, oneEvaluator));
        break;
    }
  }

  public float getWidthValue(float value) {
    return screenWidth * value;
  }

  public float getHeightValue(float value) {
    return screenHeight * value;
  }

  @Override
  public void clearDrawingAnimation() {

  }

  @Override
  public void stopAndClear() {

  }

  public class Sun {
    private final int startColor;
    private final int endColor;
    private final PointF[] pointFs;
    private final ThreeBezierEvaluator oneEvaluator;
    private Paint sunPoint;
    private int x;
    private int y;
    private int size;
    private RadialGradient radialGradient;

    public Sun(int startColor, int endColor, PointF[] pointFs, ThreeBezierEvaluator oneEvaluator) {
      this.startColor = startColor;
      this.endColor = endColor;
      this.pointFs = pointFs;
      this.oneEvaluator = oneEvaluator;
      sunPoint = new Paint();
    }

    public void setSize(int size) {
      this.size = size;
      float radius = size / 2;
      radialGradient = new RadialGradient(x, y, radius, startColor,
          endColor, Shader.TileMode.CLAMP);
      sunPoint.setShader(radialGradient);
    }

    public void draw(Canvas canvas) {
      canvas.drawRect(x - size / 2, y - size / 2, x + size / 2, y + size / 2, sunPoint);
    }

    public void update(float speed) {
      PointF evaluate = oneEvaluator.evaluate(speed, pointFs[0], pointFs[3]);
      x = (int) (evaluate.x + 0.5f);
      y = (int) (evaluate.y + 0.5f);
      setSize((int) (speed * maxSize));
    }
  }
}
