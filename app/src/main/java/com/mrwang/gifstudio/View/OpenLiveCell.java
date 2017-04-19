package com.mrwang.gifstudio.View;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.util.Log;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/10
 * Time: 上午11:03
 */
public class OpenLiveCell {

  private BitmapDrawable liveBitmap;
  private RectF imageRoundRect;
  private Paint paint;
  private int imageRoundSize = 100;
  private RectF textRoundRect;
  private int textRoundSize;
  private float textCenterX;
  private float textCenterY;
  private float scale = 1.0f;
  private Paint textPaint;
  private int startX;
  private int startY;
  private boolean textShow;
  private float textScale=1.0f;

  public void setTextRoundRect(RectF rectF) {
    this.textRoundRect = rectF;
  }

  public void setImage(Resources resources, @DrawableRes int resId) {
    liveBitmap = new BitmapDrawable(resources,
        BitmapFactory.decodeResource(resources, resId));
  }

  public void setImageRoundRect(RectF rectF) {
    this.imageRoundRect = rectF;
  }

  public void setPaint(Paint paint) {
    this.paint = paint;
  }

  public void setImageRoundSize(int roundSize) {
    this.imageRoundSize = roundSize;
  }

  public void setTextRoundSize(int roundSize) {
    this.textRoundSize = roundSize;
  }

  public void setBounds(int left, int top, int right, int bottom) {
    liveBitmap.setBounds(left, top, right, bottom);
  }

  public void setTextLocation(int textCenterX, int textCenterY) {
    this.textCenterX = textCenterX;
    this.textCenterY = textCenterY;

    textPaint = new Paint();
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTextSize(20);
    textPaint.setColor(Color.BLACK);
  }

  public void draw(Canvas canvas) {
    canvas.save();

    float x = (1 - scale) * (startX - imageRoundRect.centerX());
    float y = (1 - scale) * (startY - imageRoundRect.centerY());
    Log.i("OpenLiveCell", " scale=" + scale + " x=" + x + " y=" + y);
    canvas.translate(x, y);
    canvas.rotate(scale * 360, imageRoundRect.centerX(), imageRoundRect.centerY());

    canvas.scale((1.0f - 0.75f) * scale + 0.75f, (1.0f - 0.75f) * scale + 0.75f,
        imageRoundRect.centerX(), imageRoundRect.centerY());
    canvas.drawRoundRect(imageRoundRect, imageRoundSize, imageRoundSize, paint);
    liveBitmap.draw(canvas);
    canvas.restore();

    if (textShow) {
      Log.i("OpenLiveCell", "textShow scale="+textScale);
      canvas.save();
      canvas.scale(textScale, textScale, textRoundRect.centerX(), textRoundRect.centerY());
      canvas.drawRoundRect(textRoundRect, textRoundSize, textRoundSize, paint);
      canvas.drawText("直播", textCenterX, textCenterY, textPaint);
      canvas.restore();
    }

  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public void setStart(int startX, int startY) {
    this.startX = startX;
    this.startY = startY;
  }

  public void setTextShow(boolean textShow) {
    this.textShow = textShow;
  }

  public void setTextScale(float textScale) {
    this.textScale = textScale;
  }
}
