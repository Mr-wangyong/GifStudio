package com.mrwang.gifstudio.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/23
 * Time: 下午4:40
 */
public class CustomTextView extends AppCompatTextView {

  private Paint paint = getPaint();
  LinearGradient linearGradient;

  public CustomTextView(Context context) {
    super(context);
  }

  public CustomTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    linearGradient = new LinearGradient(0, 0, 0, h, Color.BLUE, Color.YELLOW, Shader.TileMode.CLAMP);
  }

  @Override
  protected void onDraw(Canvas canvas) {

    paint.setStrokeWidth(3);

    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setShader(linearGradient);
    setTextColor(Color.RED);
    super.onDraw(canvas);
//    paint.setStyle(Paint.Style.FILL);
//    setTextColor(Color.GREEN);
//    super.onDraw(canvas);
  }
}
