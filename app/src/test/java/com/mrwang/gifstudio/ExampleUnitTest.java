package com.mrwang.gifstudio;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mrwang.gifstudio.SparseArrayParse.SeatUser;
import com.mrwang.gifstudio.SparseArrayParse.SparseArrayTypeAdapter;

import org.junit.Test;

import java.lang.reflect.Type;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test
  public void addition_isCorrect() throws Exception {

  }

  private static String json =
      "{\"sm\":{\"0\":{\"uid\":1000000735,\"nick\":\"啦啦啦\",\"userId\":\"1000000735\",\"gender\":\"1\",\"grade\":\"1\",\"avatar\":\"http://hunger.blinnnk.com/FiKe4NSwdAy-LZF5BbcLDivXo--q\"}}}";


  public static void main(String[] args){
    Type sparseArrayType = new TypeToken<SparseArray<SeatUser>>() {}.getType();
    Gson sparseGson = new GsonBuilder()
        .registerTypeAdapter(sparseArrayType, new SparseArrayTypeAdapter<>(SeatUser.class))
        .create();
    SparseArray<SeatUser> sparseArray = sparseGson.fromJson(json, SparseArray.class);
    System.out.print("sparseArray="+sparseArray.toString());
  }
}