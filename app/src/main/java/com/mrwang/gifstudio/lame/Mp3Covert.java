package com.mrwang.gifstudio.lame;

import android.media.AudioFormat;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;

import java.io.File;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/20
 * Time: 上午11:55
 */
public class Mp3Covert {
  //private static MediaExtractor mediaExtractor = new MediaExtractor();// 此类可分离视频文件的音轨和视频轨道

  private static MediaMetadataRetriever mmr = new MediaMetadataRetriever();;

  public static void processDir(String dir) {
    File file = new File(dir);
    if (file.exists() && file.isDirectory()) {
      for (File srcFile : file.listFiles()) {
        process(srcFile);
      }
    }
  }

  public static void process(File srcFile) {
    processBitRate(srcFile.getAbsolutePath());
    processSimpleRate(srcFile.getAbsolutePath());
  }

  public static void processBitRate(String path) {
    try {
      mmr.setDataSource(path);
      String bitRate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE); // 播放时长单位为毫秒
      Log.i("TAG", "比特率=" + bitRate);
      if (!"192000".equals(bitRate)) {
        Log.e("TAG", "不合法的音频文件 比特率不对 bitRate=" + bitRate + "  path=" + path);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void processSimpleRate(String srcPath) {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(srcPath);// 媒体文件的位置
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {// 遍历媒体轨道 此处我们传入的是音频文件，所以也就只有一条轨道
          MediaFormat format = mediaExtractor.getTrackFormat(i);
          // 比特率 声音中的比特率是指将模拟声音信号转换成数字声音信号后，单位时间内的二进制数据量，是间接衡量音频质量的一个指标
          format.setInteger(MediaFormat.KEY_BIT_RATE, AudioFormat.ENCODING_PCM_16BIT);
          // format.setInteger(MediaFormat.KEY_BIT_RATE, kBitRates[1]);
          // format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, minBufferSize);
          // format.setInteger(MediaFormat.KEY_SAMPLE_RATE, kSampleRates[3]);
          // format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, AudioFormat.CHANNEL_IN_STEREO);
          int simpleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
          Log.i("TAG", "采样率=" + simpleRate);
          Log.i("TAG", "比特率=" + format.getInteger(MediaFormat.KEY_BIT_RATE));
          Log.i("TAG", "音频通道=" + format.getInteger(MediaFormat.KEY_CHANNEL_COUNT));

          if (simpleRate != 44100) {
            Log.e("TAG", "不合法的音频文件 采样率不对 simpleRate=" + simpleRate + "  path=" + srcPath);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
