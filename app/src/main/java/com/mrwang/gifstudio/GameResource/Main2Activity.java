package com.mrwang.gifstudio.GameResource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mrwang.gifstudio.R;
import com.mrwang.imageframe.ImageFrameCustomView;
import com.mrwang.imageframe.ImageFrameHandler;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);


    // new Thread(){
    // @Override
    // public void run() {
    // String absolutePath = Environment
    // .getExternalStorageDirectory().getAbsolutePath();
    // File file=new File(absolutePath+"/blink_thor");
    // for (File file1 : file.listFiles()) {
    // Log.i("TAG","file1 name="+file1.getName());
    // }
    // }
    // }.start();
    final ImageFrameCustomView image = (ImageFrameCustomView) findViewById(R.id.image);
    image.post(new Runnable() {
      @Override
      public void run() {
        GameResourceProcess.GameResourceEntity action =
            GameResourceProcess.getGameResource(25, "werewolfkill_guardianwait_anim");
        if (action != null) {
          Log.i("TAG", "action type=" + action.getType() + " name=" + action.drawable);
          // image.setBackgroundDrawable(action.drawable);
          File[] files = new File[action.frameFile.size()];
          image.startImageFrame(
              new ImageFrameHandler.FileHandlerBuilder(action.frameFile.toArray(files))
                  .setLoop(true)
                  .setFps(action.fps)
                  .build());
        }
      }
    });

  }
}
