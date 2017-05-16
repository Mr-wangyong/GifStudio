package com.mrwang.gifstudio.TexasProgress;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
  private int width;
  private BitmapDrawable bg;
  private int progress;
  private Paint textPaint;
  private int start;

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
    textPaint.setColor(Color.parseColor("#FFD370"));
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTextSize(SystemUtils.dpToPx(getContext(), 10));
    setWillNotDraw(false);
  }

  public void setProgress(@IntRange(from = 0, to = 100) int progress) {
    this.progress = progress;
    this.progressWidth = (int) (progress / (max + 1.0f) * width);
    invalidate();
  }

  public void setMax(int max) {
    this.max = max;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.height = h;
    setSize(w, h / 2);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  private void setSize(int w, int h) {
    bg.setBounds(0, 0, w, h);
    slode.setBounds(0, 0, h, h);
    LayoutParams layoutParams = (LayoutParams) allInImage.getLayoutParams();
    layoutParams.height = h / 2 - SystemUtils.dpToPx(getContext(), 4);
    layoutParams.leftMargin = w - rightSpace - layoutParams.height * 3 / 2;
    layoutParams.topMargin = h + h / 6;
    allInImage.setLayoutParams(layoutParams);
    showButtom.setBounds(0, 0, w / 5, h);
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
    if (x > spaceWidth && x < width - spaceWidth - height / 2 - buttonSpace) {
      progressWidth = (int) x;
      invalidate();
      return true;
    }
    return false;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int width = getWidth() - rightSpace;
    int height = getHeight();
    canvas.save();
    canvas.translate(0, height / 2);
    bg.draw(canvas);
    blackProgress.setBounds(spaceWidth, 0, width - spaceWidth - buttonSpace, height / 4);
    canvas.save();
    canvas.translate(0, height / 16);
    blackProgress.draw(canvas);
    canvas.restore();

    float v = progressWidth;
    if (v < spaceWidth) {
      v = spaceWidth;
    } else if (v > width - spaceWidth - height / 4 - buttonSpace) {
      v = width - spaceWidth - height / 4 - buttonSpace;
    }
    yellowProgress.setBounds(0, 0, (int) v, height / 4);

    canvas.save();
    canvas.translate(spaceWidth, height / 16);
    yellowProgress.draw(canvas);
    canvas.translate((int) v - height / 4, -height / 8);
    slode.draw(canvas);
    canvas.translate(-height / 8, -height / 3 - SystemUtils.dpToPx(getContext(), 5));
    showButtom.draw(canvas);
    canvas.drawText(String.valueOf(
        ((int) (v - spaceWidth / (width - spaceWidth - height / 4 - buttonSpace) * max + start)
            * 100)
            / 100),
        showButtom.getBounds().centerX(),
        showButtom.getBounds().centerY() - height / 32,
        textPaint);
    canvas.restore();
    canvas.restore();

    super.onDraw(canvas);
  }

  public void setStart(int start) {
    this.start = start;
  }
}
