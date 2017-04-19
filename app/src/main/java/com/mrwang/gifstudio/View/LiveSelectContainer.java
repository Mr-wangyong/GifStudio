package com.mrwang.gifstudio.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.mrwang.gifstudio.R;


/**
 * 开播选择
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/7
 * Time: 下午5:27
 */
public class LiveSelectContainer extends View {

  private int heigth;
  private int width;
  private BitmapDrawable gameBitmap;
  private BitmapDrawable liveBitmap;
  private RectF roundRect;
  private int innerSize = 80;
  private int roundSize = 100;
  private Paint paint;
  private OpenLiveCell cell1;
  private int startX;
  private int startY;

  public LiveSelectContainer(Context context) {
    this(context, null);
  }

  public LiveSelectContainer(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LiveSelectContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public LiveSelectContainer(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    roundRect = new RectF();
    paint = new Paint();
    paint.setColor(Color.WHITE);
    cell1 = new OpenLiveCell();
    cell1.setImage(getResources(), R.mipmap.livestart_game);
    cell1.setImageRoundSize(roundSize);
    cell1.setPaint(paint);
  }



  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.width = w;
    this.heigth = h;

    startX = width / 2;
    startY = heigth / 2;
    cell1.setStart(startX,startY);

    int leftMargin = width/4;

    cell1.setBounds(width / 2 - innerSize - leftMargin, heigth / 2 - innerSize - leftMargin,
        width / 2 + innerSize - leftMargin,
        heigth / 2 + innerSize - leftMargin);
    roundRect.set(width / 2 - roundSize - leftMargin, heigth / 2 - roundSize - leftMargin,
        width / 2 + roundSize - leftMargin,
        heigth / 2 + roundSize - leftMargin);

    cell1.setImageRoundRect(roundRect);

    cell1.setTextLocation(width / 2 - leftMargin,
        heigth / 2 - leftMargin + roundSize + 85);

    RectF textRoundRect = new RectF();
    textRoundRect.set(width / 2 - roundSize - leftMargin, heigth / 2 + roundSize + 50 - leftMargin,
        width / 2 + roundSize - leftMargin,
        heigth / 2 + roundSize + 100 - leftMargin);


    cell1.setTextRoundRect(textRoundRect);
    cell1.setTextRoundSize(25);

    // test


    super.onSizeChanged(w, h, oldw, oldh);
  }

  public void startAnim() {
    if (startX != 0 || startY != 0) {
      ValueAnimator imageAnim = ValueAnimator.ofInt(1, 100);
      imageAnim.setDuration(5000L);
      imageAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          float animatedFraction = animation.getAnimatedFraction();
          cell1.setScale(animatedFraction);
          invalidate();
        }
      });
      imageAnim.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          Log.i("TAG","onAnimationEnd");
          cell1.setTextShow(true);
        }

        @Override
        public void onAnimationStart(Animator animation) {
          Log.i("TAG","onAnimationStart");
          cell1.setTextShow(false);
        }
      });

      ValueAnimator textAnim = ValueAnimator.ofFloat(0,1f);
      textAnim.setInterpolator(new OvershootInterpolator());
      textAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          Log.i("TAG","textAnim onAnimationUpdate");
          cell1.setTextScale((float) animation.getAnimatedValue());
          invalidate();
        }
      });
      textAnim.setDuration(500);
      textAnim.setStartDelay(100);

      AnimatorSet set=new AnimatorSet();
      set.playSequentially(imageAnim,textAnim);
      set.start();

    }

  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    paint.setStrokeWidth(0);
    cell1.draw(canvas);
    paint.setStrokeWidth(30);
    paint.setStrokeCap(Paint.Cap.ROUND);
    canvas.drawPoint(width/2,heigth/2,paint);
  }
}
