package com.mrwang.gifstudio.lame;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/12
 * Time: 下午2:20
 */
public class PMCToMP3Test {

  public void test() {
    final String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    final AudioCodec audioCodec = AudioCodec.newInstance();
    audioCodec.setPath(path + File.separator + "f.mp3", path + File.separator + "e.wav");
    audioCodec.prepare();
    audioCodec.setOnCompleteListener(new AudioCodec.OnCompleteListener() {
      @Override
      public void completed() {
        Log.i("TAG", "success");
      }
    });
    Thread thread = new Thread(audioCodec.startAsync());
    thread.start();
  }

  public void processMedia() {
    new Thread(){
      @Override
      public void run() {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        // String srcPath=path + File.separator + "blink_thor/audio/08bbf47bcf1846f11d2729de2201cabc";
        // String srcPath=path + File.separator + "c.mp3";
        String srcPath = path + File.separator + "blink_thor/gamesound/sound";

        File file = new File(srcPath);
        for (File file1 : file.listFiles()) {
          if (file1.isDirectory()) {
            Collection<File> files = listFiles(file1);
            for (File file2 : files) {
              Log.i("TAG", "srcPath=" + file2.getAbsolutePath());
              Mp3Covert.process(file2);
            }
          }
        }
      }
    }.start();
  }



  static Collection<File> listFiles(File root) {
    List<File> files = new ArrayList<>();
    listFiles(files, root);
    return files;
  }

  static void listFiles(List<File> files, File dir) {
    File[] listFiles = dir.listFiles();
    for (File f : listFiles) {
      if (f.isFile() && f.getName().endsWith(".mp3")) {
        files.add(f);
      } else if (f.isDirectory()) {
        listFiles(files, f);
      }
    }
  }


}
