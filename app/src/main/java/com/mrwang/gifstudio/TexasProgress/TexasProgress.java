package com.mrwang.gifstudio.TexasProgress;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.mrwang.gifstudio.R;
import com.mrwang.gifstudio.other.SystemUtils;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date:2017/5/15
 * Time: 上午10:50
 */
public class TexasProgress extends RelativeLayout {
  private int progressWidth;
  public int max = 100;
  private BitmapDrawable yellowProgress;
  private BitmapDrawable blackProgress;
  private BitmapDrawable slode;
  private BitmapDrawable showButtom;
  private int leftWidth;
  private int rightSpace;
  // private ImageView allInImage;
  private int buttonSpace;
  private int height;
  private BitmapDrawable bg;
  private int progress;
  private Paint textPaint;
  private int start;
  private boolean showFull;
  private int strokeColor;
  private int totalPgWidth;// 能滑动的区域最大值
  private int progressNum;
  private int textColor;
  private int betLower;
  public boolean isMax;
  // private DownProgress.OnProgress onProgress;

  public TexasProgress(Context context) {
    super(context);
    init();
  }

  public TexasProgress(Context context,  AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TexasProgress(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    yellowProgress = new BitmapDrawable(getResources(),
        BitmapFactory.decodeResource(getResources(), R.drawable.progress_bar));
    blackProgress = new BitmapDrawable(getResources(),
        BitmapFactory.decodeResource(getResources(), R.drawable.progress_bar_bg));
    slode = new BitmapDrawable(getResources(),
        BitmapFactory.decodeResource(getResources(), R.drawable.button_slide));
    showButtom = new BitmapDrawable(getResources(),
        BitmapFactory.decodeResource(getResources(), R.drawable.show_money_bg));

    bg = new BitmapDrawable(getResources(),
        BitmapFactory.decodeResource(getResources(), R.drawable.bottom_pour_bar_bg));
    // setBackgroundResource(R.drawable.bottom_pour_bar_bg);
    leftWidth = SystemUtils.dpToPx(getContext(), 25);
    rightSpace = SystemUtils.dpToPx(getContext(), 120);

    buttonSpace = SystemUtils.dpToPx(getContext(), 10);

    textPaint = new Paint();
    textColor = Color.parseColor("#FFD370");
    textPaint.setColor(textColor);
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTextAlign(Paint.Align.CENTER);
    // textPaint.setTypeface(FontsUtils.getNumTypeface());
    // textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.big_georgia));
    // strokeColor = ContextCompat.getColor(getContext(), R.color.opacity_7_black);
    int strokeWidth = SystemUtils.dpToPx(getContext(), 1);
    textPaint.setStrokeWidth(strokeWidth);
    setWillNotDraw(false);
  }

  public void setProgress(int progress) {
    this.progress = progress;
    isMax = progress == max;
    this.progressWidth = Math.round(progress / (max + 1.0f) * totalPgWidth);
    invalidate();
  }

  public void setMax(int max) {
    this.max = max;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    int width = w - getPaddingLeft();
    this.height = h;
    setSize(width, h / 2);
    totalPgWidth = width - rightSpace - height / 4;
    super.onSizeChanged(w, h, oldw, oldh);
  }

  private void setSize(int w, int h) {
    bg.setBounds(0, 0, w, h);
    slode.setBounds(0, 0, h - SystemUtils.dpToPx(getContext(), 5),
        h - SystemUtils.dpToPx(getContext(), 5));
    showButtom.setBounds(0, 0, w / 4, h);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x;
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        x = event.getX();
        return setMoveX(x);
      case MotionEvent.ACTION_MOVE:
        x = event.getX();
        return setMoveX(x);
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_OUTSIDE:
        // 隐藏进度条
        break;
    }
    return true;
  }

  private boolean setMoveX(float x) {
    x = x - height / 8;
    if (x < leftWidth) {
      x = leftWidth;
    }
    if (x >= totalPgWidth + leftWidth) {
      x = totalPgWidth + leftWidth;
      isMax = true;
    } else {
      isMax = false;
    }
    progressWidth = Math.round(x);
    invalidate();
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.save();
    canvas.translate(getPaddingLeft(), height / 2);
    bg.draw(canvas);
    blackProgress.setBounds(leftWidth, 0, totalPgWidth + leftWidth * 2, height / 4);

    canvas.save();
    canvas.translate(0, height / 16);
    blackProgress.draw(canvas);
    canvas.restore();

    float v;
    if (!showFull) {
      v = progressWidth;
      if (v < leftWidth) {
        v = leftWidth;
        isMax = false;
      } else if (v > totalPgWidth + leftWidth) {
        v = totalPgWidth + leftWidth;
        isMax = true;
      }
    } else {
      v = totalPgWidth;
      isMax = true;
    }

    yellowProgress.setBounds(0, 0, Math.round(v), height / 4);

    canvas.save();
    canvas.translate(leftWidth, height / 16);
    yellowProgress.draw(canvas);
    canvas.restore();

    canvas.save();
    canvas.translate(Math.round(v - slode.getBounds().width() / 4),
        -height / 4 + slode.getBounds().height() / 2);
    slode.draw(canvas);

    canvas.translate(-height / 6, -height / 2.8f);
    showButtom.draw(canvas);

    // if (isMax) {
    // progressNum = max;
    // } else {
    // float v1 = (v > leftWidth ? v : 0) / (totalPgWidth) * (max - start)
    // + start;
    // float v2 = v1 % betLower;
    // progressNum = v2 > betLower / 2 ? Math.round(v1 - v2) : Math.round(v1 - v2 + betLower);
    //
    // //新增需要
    // //前 50% 20% 后50% 80%
    //
    // }
    // String s = UnitUtils.formatNum(progressNum);
    // LogUtils.i("progressNum =" + progressNum + " max=" + max);
    // 先计算占比长度
    float speedWidth = v - leftWidth;
    // 计算比例 0~100
    float speed = speedWidth / totalPgWidth;


    float num;
    int sumNum = max - start;
    if (speed < 0.5f) {// 前 50% 20%
      num = speed * 2 * sumNum * 0.2f + start;
    } else {// 前 50% 剩余的 80%
      num = speed * sumNum * 0.8f + sumNum * 0.2f+start;
    }

    Log.i("TAG", " speedWidth=" + speedWidth + " speed=" + speed + " num=" + num);
    textPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
    textPaint.setColor(strokeColor);
    String s = String.valueOf(num);
    canvas.drawText(s,
        showButtom.getBounds().centerX(),
        showButtom.getBounds().centerY() - height / 32,
        textPaint);

    // if (onProgress != null) {
    // onProgress.onProgress(progressNum + betLower);
    // }
    textPaint.setColor(textColor);
    textPaint.setStyle(Paint.Style.FILL);
    canvas.drawText(s,
        showButtom.getBounds().centerX(),
        showButtom.getBounds().centerY() - height / 32,
        textPaint);
    canvas.restore();

    canvas.restore();
    super.onDraw(canvas);
  }

  public void setStart(int start) {
    setProgress(0);
    this.start = start;
  }

  public void showFull(boolean showFull) {
    this.showFull = showFull;
  }

  public int getProgress() {
    return progress;
  }


  public float getProgressNum() {
    return progressNum;
  }

  public void setBetLower(int betLower) {
    this.betLower = betLower;
  }

  // public void setOnProgress(DownProgress.OnProgress progress) {
  // this.onProgress = progress;
  // }
}
