package com.mrwang.gifstudio.TexasProgress;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mrwang.gifstudio.R;
import com.mrwang.gifstudio.SystemUtils;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date:2017/5/15
 * Time: 上午10:50
 */
public class TexasProgress extends FrameLayout {
  private static final String TAG = "TexasProgress";
  private int progressWidth;
  private int max = 100;
  private BitmapDrawable yellowProgress;
  private BitmapDrawable blackProgress;
  private BitmapDrawable slode;
  private BitmapDrawable showButtom;
  private int spaceWidth;
  private int rightSpace;
  private ImageView allInImage;
  private int buttonSpace;
  private int height;
  private BitmapDrawable bg;
  private int progress;
  private Paint textPaint;
  private int start;
  private int totalPgWidth;

  public TexasProgress(Context context) {
    super(context);
    init();
  }

  public TexasProgress(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public TexasProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    spaceWidth = SystemUtils.dpToPx(getContext(), 15);
    rightSpace = SystemUtils.dpToPx(getContext(), 50);

    buttonSpace = SystemUtils.dpToPx(getContext(), 10);

    LayoutParams params =
        new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    allInImage = new ImageView(getContext());
    allInImage.setImageResource(R.drawable.all_in_progress);
    addView(allInImage, params);
    allInImage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        setProgress(max);
      }
    });

    textPaint = new Paint();
    textPaint.setColor(ContextCompat.getColor(getContext(), R.color.progress_color));
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setStrokeWidth(1);

    textPaint.setTextSize(SystemUtils.dpToPx(getContext(), 10));
    setWillNotDraw(false);
  }

  public void setProgress(@IntRange(from = 0, to = 100) int progress) {
    this.progress = progress;
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
    totalPgWidth = width - spaceWidth - height / 4 - buttonSpace - rightSpace;
    super.onSizeChanged(w, h, oldw, oldh);
  }

  private void setSize(int w, int h) {
    bg.setBounds(0, 0, w, h);
    slode.setBounds(0, 0, h - SystemUtils.dpToPx(getContext(), 5),
        h - SystemUtils.dpToPx(getContext(), 5));
    LayoutParams layoutParams = (LayoutParams) allInImage.getLayoutParams();
    layoutParams.height = h / 2 - SystemUtils.dpToPx(getContext(), 4);
    layoutParams.leftMargin = w - rightSpace - spaceWidth - layoutParams.height * 3;
    layoutParams.topMargin = h + h / 6;
    allInImage.setLayoutParams(layoutParams);
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
        break;
    }
    return super.onTouchEvent(event);
  }

  private boolean setMoveX(float x) {
    x = x - height / 8;
    if (x < spaceWidth) {
      x = spaceWidth;
    }
    if (x > totalPgWidth) {
      x = totalPgWidth;
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
    blackProgress.setBounds(spaceWidth, 0, totalPgWidth + spaceWidth, height / 4);

    canvas.save();
    canvas.translate(0, height / 16);
    blackProgress.draw(canvas);
    canvas.restore();

    float v = progressWidth;
    if (v < spaceWidth) {
      v = spaceWidth;
    } else if (v >= totalPgWidth) {
      v = totalPgWidth;
    }
    yellowProgress.setBounds(0, 0, Math.round(v), height / 4);

    canvas.save();
    canvas.translate(spaceWidth, height / 16);
    yellowProgress.draw(canvas);
    canvas.restore();

    canvas.save();
    canvas.translate(Math.round(v - slode.getBounds().width() / 4),
        -height / 4 + slode.getBounds().height() / 2);
    slode.draw(canvas);

    canvas.translate(-height / 8, -height / 2.8f);
    showButtom.draw(canvas);
    float v1 =
        (v > spaceWidth ? v : 0) / (totalPgWidth) * (max - start)
            + start;
    float v2 = v1 % start;
    int round = v2 > start / 2 ? Math.round(v1 - v2) : Math.round(v1 - v2 + start);
    Log.i(TAG, "v=" + v + " string=" + v1 + " start=" + totalPgWidth + " end="
        + (v > spaceWidth ? v : 0) / (totalPgWidth) + " round=" + round);
    // textPaint.setStyle(Paint.Style.FILL);
    // textPaint.setColor(ContextCompat.getColor(getContext(), R.color.progress_color));
    canvas.drawText(String.valueOf(
        round),
        showButtom.getBounds().centerX(),
        showButtom.getBounds().centerY() - height / 32,
        textPaint);
    // textPaint.setStyle(Paint.Style.STROKE);
    // textPaint.setColor(Color.BLACK);
    // canvas.drawText(String.valueOf(
    // round),
    // showButtom.getBounds().centerX(),
    // showButtom.getBounds().centerY() - height / 32,
    // textPaint);

    canvas.restore();
    canvas.restore();

    super.onDraw(canvas);
  }

  public void setStart(int start) {
    this.start = start;
  }

}
