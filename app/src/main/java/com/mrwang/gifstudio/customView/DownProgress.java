package com.mrwang.gifstudio.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/30
 * Time: 下午7:02
 */
public class DownProgress extends View {
  private int progress;
  private int max;
  private Paint outSidePaint;
  private int height;
  private int width;
  private Paint textPaint;
  private RectF bgRect;
  private Paint middlePaint;
  private int bgSize;
  private String text;
  private int roundSize;
  private GradientDrawable drawable;
  private float[] floats;
  private int round;
  private float textBaseY;

  public DownProgress(Context context) {
    this(context, null);
  }

  public DownProgress(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DownProgress(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    outSidePaint = new Paint();
    middlePaint = new Paint();
    textPaint = new Paint();

    outSidePaint.setColor(Color.BLACK);

    textPaint.setColor(Color.GREEN);

    bgSize = dp2px(1);
    outSidePaint.setStrokeWidth(bgSize);
    outSidePaint.setStrokeJoin(Paint.Join.ROUND);
    outSidePaint.setStyle(Paint.Style.STROKE);

    middlePaint.setColor(Color.YELLOW);



    textPaint.setTextSize(sp2px(20));
    textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    textPaint.setTextAlign(Paint.Align.CENTER);

    bgRect = new RectF();

    int fillColor = Color.YELLOW;

    drawable = new GradientDrawable();
    drawable.setColor(fillColor);

  }

  public void setOutSideWidth(int width) {
    bgSize = width;
    outSidePaint.setStrokeWidth(width);
  }

  public void setOutSideColor(@ColorInt int color) {
    outSidePaint.setColor(color);
  }

  public void setTextSize(int size) {
    textPaint.setTextSize(sp2px(20));
  }

  public void setTextColor(@ColorInt int color) {
    textPaint.setColor(color);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.height = h;

    round = (height - 2 * bgSize) / 2;
    floats = new float[] {
        round, round, 0, 0, 0, 0, round, round
    };
    drawable.setCornerRadii(floats);
    bgRect.set(bgSize, bgSize, w - bgSize, h - bgSize);

    middlePaint.setStrokeWidth(h / 2);
    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
    // 计算文字高度
    float fontHeight = fontMetrics.bottom - fontMetrics.top;
    // 计算文字baseline
    textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
    super.onSizeChanged(w, h, oldw, oldh);
  }

  public void setProgress(int progress) {
    this.progress = progress;
    postInvalidate();
  }

  public void setMax(int max) {
    this.max = max;
  }

  public void setText(String text) {
    this.text = text;
  }

  private Bitmap roundBitmap;
  private Rect drawRect = new Rect();

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    canvas.drawRoundRect(bgRect, height / 2f, height / 2f, outSidePaint);

    if (roundBitmap == null) {
      createRoundBitmap();
    }
    float radio = progress * 1.0f / max;
    float progressPosX = (int) ((width - bgSize) * radio);
    Log.i("TAG", "width=" + width + "progressPosX=" + progressPosX);
    drawRect.set(0, 0, (int) progressPosX - bgSize, roundBitmap.getHeight());
    canvas.drawBitmap(roundBitmap, drawRect, drawRect, null);
    // canvas.drawBitmap(roundBitmap,0,0,null);
    canvas.drawText(text, width / 2, textBaseY, textPaint);
  }

  private void createRoundBitmap() {
    roundBitmap =
        Bitmap.createBitmap((int) bgRect.width(), (int) bgRect.height(), Bitmap.Config.ARGB_8888);
    Paint paint = new Paint();
    paint.setColor(Color.YELLOW);
    paint.setStyle(Paint.Style.FILL);
    Canvas canvas = new Canvas(roundBitmap);
    // canvas.drawColor(Color.YELLOW);
    canvas.drawRoundRect(bgRect, height / 2f - bgSize, height / 2f - bgSize, paint);
  }


  public int getRoundSize() {
    if (roundSize == 0) {
      roundSize = (int) (height / 2f + 0.5f);
    }
    return roundSize;
  }

  /**
   * dp 2 px
   *
   * @param dpVal
   */
  protected int dp2px(float dpVal) {
    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dpVal, getResources().getDisplayMetrics()) + 0.5f);
  }

  /**
   * sp 2 px
   *
   * @param spVal
   * @return
   */
  protected int sp2px(int spVal) {
    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        spVal, getResources().getDisplayMetrics()) + 0.5f);

  }
}
