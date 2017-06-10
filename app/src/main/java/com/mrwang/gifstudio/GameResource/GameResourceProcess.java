package com.mrwang.gifstudio.GameResource;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.IntDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/7
 * Time: 下午2:36
 */
public class GameResourceProcess {
  public static final int AUDIO = 1;
  public static final int IMAGE = 2;
  public static final int IMAGE_FRAME = 3;

  @IntDef({AUDIO, IMAGE, IMAGE_FRAME})
  @Retention(RetentionPolicy.SOURCE)
  public @interface Type {}

  private static String filePath = Environment
      .getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.blinnnk.thor/files/" + "gameRes/";

  /**
   * 目录 FileUtils.GAME_RESOURCE_DIR;
   * 子目录 gameId
   *
   * 三种
   * 1.图片 png或者jpg
   * 2.序列帧 以_anim结尾 里面放了_num结尾的序列帧图片
   * 3.声音 直接播放
   *
   * 返回值
   * 图片 返回drawable
   * 序列帧 返回ImageFrameCustomView
   * 声音 返回路径
   *
   * @param gameTypeCode 游戏id
   * @param soundName 资源名称
   */
  public static GameResourceEntity getGameResource(int gameTypeCode, String soundName) {
    String rootFileName = filePath + gameTypeCode + File.separator + soundName;
    File file = new File(rootFileName);
    if (file.exists()) {
      if (file.isDirectory()) {
        return processFrameAnim(file);
      } else if (file.isFile()) {
        return processFile(file);
      }
    }
    return null;
  }

  private static GameResourceEntity processFile(File file) {
    GameResourceEntity entity = new GameResourceEntity();
    if (judgeType(file, ".png", ".jpg")) {
      entity.type = IMAGE;
      entity.file = file;
      entity.drawable = new BitmapDrawable(
          BitmapFactory.decodeFile(file.getAbsolutePath()));
    } else if (judgeType(file, ".mp3", ".pcm")) {
      entity.type = AUDIO;
      entity.file = file;
    }
    return entity;
  }

  private static boolean judgeType(File file, String... suffix) {
    for (String s : suffix) {
      if (file.getName().endsWith(s)) {
        return true;
      }
    }
    return false;
  }

  private static GameResourceEntity processFrameAnim(File file) {
    if (file.isDirectory() && judgeType(file, "_anim")) {
      GameResourceEntity entity = null;
      File[] desFiles = file.listFiles();
      for (File desFile : desFiles) {

        String name = desFile.getName();
        if (name.startsWith("res")) {

          //
          String[] split = name.split("_");
          int start = Integer.valueOf(split[1]);
          int end = Integer.valueOf(split[2]);
          int fps = Integer.valueOf(split[3]);


          List<File> frameFiles = new ArrayList<>();
          File[] files = desFile.listFiles();
          File[] tempFiles = new File[files.length];
          for (File diskFile : files) {
            if (diskFile.isFile() && judgeType(diskFile, ".png", ".jpg")) {
              String innerName = diskFile.getName();
              String s = innerName.split("\\.")[0];
              String substring = s.substring(s.lastIndexOf("_") + 1, s.length());
              int index = Integer.valueOf(substring) - start;
              if (index <= end) {
                tempFiles[index] = diskFile;
              }
            }
          }
          for (File tempFile : tempFiles) {
            if (tempFile != null) {
              frameFiles.add(tempFile);
            }
          }
          if (tempFiles.length != 0) {
            entity = new GameResourceEntity();
            entity.type = IMAGE_FRAME;
            entity.frameFile = frameFiles;
            entity.fps = fps;
          }
          return entity;
        }
      }
    }
    return null;
  }

  public static class GameResourceEntity {
    public @Type int type;
    public File file;// type为音频的时候才有
    public Drawable drawable;// type为图片的时候才有
    List<File> frameFile;// type为序列帧的时候才有
    public int fps;// type为序列帧的时候才有

    public int getType() {
      return type;
    }

    public File getFile() {
      return file;
    }

    public List<File> getFrameFile() {
      return frameFile;
    }

    public Drawable getDrawable() {
      return drawable;
    }
  }


}
