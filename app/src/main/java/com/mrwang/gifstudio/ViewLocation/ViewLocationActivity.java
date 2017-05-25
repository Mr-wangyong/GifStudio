package com.mrwang.gifstudio.ViewLocation;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/17
 * Time: 上午10:41
 */
public class ViewLocationActivity extends AppCompatActivity {
  public String TAG="ViewLocationActivity";
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_location);
    final View view = findViewById(R.id.view);
    view.post(new Runnable() {


      @Override
      public void run() {
        Rect localVisibleRect = ViewLocation.getLocalVisibleRect(view);
        Rect globalVisibleRect = ViewLocation.getGlobalVisibleRect(view);
        Rect drawingRect = ViewLocation.getDrawingRect(view);
        Rect focusedRect = ViewLocation.getFocusedRect(view);
        Rect hitRect = ViewLocation.getHitRect(view);

        Log.i(TAG,"localVisibleRect ="+localVisibleRect);
        Log.i(TAG,"globalVisibleRect ="+globalVisibleRect);
        Log.i(TAG,"drawingRect ="+drawingRect);
        Log.i(TAG,"focusedRect ="+focusedRect);
        Log.i(TAG,"hitRect ="+hitRect);
      }
    });
  }
}
