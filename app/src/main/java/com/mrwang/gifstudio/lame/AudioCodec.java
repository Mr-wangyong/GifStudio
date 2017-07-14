package com.mrwang.gifstudio.lame;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/12
 * Time: 下午2:19
 */
public class AudioCodec {

  private static final String TAG = "AudioDecode";
  private String srcPath;// 语音本地路径
  private MediaCodec mediaDecode;
  private MediaExtractor mediaExtractor;
  private ByteBuffer[] decodeInputBuffers;
  private ByteBuffer[] decodeOutputBuffers;
  private MediaCodec.BufferInfo decodeBufferInfo;
  private ArrayList<byte[]> chunkPCMDataContainer;// PCM数据
  private OnCompleteListener onCompleteListener;
  private boolean codeOver = false;// 解码结束
  private Thread decoderThread;
  private String targePath;
  private long audioLength;
  private boolean isSupportHardWare;

  public static AudioCodec newInstance() {
    return new AudioCodec();
  }

  /** * 设置要读取的文件位置 * * @param srcPath */
  public void setPath(String srcPath, String targePath) {
    this.srcPath = srcPath;
    this.targePath = targePath;
  }

  /** * 准备工作 */
  public void prepare() {
    if (srcPath == null || "".equals(srcPath)) {// 其实这个路径在使用转码前就已经判断本地文件是否存在了，这个判断可有可无
      throw new IllegalArgumentException("srcPath can't be null");
    }
    chunkPCMDataContainer = new ArrayList<>();
    initMediaDecode();// 解码器
  }

