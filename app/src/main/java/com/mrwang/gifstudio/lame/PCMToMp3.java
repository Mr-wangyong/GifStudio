package com.mrwang.gifstudio.lame;

import android.media.AudioFormat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/12
 * Time: 下午1:50
 */
public class PCMToMp3 {

  private static final String TAG = "PCMToMp3";

  /**
   * 将PCM程序换成MP3
   */
  public static void pcmToMp3(String src, String des, ArrayList<byte[]> chunkPCMDataContainer,
      long audioLength) {
    try {
      final long totalAudioLen;
      final long totalDataLen;
      if (audioLength == 0) {
        File file = new File(src);
        totalAudioLen = file.length();
        totalDataLen = totalAudioLen + 36;
      } else {
        totalAudioLen = audioLength;
        totalDataLen = audioLength + 36;
      }
      int mSampleRate = 44100;
      int channels = AudioFormat.CHANNEL_IN_STEREO;
      long byteRate = 16 * mSampleRate * channels / 8;
      byte[] header = createHeader(totalAudioLen, totalDataLen, mSampleRate, channels, byteRate);
      RandomAccessFile fopen = fopen(des, header);
      for (byte[] bytes : chunkPCMDataContainer) {
        fwrite(fopen, bytes, 0, bytes.length);
      }
      fclose(fopen);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static RandomAccessFile fopen(String path, byte[] header) throws IOException {
    File f = new File(path);
    if (f.exists()) {
      f.delete();
    } else {
      File parentDir = f.getParentFile();
      if (!parentDir.exists()) {
        parentDir.mkdirs();
      }
    }
    RandomAccessFile file = new RandomAccessFile(f, "rw");
    file.write(header);
    Log.d(TAG, "wav path: " + path);
    return file;
  }

  private static void fwrite(RandomAccessFile file, byte[] data, int offset, int size)
      throws IOException {
    file.write(data, offset, size);
    Log.d(TAG, "fwrite: " + size);
  }

  private static void fclose(RandomAccessFile file) throws IOException {
    try {
      file.seek(4); // riff chunk size
      file.writeInt(Integer.reverseBytes((int) (file.length() - 8)));

      file.seek(40); // data chunk size
      file.writeInt(Integer.reverseBytes((int) (file.length() - 44)));

      Log.d(TAG, "wav size: " + file.length());

    } finally {
      file.close();
    }
  }


  /**
   * 加入wav文件头
   */
  private static byte[] createHeader(long totalAudioLen,
      long totalDataLen, long longSampleRate, int channels, long byteRate)
      throws IOException {
    byte[] header = new byte[44];
    header[0] = 'R'; // RIFF/WAVE header
    header[1] = 'I';
    header[2] = 'F';
    header[3] = 'F';
    header[4] = (byte) (totalDataLen & 0xff);
    header[5] = (byte) ((totalDataLen >> 8) & 0xff);
    header[6] = (byte) ((totalDataLen >> 16) & 0xff);
    header[7] = (byte) ((totalDataLen >> 24) & 0xff);
    header[8] = 'W'; // WAVE
    header[9] = 'A';
    header[10] = 'V';
    header[11] = 'E';
    header[12] = 'f'; // 'fmt ' chunk
    header[13] = 'm';
    header[14] = 't';
    header[15] = ' ';
    header[16] = 16; // 4 bytes: size of 'fmt ' chunk
    header[17] = 0;
    header[18] = 0;
    header[19] = 0;
    header[20] = 1; // format = 1
    header[21] = 0;
    header[22] = (byte) channels;
    header[23] = 0;
    header[24] = (byte) (longSampleRate & 0xff);
    header[25] = (byte) ((longSampleRate >> 8) & 0xff);
    header[26] = (byte) ((longSampleRate >> 16) & 0xff);
    header[27] = (byte) ((longSampleRate >> 24) & 0xff);
    header[28] = (byte) (byteRate & 0xff);
    header[29] = (byte) ((byteRate >> 8) & 0xff);
    header[30] = (byte) ((byteRate >> 16) & 0xff);
    header[31] = (byte) ((byteRate >> 24) & 0xff);
    header[32] = (byte) (2 * 16 / 8); // block align
    header[33] = 0;
    header[34] = 16; // bits per sample
    header[35] = 0;
    header[36] = 'd'; // data
    header[37] = 'a';
    header[38] = 't';
    header[39] = 'a';
    header[40] = (byte) (totalAudioLen & 0xff);
    header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
    header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
    header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
    return header;
  }


}
