package com.mrwang.gifstudio;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();
    method1();
    assertEquals("com.mrwang.gifstudio", appContext.getPackageName());
  }

  private static void method1() {
    SparseArray<String> sparseArray=new SparseArray<>();
    sparseArray.put(10,"10");
    sparseArray.put(1,"1");
    sparseArray.put(5,"5");

    for (int i = 0; i < sparseArray.size(); i++) {
      int key = sparseArray.keyAt(i);
      System.out.println(" key="+key+" value="+sparseArray.get(key));
    }
  }
}
