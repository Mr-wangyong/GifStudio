package com.mrwang.gifstudio.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mrwang.gifstudio.R;

/**
 * TextView预渲染研究
 * http://ragnraok.github.io/textview-pre-render-research.html
 */
public class MainActivity extends AppCompatActivity {

  private Button staticLayoutBtn;
  private Button normalLayoutBtn;
  private Button staticLongStringBtn;
  private Button normalLongStringBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.text_view);

    staticLayoutBtn = (Button) findViewById(R.id.static_layout_test);
    normalLayoutBtn = (Button) findViewById(R.id.normal_layout_test);
    staticLongStringBtn = (Button) findViewById(R.id.static_long_string);
    normalLongStringBtn = (Button) findViewById(R.id.normal_long_string);

    GhostThread.start();

    staticLayoutBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, StaticLayoutUI.class);
        startActivity(intent);
      }
    });

    normalLayoutBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, NormalLayoutUI.class);
        startActivity(intent);
      }
    });

    staticLongStringBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, StaticLongStringUI.class);
        startActivity(intent);
      }
    });

    normalLongStringBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, NormalLongStringUI.class);
        startActivity(intent);
      }
    });

    FpsCalculator.instance().start();

    new Thread(new Runnable() {
      @Override
      public void run() {
        TestSpan.init(MainActivity.this);
        StaticLayoutManager.getInstance().initLayout(MainActivity.this, TestSpan.getSpanString(),
            TestSpan.getLongSpanString());


        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(MainActivity.this, "init layout and span finish", Toast.LENGTH_LONG)
                .show();
          }
        });
      }
    }).start();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    FpsCalculator.instance().stop();
    GhostThread.stop();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }
}
