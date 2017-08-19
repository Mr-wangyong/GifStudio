package com.mrwang.gifstudio.other;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/7/21
 * Time: 下午3:04
 */
public class ThreadView {


  private Context context;
  private WindowManager wManager;

  public ThreadView(Context context) {
    this.context = context;
  }



  public void show(View view, int showLength) {
    // 使用布局加载器，将编写的toast_layout布局加载进来
    // 设置显示的内容
    Toast toast = new Toast(context);
    // 设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
    toast.setGravity(Gravity.CENTER, 0, 0);
    // 设置显示时间
    toast.setDuration(showLength);
    toast.setView(view);
    toast.show();

  }

  public void show(View view){
    wManager = (WindowManager) context.getSystemService(
      Context.WINDOW_SERVICE);
    WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
    mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
    //mParams.format = PixelFormat.RGBA_8888;
    mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
    mParams.width = 1080;//窗口的宽和高
    mParams.height = 1920;
    mParams.x = 0;//窗口位置的偏移量
    mParams.y = 0;
    //mParams.alpha = 0.1f;//窗口的透明度
    wManager.addView(view, mParams);//添加窗口
  }

  public void remove(View view){
    wManager.removeView(view);//移除窗口
  }
}
