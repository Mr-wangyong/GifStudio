package com.mrwang.gifstudio.ViewFlip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrwang.gifstudio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/20
 * Time: 下午6:12
 */
public class ViewFlipActivity extends AppCompatActivity {
  private UPMarqueeView upview1;
  List<String> data = new ArrayList<>();
  List<View> views = new ArrayList<>();


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_flip);
    initParam();
    initdata();
    initView();
  }

  /**
   * 实例化控件
   */
  private void initParam() {
    upview1 = (UPMarqueeView) findViewById(R.id.upview1);
  }

  /**
   * 初始化界面程序
   */
  private void initView() {
    setView();
    upview1.setViews(views);
  }

  /**
   * 初始化需要循环的View
   * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
   * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
   */
  private void setView() {
    for (int i = 0; i < data.size(); i++) {
      // 设置滚动的单个布局
      LinearLayout moreView =
          (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_view, null);
      // 初始化布局的控件
      TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
      // 进行对控件赋值
      tv1.setText(data.get(i));

      // 添加到循环滚动数组里面去
      views.add(moreView);
    }
    upview1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        upview1.stop();
      }
    });

    upview1.post(new Runnable() {
      @Override
      public void run() {
        upview1.stop();
      }
    });


    update();
  }

  private int topUpdateTime = 3000;
  private int num = 0;

  public void update() {
    int bottomUpdateTime = 3000;
    upview1.postDelayed(new Runnable() {
      @Override
      public void run() {
        upview1.stop();
        upview1.showNext();
        num++;
        if (num == 6) {
          topUpdateTime = 10000;
        }
        update();
        Log.i("TAG", "num=" + num + " topUpdateTime=" + topUpdateTime + " bottomUpdateTime="
            + 3000);
      }
    }, num % 2 == 0 ? topUpdateTime : bottomUpdateTime);
  }

  /**
   * 初始化数据
   */
  private void initdata() {
    data = new ArrayList<>();
    data.add("家人给2岁孩子喝这个，孩子智力倒退10岁!!!");
    data.add("iPhone8最感人变化成真，必须买买买买!!!!");
    // data.add("简直是白菜价！日本玩家33万甩卖15万张游戏王卡");
    // data.add("iPhone7价格曝光了！看完感觉我的腰子有点疼...");
    // data.add("主人内疚逃命时没带够，回废墟狂挖30小时！");
    // data.add("竟不是小米乐视！看水抢了骁龙821首发了！！！");

  }
}
