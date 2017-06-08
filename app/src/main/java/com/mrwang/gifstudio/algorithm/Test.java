package com.mrwang.gifstudio.algorithm;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/31
 * Time: 上午10:27
 */
public class Test {
  public static void main(String[] args) {

    TestStack minStack=new TestStack();

    minStack.push(2147483646);
    minStack.push(2147483646);
    minStack.push(2147483647);

    minStack.top();
    minStack.pop();
    minStack.getMin();

    minStack.pop();
    minStack.getMin();
    minStack.pop();
    minStack.push(2147483647);
    int min = minStack.getMin();

    System.out.println("minStack min=" + min);
  }


}