  /** * 初始化解码器 */
  private void initMediaDecode() {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        mediaExtractor = new MediaExtractor();// 此类可分离视频文件的音轨和视频轨道
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        final int kSampleRates[] = {8000, 11025, 22050, 44100, 48000, 24000};
        // MediaFormat.createAudioFormat(
        // MediaFormat.KEY_PCM_ENCODING, kSampleRates[3], 2);
        // 比特率 声音中的比特率是指将模拟声音信号转换成数字声音信号后，单位时间内的二进制数据量，是间接衡量音频质量的一个指标
        final int kBitRates[] = {64000, 96000, 128000};
        int minBufferSize = AudioRecord.getMinBufferSize(kSampleRates[3],
            AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        mediaExtractor.setDataSource(srcPath);// 媒体文件的位置
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {// 遍历媒体轨道 此处我们传入的是音频文件，所以也就只有一条轨道
          MediaFormat format = mediaExtractor.getTrackFormat(i);
          // 比特率 声音中的比特率是指将模拟声音信号转换成数字声音信号后，单位时间内的二进制数据量，是间接衡量音频质量的一个指标
          format.setInteger(MediaFormat.KEY_BIT_RATE, AudioFormat.ENCODING_PCM_16BIT);
          try {
            audioLength = format.getLong(MediaFormat.KEY_DURATION);
          } catch (Exception e) {
            e.printStackTrace();
          }
          // format.setInteger(MediaFormat.KEY_BIT_RATE, kBitRates[1]);
          // format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, minBufferSize);
          // format.setInteger(MediaFormat.KEY_SAMPLE_RATE, kSampleRates[3]);
          // format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, AudioFormat.CHANNEL_IN_STEREO);
          String mime = format.getString(MediaFormat.KEY_MIME);
          if (mime.startsWith("audio")) {// 获取音频轨道
            mediaExtractor.selectTrack(i);// 选择此音频轨道
            mediaDecode = MediaCodec.createDecoderByType(mime);// 创建Decode解码器
            mediaDecode.configure(format, null, null, 0);
            break;
          }
        }
        if (mediaDecode == null) {
          Log.e(TAG, "create mediaDecode failed");
          return;
        }
        mediaDecode.start();// 启动MediaCodec ，等待传入数据
        decodeInputBuffers = mediaDecode.getInputBuffers();// MediaCodec在此ByteBuffer[]中获取输入数据
        decodeOutputBuffers = mediaDecode.getOutputBuffers();// MediaCodec将解码后的数据放到此ByteBuffer[]中
                                                             // 我们可以直接在这里面得到PCM数据
        decodeBufferInfo = new MediaCodec.BufferInfo();// 用于描述解码得到的byte[]数据的相关信息
        showLog("buffers:" + decodeInputBuffers.length);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  /** * 开始转码 * 音频数据 解码成PCM，获取到PCM数组 */
  public DecodeRunnable startAsync() {
    return new DecodeRunnable();
  }

  /** * 将PCM数据存入{@link #chunkPCMDataContainer} * * @param pcmChunk PCM数据块 */
  private void putPCMData(byte[] pcmChunk) {
    synchronized (AudioCodec.class) {// 记得加锁
      chunkPCMDataContainer.add(pcmChunk);
    }
  }

  /** * 在Container中{@link #chunkPCMDataContainer}取出PCM数据 * * @return PCM数据块 */
  private byte[] getPCMData() {
    synchronized (AudioCodec.class) {// 记得加锁
      if (chunkPCMDataContainer.isEmpty()) {
        return null;
      }

      byte[] pcmChunk = chunkPCMDataContainer.get(0);// 每次取出index 0 的数据
      chunkPCMDataContainer.remove(pcmChunk);// 取出后将此数据remove掉 既能保证PCM数据块的取出顺序 又能及时释放内存
      return pcmChunk;
    }
  }


  /** * 解码{@link #srcPath}音频文件 得到PCM数据块 * * @return 是否解码完所有数据 */
  private void srcAudioFormatToPCM() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
      if (decodeInputBuffers != null) {
        try {
          for (int i = 0; i < decodeInputBuffers.length - 1; i++) {
            int inputIndex = 0;// 获取可用的inputBuffer -1代表一直等待，0表示不等待 建议-1,避免丢帧
            inputIndex = mediaDecode.dequeueInputBuffer(-1);
            if (inputIndex < 0) {
              codeOver = true;
              return;
            }
            ByteBuffer inputBuffer = decodeInputBuffers[inputIndex];// 拿到inputBuffer
            inputBuffer.clear();// 清空之前传入inputBuffer内的数据
            int sampleSize = mediaExtractor.readSampleData(inputBuffer, 0);// MediaExtractor读取数据到inputBuffer中
            if (sampleSize < 0) {// 小于0 代表所有数据已读取完成
              codeOver = true;
            } else {
              mediaDecode.queueInputBuffer(inputIndex, 0, sampleSize, 0, 0);// 通知MediaDecode解码刚刚传入的数据
              mediaExtractor.advance();// MediaExtractor移动到下一取样处
            }
          }

          // 获取解码得到的byte[]数据 参数BufferInfo上面已介绍 10000同样为等待时间 同上-1代表一直等待，0代表不等待。此处单位为微秒
          // 此处建议不要填-1 有些时候并没有数据输出，那么他就会一直卡在这 等待
          int outputIndex = mediaDecode.dequeueOutputBuffer(decodeBufferInfo, 1000000);

          ByteBuffer outputBuffer;
          byte[] chunkPCM;
          while (outputIndex >= 0) {// 每次解码完成的数据不一定能一次吐出 所以用while循环，保证解码器吐出所有数据
            outputBuffer = decodeOutputBuffers[outputIndex];// 拿到用于存放PCM数据的Buffer
            chunkPCM = new byte[decodeBufferInfo.size];// BufferInfo内定义了此数据块的大小
            outputBuffer.get(chunkPCM);// 将Buffer内的数据取出到字节数组中
            outputBuffer.clear();// 数据取出后一定记得清空此Buffer MediaCodec是循环使用这些Buffer的，不清空下次会得到同样的数据
            putPCMData(chunkPCM);// 自己定义的方法，供编码器所在的线程获取数据,下面会贴出代码
            mediaDecode.releaseOutputBuffer(outputIndex, false);// 此操作一定要做，不然MediaCodec用完所有的Buffer后
                                                                // 将不能向外输出数据
            outputIndex = mediaDecode.dequeueOutputBuffer(decodeBufferInfo, 10000);// 再次获取数据，如果没有数据输出则outputIndex=-1
                                                                                   // 循环结束
          }

          if (codeOver) {
            PCMToMp3.pcmToMp3(srcPath, targePath, chunkPCMDataContainer, audioLength);
            if (onCompleteListener != null) {
              onCompleteListener.completed(targePath);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          codeOver = true;
          PCMToMp3.pcmToMp3(srcPath, targePath, chunkPCMDataContainer, audioLength);
          if (onCompleteListener != null) {
            onCompleteListener.completed(targePath);
          }
        }
      }
    }
  }

  /** * android 5.0以上 */
  private void srcAudioFormatToPCMHigherApi() {
    if (android.os.Build.VERSION.SDK_INT >= 21) {
      boolean sawOutputEOS = false;
      final long kTimeOutUs = 10000;
      long presentationTimeUs = 0;
      while (!sawOutputEOS) {
        try {
          int inputIndex = mediaDecode.dequeueInputBuffer(-1);
          if (inputIndex >= 0) {
            ByteBuffer inputBuffer = mediaDecode.getInputBuffer(inputIndex);
            if (inputBuffer != null) {
              int sampleSize = mediaExtractor.readSampleData(inputBuffer, 0);
              if (sampleSize < 0) {// 小于0 代表所有数据已读取完成
                sawOutputEOS = true;
                codeOver = true;
                break;
              } else {
                presentationTimeUs = mediaExtractor.getSampleTime();
                mediaDecode.queueInputBuffer(inputIndex, 0, sampleSize, presentationTimeUs, 0);// 通知MediaDecode解码刚刚传入的数据
                mediaExtractor.advance();
              }
            }
          } else {
            sawOutputEOS = true;
            codeOver = true;
          }
          int outputIndex = mediaDecode.dequeueOutputBuffer(decodeBufferInfo, kTimeOutUs);
          ByteBuffer outputBuffer;// = mediaDecode.getOutputBuffer(outputIndex);//
                                  // 拿到用于存放PCM数据的Buffer
          while (outputIndex >= 0) {
            outputBuffer = mediaDecode.getOutputBuffer(outputIndex);
            boolean doRender = (decodeBufferInfo.size != 0);
            if (doRender && outputBuffer != null) {
              outputBuffer.position(decodeBufferInfo.offset);
              outputBuffer.limit(decodeBufferInfo.offset + decodeBufferInfo.size);
              byte[] chunkPCM = new byte[decodeBufferInfo.size];// BufferInfo内定义了此数据块的大小
              outputBuffer.get(chunkPCM);
              outputBuffer.clear();// 数据取出后一定记得清空此Buffer MediaCodec是循环使用这些Buffer的，不清空下次会得到同样的数据
              putPCMData(chunkPCM);// 自己定义的方法，供编码器所在的线程获取数据,下面会贴出代码
              mediaDecode.releaseOutputBuffer(outputIndex, false);// 此操作一定要做，不然MediaCodec用完所有的Buffer后将不能向外输出数据
              outputIndex = mediaDecode.dequeueOutputBuffer(decodeBufferInfo, kTimeOutUs);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          sawOutputEOS = true;
          codeOver = true;
        }
      }
      if (codeOver) {
        PCMToMp3.pcmToMp3(srcPath, targePath, chunkPCMDataContainer, audioLength);
        if (onCompleteListener != null) {
          onCompleteListener.completed(targePath);
        }
      }
    }
  }

  /** * 释放资源 */
  public void release() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
      try {
        if (decoderThread != null && decoderThread.isAlive()) {
          decoderThread.interrupt();
        }
        codeOver = true;
        if (mediaDecode != null) {
          mediaDecode.stop();
          mediaDecode.release();
          mediaDecode = null;
        }
        if (mediaExtractor != null) {
          mediaExtractor.release();
          mediaExtractor = null;
        }
        if (onCompleteListener != null) {
          onCompleteListener = null;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }



  /** * 解码线程 */
  private class DecodeRunnable implements Runnable {

    @Override
    public void run() {
      decode();
    }
  }

  public void decode() {
    Log.i("TAG", "开始解码 name=" + srcPath + " thread=" + Thread.currentThread().getName());
    while (!codeOver) {
      if (Build.VERSION.SDK_INT >= 21) {
        srcAudioFormatToPCMHigherApi();
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        srcAudioFormatToPCM();
      }
    }
    if (chunkPCMDataContainer == null || chunkPCMDataContainer.isEmpty()) {
      Log.i("TAG", "解码失败 不支持的格式 name=" + srcPath);
    }

    if (onCompleteListener != null) {
      onCompleteListener.onFinish();
    }
    codeOver = false;
  }

  /** * 解码完成回调接口 */
  public interface OnCompleteListener {
    void completed(String targePath);

    void onFinish();
  }

  /** * 设置转码完成监听器 * @param onCompleteListener 监听器 */
  public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
    this.onCompleteListener = onCompleteListener;
  }

  private void showLog(String msg) {
    Log.e("AudioCodec", msg);
  }


  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  private void checkMediaDecoder() {
    int numCodecs = MediaCodecList.getCodecCount();
    MediaCodecInfo mediaCodecInfo = null;
    for (int i = 0; i < numCodecs && mediaCodecInfo == null; i++) {
      MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
      if (info.isEncoder()) {
        continue;
      }
      String[] types = info.getSupportedTypes();
      boolean found = false;
      for (int j = 0; j < types.length && !found; j++) {
        if (types[j].equals("video/avc")) {
          System.out.println("found");
          found = true;
          isSupportHardWare = found;
          return;
        }
      }
      if (!found) {
        continue;
      }
      mediaCodecInfo = info;
    }

    if (mediaCodecInfo != null) {
      int[] colorFormats = mediaCodecInfo.getCapabilitiesForType("video/avc").colorFormats;
      if (colorFormats!=null){
        for (int colorFormat : colorFormats) {
          showLog("");
        }
      }
    }

  }
}
