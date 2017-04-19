package com.mrwang.gifstudio.StrokedText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

public class StrokeSingleTextView extends android.support.v7.widget.AppCompatTextView {

  //private TextPaint textPaint;
  private int textColor, strokeColor, startColor, endColor;
  private float strokeWidth = 5f;
  private boolean gradient;
  private boolean m_bDrawSideLine = true; // 默认采用描边
  private LinearGradient linearGradient;

  public StrokeSingleTextView(Context context) {
    super(context);
  }

  public StrokeSingleTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public StrokeSingleTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public StrokeSingleTextView(Context context, int textColor, int strokeColor) {
    super(context);
    //this.textPaint = getPaint();
    this.textColor = textColor;
    this.strokeColor = strokeColor;
  }

  @Override
  public void setTextColor(int textColor) {
    this.textColor = textColor;
  }

  public void setStrokeColor(int strokeColor) {
    this.strokeColor = strokeColor;
  }

  public void setStartColor(int startColor) {
    this.startColor = startColor;
  }

  public void setEndColor(int endColor) {
    this.endColor = endColor;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    linearGradient = new LinearGradient(0, 0, 0, h, startColor, endColor, Shader.TileMode.CLAMP);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  public void setStrokeTextColor(int textColor) {
    this.textColor = textColor;
    invalidate();
  }

  public void setStrokeViewColor(int strokeColor){
    this.strokeColor = strokeColor;
    invalidate();
  }

  /**
   *
   */
  @Override
  protected void onDraw(Canvas canvas) {
    TextPaint textPaint = getPaint();
    if (m_bDrawSideLine) {
      // 描外层
      textPaint.setShader(null);
      //textPaint.setColor(strokeColor);
      super.setTextColor(strokeColor);
      textPaint.setStrokeWidth(strokeWidth); // 描边宽度
      textPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
      textPaint.setFakeBoldText(true); // 外层text采用粗体
      //textPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
      super.onDraw(canvas);
    }
    // 描内层，恢复原先的画笔
    if (gradient) {
      textPaint.setShader(linearGradient);
    } else {
      textPaint.setColor(textColor);
    }
    textPaint.setStrokeWidth(0);
    textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    textPaint.setFakeBoldText(false);
    //textPaint.setShadowLayer(0, 0, 0, 0);
    super.onDraw(canvas);
  }

  /**
   * 使用反射的方法进行字体颜色的设置
   *
   * @param color
   */
  private void setTextColorUseReflection(int color) {
    Field textColorField;
    try {
      textColorField = TextView.class.getDeclaredField("mCurTextColor");
      textColorField.setAccessible(true);
      textColorField.set(this, color);
      textColorField.setAccessible(false);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    //textPaint.setColor(color);
  }

}
