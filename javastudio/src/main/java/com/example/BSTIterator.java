package com.example;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/1
 * Time: 上午10:49
 */



/**
 * 
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the
 * root node of a BST.
 * 
 * *在二进制搜索树上实现迭代器（BST）。您的迭代器将被初始化
 * 一个布尔根节点
 * 
 * Calling next() will return the next smallest number in the BST.
 * 调用next()将返回在BST下最小的数
 *
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the
 * height of the tree.
 *
 * 注：next()和hasnext()应该运行在平均O（1）时间，使用O（h）的内存，其中H为
 * 树的高度。
 *
 *
 * Credits:
 * Special thanks to @ts for adding this problem and creating all test cases.
 *
 * 
 * Subscribe to see which companies asked this question.
 * Definition for binary tree
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

public class BSTIterator {

  public BSTIterator(TreeNode root) {

  }

  /** @return whether we have a next smallest number */
  public boolean hasNext() {
    return false;
  }

  /** @return the next smallest number */
  public int next() {
    return 0;
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */
