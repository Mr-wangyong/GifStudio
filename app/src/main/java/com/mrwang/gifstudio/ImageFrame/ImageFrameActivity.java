package com.mrwang.gifstudio.ImageFrame;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mrwang.gifstudio.R;
import com.mrwang.imageframe.ImageFrameCustomView;
import com.mrwang.imageframe.ImageFrameHandler;

public class ImageFrameActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_frame);

    final ImageFrameCustomView imageFrameCustomView = (ImageFrameCustomView) findViewById(R.id.image_frame);
    View pause = findViewById(R.id.start);
    View start = findViewById(R.id.pause);

    imageFrameCustomView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadResBuilder(imageFrameCustomView);
      }
    });

    pause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (imageFrameCustomView.getImageFrameHandler()!=null){
          imageFrameCustomView.getImageFrameHandler().pause();
        }
      }
    });

    start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (imageFrameCustomView.getImageFrameHandler()!=null){
          imageFrameCustomView.getImageFrameHandler().start();
        }
      }
    });
  }

  private void loadResBuilder(final ImageFrameCustomView imageFrame) {
    final int[] resIds = new int[210];
    Resources res = getResources();
    final String packageName = getPackageName();
    for (int i = 0; i < resIds.length; i++) {
      int imageResId = res.getIdentifier("gift_" + (i + 1), "drawable", packageName);
      resIds[i] = imageResId;
      Log.e("TAG", "imageResId=" + imageResId);
    }

    ImageFrameHandler build = new ImageFrameHandler.ResourceHandlerBuilder(getResources(), resIds)
      .setFps(30)
      .setStartIndex(10)
      .setEndIndex(210)
      .setLoop(true)
      .build();

    imageFrame.startImageFrame(build);
  }
}
