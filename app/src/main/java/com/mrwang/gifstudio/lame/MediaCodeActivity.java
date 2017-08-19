package com.mrwang.gifstudio.lame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.mrwang.gifstudio.R;
import com.mrwang.gifstudio.customView.MusicDownActivity;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/12
 * Time: 下午6:55
 */
public class MediaCodeActivity extends Activity {
  private boolean isSupportHardWare;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view);
    View viewById = findViewById(R.id.view);
    viewById.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MediaCodeActivity.this, MusicDownActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_exit_right);
      }
    });
    // PMCToMP3Test test = new PMCToMP3Test();
    // // test.test2();
    // // test.processMedia();
    // // test.test();
    // // try {
    // // MediaCodec decoderByType = MediaCodec.createByCodecName("video/mp4v-es");
    // // } catch (IOException e) {
    // // e.printStackTrace();
    // // }
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    // checkMediaDecoder();
    // }
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void checkMediaDecoder() {
    MediaCodecList mediaCodecList = new MediaCodecList(MediaCodecList.ALL_CODECS);

    MediaCodecInfo[] codecInfos = mediaCodecList.getCodecInfos();
    for (MediaCodecInfo codecInfo : codecInfos) {
      Log.i("TAG", "codecInfo =" + codecInfo.getName());
    }
  }

  public void fun() {
    MediaExtractor mediaExtractor = new MediaExtractor();

  }
}
