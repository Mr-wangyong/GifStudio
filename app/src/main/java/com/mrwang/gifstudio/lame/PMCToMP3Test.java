package com.mrwang.gifstudio.lame;

import android.os.Environment;
import android.util.Log;

import com.mrwang.imageframe.WorkHandler;

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
  private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
  private File[] files;

  public void test() {


    final AudioCodec audioCodec = AudioCodec.newInstance();
    audioCodec.setPath(
        path + File.separator + "android/data/com.blinnnk.thor/files/gameRes/20/out_20.mp3",
        path + File.separator + "e.wav");
    audioCodec.prepare();
    audioCodec.setOnCompleteListener(new AudioCodec.OnCompleteListener() {
      @Override
      public void completed(String desPath) {
        Log.i("TAG", "success");
      }

      @Override
      public void onFinish() {

      }
    });
    Thread thread = new Thread(audioCodec.startAsync());
    thread.start();
  }

  public void test2() {
    final AudioCodec audioCodec = AudioCodec.newInstance();
    final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    // Thread thread = new Thread(audioCodec.startAsync());
    WorkHandler handler = new WorkHandler();
    File file = new File(path + File.separator + "android/data/com.blinnnk.thor/files/gameRes/20");
    if (file.isDirectory() && file.listFiles() != null) {
      files = file.listFiles();
      process(files[index], audioCodec, handler);
    }
  }

  private int index;

  public void process(final File srcFile, final AudioCodec audioCodec, final WorkHandler handler) {
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Log.i("TAG", "onFinish srcFile=" + srcFile.getAbsolutePath());
        if (srcFile.isFile() && srcFile.getName().endsWith(".mp3")) {
          audioCodec.setPath(srcFile.getAbsolutePath(),
              path + File.separator + srcFile.getName().replace(".mp3", ".pcm"));
          audioCodec.prepare();
          audioCodec.setOnCompleteListener(new AudioCodec.OnCompleteListener() {
            @Override
            public void completed(String desPath) {
              File file = new File(desPath);

              Log.i("TAG", "success targePath=" + desPath + " file exit=" + file.exists());
            }

            @Override
            public void onFinish() {
              Log.i("TAG", "onFinish");
              next(audioCodec, handler);
            }
          });
          audioCodec.decode();
        } else {
          next(audioCodec, handler);
        }
      }
    }, 500);

  }

  private void next(AudioCodec audioCodec, WorkHandler handler) {
    index++;
    if (index < files.length) {
      Log.i("TAG", "onFinish start");
      process(files[index], audioCodec, handler);
    }
  }

  public void processMedia() {
    new Thread() {
      @Override
      public void run() {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        // String srcPath=path + File.separator +
        // "blink_thor/audio/08bbf47bcf1846f11d2729de2201cabc";
        // String srcPath=path + File.separator + "c.mp3";
        String srcPath = path + "/Android/data/com.blinnnk.thor/files/gameRes/20/out_20.mp3";

        File file = new File(srcPath);
        Mp3Covert.process(file);
        // for (File file1 : file.listFiles()) {
        // if (file1.isDirectory()) {
        // Collection<File> files = listFiles(file1);
        // for (File file2 : files) {
        // Log.i("TAG", "srcPath=" + file2.getAbsolutePath());
        // Mp3Covert.process(file2);
        // }
        // }
        // }
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
