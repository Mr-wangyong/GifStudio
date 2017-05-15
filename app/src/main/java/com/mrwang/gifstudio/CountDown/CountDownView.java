package com.mrwang.gifstudio.CountDown;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
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
  private float progress;
  private int strokeWidth;
  private RectF roundRect;
  private Bitmap bitmap;
  private Paint bitmapPaint;
  public boolean isRect = true;
  private Path path;
  private PathMeasure measure;
  private Path dst;
  private float length;

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

  public void isRect(boolean isRect) {
    this.isRect = isRect;
    init();
    onSizeChanged(width, height, width, height);
  }

  private void init() {
    // drawable = ContextCompat.getDrawable(getContext(), R.drawable.count_down);
    if (isRect) {
      bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zhubo_count_down);
      path = new Path();
      // 创建用于存储截取后内容的 Path
      dst = new Path();

      // 将 Path 与 PathMeasure 关联
      measure = new PathMeasure(path, false);
    } else {
      bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.count_down);
    }

    paint = new Paint();
    strokeWidth = 20;
    paint.setStrokeWidth(strokeWidth);
    paint.setAntiAlias(true);
    paint.setColor(Color.parseColor("#99000000"));
    rect = new RectF();
    roundRect = new RectF();
    paint.setStrokeCap(Paint.Cap.ROUND);
    porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);// XDR CLEAR都是清楚的效果
                                                                       // OVERLAY是覆盖的效果
    bitmapPaint = new Paint();
    setLayerType(LAYER_TYPE_SOFTWARE, null);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.height = h;
    rect.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2,
        height - strokeWidth / 2);
    if (isRect) {//这里有一个bug addRoundRect 默认从右上角开始画 奇怪
      path.reset();
      path.rewind();
      path.addRect(rect, Path.Direction.CCW);
      path.setLastPoint(rect.right,rect.top);
      measure.setPath(path, false);
      length = measure.getLength();
    }
    roundRect.set(strokeWidth, strokeWidth, w - strokeWidth, h - strokeWidth);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  public void setProgress(float progress) {
    if (isRect) {
      this.progress = progress;
    } else {
      progress = (int) (progress * 360);
    }
    this.progress = progress;
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // 中间给渲染成透明的
    canvas.save();
    canvas.scale(width / (bitmap.getWidth() + 0.0f), height / (bitmap.getHeight() + 0.0f));
    canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    canvas.restore();
    bitmapPaint.setXfermode(porterDuffXfermode);
    if (isRect) {
      canvas.drawRoundRect(roundRect, strokeWidth / 2, strokeWidth / 2, bitmapPaint);
    } else {
      canvas.drawRoundRect(roundRect, width / 2, height / 2, bitmapPaint);
    }
    bitmapPaint.setXfermode(null);

    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();
    int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
    paint.setStyle(Paint.Style.STROKE);
    if (isRect) {
      canvas.drawRoundRect(rect, strokeWidth, strokeWidth, paint);
    } else {
      canvas.drawRoundRect(rect, width / 2, height / 2, paint);
    }
    paint.setXfermode(porterDuffXfermode);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeJoin(Paint.Join.ROUND);

    if (isRect) {
      // 画path
      // canvas.drawPath();
      // canvas.drawArc(rect, progress - 90, 360 - progress, false, paint);
      // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
      dst.reset();
      dst.rewind();
      float v = length * (1.0f - progress);
      // float v = length * progress / 360;
      System.out
          .println("length=" + v + " v=" + (1.0f - progress) + " progress=" + progress);
      measure.getSegment(0, v, dst, true);
      canvas.drawPath(dst, paint);
    } else {
      canvas.drawArc(rect, progress - 90, 360 - progress, false, paint);
    }
    paint.setXfermode(null);

    canvas.restoreToCount(layerId);
  }
}
