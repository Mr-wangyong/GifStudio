package com.mrwang.gifstudio.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // TextView tv = (TextView) findViewById(R.id.tv);
    // TextView textView = (TextView) findViewById(R.id.tv);
    // textView.setText("123456");
    //
    GifSurface gifSurface = (GifSurface) findViewById(R.id.surface);


    // ViewAnim anim=new ViewAnim(getApplication());

    FireWorksAnim anim = new FireWorksAnim(getApplicationContext());

    //PathAnim anim=new PathAnim(getApplicationContext());
    gifSurface.addGif(0, anim);

    // int startColor = ContextCompat.getColor(getApplicationContext(), R.color.startColor);
    // int endColor = ContextCompat.getColor(getApplicationContext(), R.color.endColor);
    // ExpAnim expAnim = new ExpAnim(startColor,endColor);
    // gifSurface.addGif(0, expAnim);


  }
}
