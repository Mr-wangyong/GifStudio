package com.mrwang.gifstudio.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;


/**
 * System and device related utils.
 */
public class SystemUtils {
  private static String versionName = null;
  private static int versionCode = 0;
  private static String deviceId = null;
  private static String deviceMAC = null;
  private static int screenHeightPx;
  private static int screenWidthPx;

  private static Paint drawPaint = new Paint();

  private SystemUtils() {}

  public static int getApiLevel() {
    return Build.VERSION.SDK_INT;
  }

  /**
   * 居中绘制描边字符串
   */
  public static void drawStrokedText(String content, Canvas canvas, Typeface typeface, float x,
      float y,
      int textSize, int textColor, int strokeColor, float alpha) {
    Paint paint = drawPaint;// new Paint();
    paint.reset();
    if (typeface != null) {
      paint.setTypeface(typeface);
    }
    if (alpha < 0) {
      alpha = 0;
    }

    paint.setTextAlign(Paint.Align.CENTER);
    paint.setColor(strokeColor);
    paint.setTextSize(textSize + 3);
    paint.setAntiAlias(true);

    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(8.0f);
    paint.setColor(strokeColor);
    paint.setAlpha((int) (alpha * 255));
    canvas.drawText(content, x, y, paint);

    paint.setStyle(Paint.Style.FILL);
    paint.setColor(textColor);
    paint.setAlpha((int) (alpha * 255));
    canvas.drawText(content, x, y, paint);
  }

  public static int dpToPx(Context context, float dp) {
    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        context.getResources().getDisplayMetrics());
    return (int) px;
  }

  public static int pxTodp(Context context, float px) {
    float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,
        context.getResources().getDisplayMetrics());
    return (int) dp;
  }


}
