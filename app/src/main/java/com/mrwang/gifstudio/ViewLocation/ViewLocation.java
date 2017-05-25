package com.mrwang.gifstudio.ViewLocation;

import android.graphics.Rect;
import android.view.View;

/**
 * 在Android中获取View在父View或在屏幕中的位置可以使用getHitRect、
 * getDrawingRect、getLocalVisibleRect、getGlobalVisibleRect，以判断View是否显示在屏幕中
 * localVisibleRect =Rect(0, 0 - 300, 600)
 * 05-17 10:48:53.736 20690-20690/? I/ViewLocationActivity: globalVisibleRect =Rect(150, 393 - 450,
 * 993)
 * 05-17 10:48:53.736 20690-20690/? I/ViewLocationActivity: drawingRect =Rect(0, 0 - 300, 600)
 * 05-17 10:48:53.736 20690-20690/? I/ViewLocationActivity: focusedRect =Rect(0, 0 - 300, 600)
 * 05-17 10:48:53.736 20690-20690/? I/ViewLocationActivity: hitRect =Rect(150, 150 - 450, 750)
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/17
 * Time: 上午10:40
 */
public class ViewLocation {

  /**
   * View自己的范围 左上角永远是(0,0) 右下角等于宽高
   */
  public static Rect getLocalVisibleRect(View view) {
    Rect rect = new Rect();
    view.getLocalVisibleRect(rect);
    return rect;
  }

  /**
   * 相对于Activity的坐标 减去了标题栏和状态栏 实际位置
   * 
   * @param view
   */
  public static Rect getGlobalVisibleRect(View view) {
    Rect rect = new Rect();
    view.getGlobalVisibleRect(rect);
    return rect;
  }

  /**
   * 绘制的区域 左上角永远是(0,0) 相对于父View 如果全部都被绘制 跟getLocalVisibleRect数据一致
   */
  public static Rect getDrawingRect(View view) {
    Rect rect = new Rect();
    view.getDrawingRect(rect);
    return rect;
  }

  /**
   * 底层也是getDrawingRect(rect);一致 估计后续要废弃
   */
  public static Rect getFocusedRect(View view) {
    Rect rect = new Rect();
    view.getFocusedRect(rect);
    return rect;
  }

  /**
   * 距离父View自己的实际的坐标
   */
  public static Rect getHitRect(View view) {
    Rect rect = new Rect();
    view.getHitRect(rect);
    return rect;
  }

  // /**
  // *
  // * @param view
  // */
  // public static Rect getRect(View view) {
  // Rect rect=new Rect();
  // view.getGlobalVisibleRect(rect);
  // return rect;
  // }
}
