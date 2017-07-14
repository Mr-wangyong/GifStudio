package com.mrwang.gifstudio.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/7/10
 * Time: 下午2:19
 */
public class DrawTextView extends View {
  private String content="经验 +10000";
  private Paint paint;
  private int strokeColor=Color.parseColor("#163100");
  private int textColor=Color.parseColor("#a8ff00");
  private int textSize=50;

  public DrawTextView(Context context) {
    super(context);
    init();
  }

  public DrawTextView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setColor(Color.YELLOW);
    paint.setShadowLayer(10,5,5,Color.BLUE);
    paint.setTextSkewX(-0.25f);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    //paint.reset();


    paint.setTextAlign(Paint.Align.CENTER);
    paint.setColor(strokeColor);
    paint.setTextSize(textSize + 3);
    paint.setAntiAlias(true);

    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(8.0f);
    paint.setColor(strokeColor);
    canvas.drawText(content, getWidth()/2, getHeight()/2, paint);

    paint.setStyle(Paint.Style.FILL);
    paint.setColor(textColor);
    canvas.drawText(content, 0, 0, paint);

    super.onDraw(canvas);
  }
}
