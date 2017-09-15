package com.mrwang.gifstudio.viewDrag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mrwang.gifstudio.R;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/8/21
 * Time: 下午2:35
 */
public class ViewDragActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_drag);
  }

  public void onClick(View v){
    Toast.makeText(this,"点击我了",Toast.LENGTH_SHORT).show();
  }
}
