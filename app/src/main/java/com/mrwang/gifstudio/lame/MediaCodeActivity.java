package com.mrwang.gifstudio.lame;

import android.app.Activity;
import android.os.Bundle;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/12
 * Time: 下午6:55
 */
public class MediaCodeActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view);
    PMCToMP3Test test = new PMCToMP3Test();
    //test.test2();
    //test.processMedia();
    test.test();
  }
}
