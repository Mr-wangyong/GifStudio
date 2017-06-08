package com.mrwang.gifstudio.algorithm;

import java.util.Queue;

/**
 * 题目:Design a stack that supports push, pop, top, and retrieving the minimum element in constant
 * time.
 * //设计一个支持push，pop，top和在一定时间内检索最小元素的堆栈
 * push(x) — Push element x onto stack.
 * //将元素x推入堆叠
 * pop() — Removes the element on top of the stack.
 * //删除堆栈顶部的元素
 * top() — Get the top element.
 * //获取栈顶元素
 * getMin() — Retrieve the minimum element in the stack.
 * //检索堆栈中的最小元素
 * Example:
 * 
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); —> Returns -3.
 * minStack.pop();
 * minStack.top(); —> Returns 0.
 * minStack.getMin(); —> Returns -2.
 * 
 * 提示：提交代码后，需要用简洁的语言解释一下代码思路~ 谢谢
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/5/31
 * Time: 上午10:13
 */
public class MinStack<T extends Comparable<T>> {
  private Queue<T> queue;
  private T min;

  public MinStack(Queue<T> queue) {
    this.queue = queue;
  }

  public void push(T element) {
    queue.add(element);
    if (min == null) {
      min = element;
    } else {
      min = element.compareTo(min) > 0 ? min : element;
    }
  }

  public void pop() {
    queue.remove();
  }

  public T top() {
    return queue.peek();
  }

  public T getMin() {
    return min;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (T t : queue) {
      builder.append(t.toString()).append(",");
    }
    return builder.toString();
  }
}
