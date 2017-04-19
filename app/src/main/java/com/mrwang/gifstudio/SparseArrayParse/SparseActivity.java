package com.mrwang.gifstudio.SparseArrayParse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/13
 * Time: 下午5:33
 */
public class SparseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SparseArrayParse parse=new SparseArrayParse();
    parse.parse();
  }
}
