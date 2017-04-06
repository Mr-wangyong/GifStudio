package com.mrwang.gifstudio.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/31
 * Time: 下午3:49
 */
public class CustomView extends View {
  private int width;
  private int height;
  private Paint bgPaint;
  private Paint middlePaint;
  private PorterDuffXfermode mXfermode;
  private Paint mBitPaint;
  private int bgSize;
  private Bitmap smallRoundBitmap;

  public CustomView(Context context) {
    this(context, null);
  }

  public CustomView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }
  private RectF bgRect;

  private void init() {
    bgPaint = new Paint();
    bgPaint.setColor(Color.BLUE);

    middlePaint=new Paint();
    middlePaint.setColor(Color.YELLOW);
    mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBitPaint.setFilterBitmap(true);
    mBitPaint.setDither(true);
    mBitPaint.setColor(Color.RED);
    bgRect = new RectF();
    bgSize = dp2px(1);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.height = h;
    smallRoundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas=new Canvas(smallRoundBitmap);
    canvas.drawColor(Color.BLUE);
    bgRect.set(bgSize, bgSize, w - bgSize, h - bgSize);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int saveLayerCount = canvas.saveLayer(0, 0, width, height, mBitPaint,
        Canvas.ALL_SAVE_FLAG);
    // 绘制目标图
    canvas.drawBitmap(smallRoundBitmap, width/2, height/2, mBitPaint);
    // 设置混合模式
    mBitPaint.setXfermode(mXfermode);
    // 绘制源图形
    canvas.drawRoundRect(bgRect,width,height, mBitPaint);
    // 清除混合模式
    mBitPaint.setXfermode(null);
    // 恢复保存的图层；
    canvas.restoreToCount(saveLayerCount);
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
