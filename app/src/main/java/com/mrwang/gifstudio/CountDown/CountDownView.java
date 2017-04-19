package com.mrwang.gifstudio.CountDown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.mrwang.gifstudio.R;

/**
 * 游戏进度条
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/18
 * Time: 下午10:18
 */
public class CountDownView extends View {

  private RectF rect;
  private Paint paint;
  private PorterDuffXfermode porterDuffXfermode;
  private int width;
  private int height;
  private int progress;
  private Drawable drawable;
  private int strokeWidth;
  private RectF roundRect;

  public CountDownView(Context context) {
    super(context);
    init();
  }

  public CountDownView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    drawable = ContextCompat.getDrawable(getContext(), R.drawable.count_down);
    paint = new Paint();
    strokeWidth = 20;
    paint.setStrokeWidth(strokeWidth);
    paint.setAntiAlias(true);
    paint.setColor(Color.parseColor("#aa000000"));
    rect = new RectF();
    roundRect = new RectF();
    paint.setStrokeCap(Paint.Cap.ROUND);
    porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.height = h;
    rect.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2,
        height - strokeWidth / 2);
    roundRect.set(0, 0, w, h);
    drawable.setBounds(0, 0, w, h);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  public void setProgress(int progress) {
    this.progress = progress;
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    drawable.draw(canvas);
    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();
    int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
    paint.setStyle(Paint.Style.FILL);
    canvas.drawRoundRect(roundRect, width / 2, height / 2, paint);
    // paint.setColor(Color.TRANSPARENT);
    paint.setXfermode(porterDuffXfermode);
    paint.setStyle(Paint.Style.STROKE);
    canvas.drawArc(rect, progress - 90, 360 - progress, false, paint);
    paint.setXfermode(null);
    canvas.restoreToCount(layerId);
  }
}
