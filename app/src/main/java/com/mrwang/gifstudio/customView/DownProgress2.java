package com.mrwang.gifstudio.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/30
 * Time: 下午7:02
 */
public class DownProgress2 extends View {
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
  private int middleWidth;

  public DownProgress2(Context context) {
    this(context, null);
  }

  public DownProgress2(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DownProgress2(Context context, AttributeSet attrs, int defStyleAttr) {
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

    round = getRoundSize();
    floats = new float[] {
        round, round, 0, 0, 0, 0, round, round
    };
    drawable.setCornerRadii(floats);
    bgRect.set(bgSize, bgSize, w - bgSize, h - bgSize);
    middleWidth = (height - 2 * bgSize) / 2;
    middlePaint.setStrokeWidth(middleWidth);
    middlePaint.setStrokeCap(Paint.Cap.ROUND);
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

  @Override
  protected synchronized void onDraw(Canvas canvas) {

    //
    canvas.drawRoundRect(bgRect, height / 2f, height / 2f, outSidePaint);
    // if (progress < 5) {
    // progress = 5;
    // }
    float radio = progress * 1.0f / max;
    float progressPosX = (int) (width * radio);
    // if (progress > 95) {
    // floats[2] = round;
    // floats[3] = round;
    // floats[4] = round;
    // floats[5] = round;
    // } else {
    // floats[2] = 0;
    // floats[3] = 0;
    // floats[4] = 0;
    // floats[5] = 0;
    // }
    // drawable.setBounds(bgSize * 2, bgSize + dp2px(0.5f), (int) progressPosX - bgSize,
    // height - bgSize *
    // 2);
    // drawable.draw(canvas);
    if (progressPosX < 5) {
      middlePaint.setStrokeWidth(middleWidth*(progressPosX/5f));
    }else{
      middlePaint.setStrokeWidth(middleWidth);
    }
    canvas.drawLine(middlePaint.getStrokeWidth(),height/2,progressPosX,height/2,middlePaint);
    canvas.drawText(text, width / 2, textBaseY, textPaint);
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
