package com.mrwang.gifstudio.algorithm;

/**
 * 学习的三步骤
 * 模仿
 * 探究
 * 超越
 *
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/31
 * Time: 下午1:52
 */
public class TestStack {
  private Node top;
  private Integer min;

  public TestStack() {

  }

  public void push(int x) {
    min = (min == null || min > x) ? x : min;
    if (top == null) {
      top = new Node(x);
    } else {
      Node node = new Node(x);
      node.next = top;
      top = node;
    }
  }

  public void pop() {
    if (top != null) {
      int index = top.index;
      top = top.next;
      if (index == min) {
        min = findMin();
      }
    } else {
      throw new StackOverflowError();
    }
  }

  public int top() {
    if (top != null) {
      return top.index;
    } else {
      throw new StackOverflowError();
    }
  }

  public int getMin() {
    return min;
  }

  private Integer findMin() {
    if (top != null) {
      min = top.index;
      Node next = top.next;
      while (next != null) {
        Node node = next;
        if (min > node.index) {
          min = node.index;
        }
        next = node.next;
      }
    } else {
      min = null;
    }
    return min;
  }

  public static class Node {
    private int index;
    private Node next;

    public Node(int index) {
      this.index = index;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (top != null) {
      builder.append(top.index).append(",");
      Node next = top.next;
      while (next != null) {
        Node node = next;
        builder.append(node.index).append(",");
        next = node.next;
      }
    }
    return builder.toString();
  }
}
