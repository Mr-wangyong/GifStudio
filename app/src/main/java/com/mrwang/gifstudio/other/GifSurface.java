package com.mrwang.gifstudio.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/3/27
 * Time: 下午1:45
 */
public class GifSurface extends SurfaceView
    implements
      SurfaceHolder.Callback,
      SurfaceHandler.OnThreadLooperPrepared {

  private volatile SparseArray<AnimationInterface> gifList;
  private boolean isRuning = true;
  private SurfaceHolder holder;
  private long lastUpdateTime;
  private SurfaceHandler surfaceHandler;

  public GifSurface(Context context) {
    this(context, null);
  }

  public GifSurface(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GifSurface(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public GifSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    gifList = new SparseArray<>();
    setZOrderOnTop(true);
    holder = getHolder();
    holder.addCallback(this);
    holder.setFormat(PixelFormat.TRANSPARENT);// 更改surface背景为透明

  }


  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    surfaceHandler = new SurfaceHandler();
    surfaceHandler.setOnLoopPrepared(this);
    surfaceHandler.sendEmpty(0);
    isRuning=true;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    isRuning = false;
  }


  public void addGif(int type, AnimationInterface anim) {
    gifList.put(type, anim);
    isRuning = true;
    //surfaceHandler.sendEmpty(gifList.size());
  }

  @Override
  public void OnThreadLooperPrepared() {
    if (isRuning) {
//      Log.i("TAG", "OnThreadLooperPrepared");
//      if (!holder.getSurface().isValid()) {
//        surfaceHandler.sendEmpty(gifList.size());
//        return;
//      }
      float deltaTime = (System.nanoTime() - lastUpdateTime) / 1000000000.0f;
      lastUpdateTime = System.nanoTime();
      Canvas canvas = null;
      try {
        canvas = holder.lockCanvas();
        if (canvas != null) {
          canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
          for (int i = 0; i < gifList.size(); i++) {
            AnimationInterface anim = gifList.valueAt(i);
            anim.update(deltaTime);
            anim.present(canvas, deltaTime);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (canvas != null) {
          try {
            holder.unlockCanvasAndPost(canvas);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      surfaceHandler.sendEmpty(gifList.size());
    }
  }
}
