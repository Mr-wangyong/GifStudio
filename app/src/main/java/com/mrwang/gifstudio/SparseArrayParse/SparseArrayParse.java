package com.mrwang.gifstudio.SparseArrayParse;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/13
 * Time: 下午5:13
 */
public class SparseArrayParse {

  private  String json =
      "{\"sm\":{\"0\":{\"uid\":1000000735,\"nick\":\"啦啦啦\",\"userId\":\"1000000735\",\"gender\":\"1\",\"grade\":\"1\",\"avatar\":\"http://hunger.blinnnk.com/FiKe4NSwdAy-LZF5BbcLDivXo--q\"}}}";

  public void parse() {
    Type sparseArrayType = new TypeToken<SparseArray<SeatUser>>() {}.getType();
    Gson sparseGson = new GsonBuilder()
        .registerTypeAdapter(sparseArrayType, new SparseArrayTypeAdapter<>(SeatUser.class))
        .create();
    try {
      JSONObject jsonObject=new JSONObject(json);
      SparseArray<SeatUser> sparseArray = sparseGson.fromJson(jsonObject.get("sm").toString(),  sparseArrayType);
      System.out.print("sparseArray="+sparseArray.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }



  }
}
