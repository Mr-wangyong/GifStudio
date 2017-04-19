package com.mrwang.gifstudio.StrokedText;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/17
 * Time: 下午4:11
 */
public class StrokeTextActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.storke_text);
    getFragmentManager().beginTransaction().replace(R.id.container, new StrokeFragment())
        .commitAllowingStateLoss();

    // StrokeSingleTextView tv = (StrokeSingleTextView) findViewById(R.id.tv);
    // tv.setTextColor(Color.WHITE);
    // tv.setStrokeColor(Color.BLACK);
  }
}
