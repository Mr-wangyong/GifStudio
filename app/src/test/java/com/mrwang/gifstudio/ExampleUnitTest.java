package com.mrwang.gifstudio;

import android.util.SparseArray;

import org.junit.Test;

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
//    Type sparseArrayType = new TypeToken<SparseArray<SeatUser>>() {}.getType();
//    Gson sparseGson = new GsonBuilder()
//        .registerTypeAdapter(sparseArrayType, new SparseArrayTypeAdapter<>(SeatUser.class))
//        .create();
//    SparseArray<SeatUser> sparseArray = sparseGson.fromJson(json, SparseArray.class);
//    System.out.print("sparseArray="+sparseArray.toString());
    method1();
  }

  private static void method1() {
    SparseArray<String> sparseArray=new SparseArray<>();
    sparseArray.put(1,"1");
    sparseArray.put(5,"5");
    sparseArray.put(10,"10");

    for (int i = 0; i < sparseArray.size(); i++) {
      int key = sparseArray.keyAt(i);
      System.out.println(" key="+key+" value="+sparseArray.get(key));
    }
  }
}