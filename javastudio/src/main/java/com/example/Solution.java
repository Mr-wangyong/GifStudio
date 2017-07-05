package com.example;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Given the coordinates of four points in 2D space, return whether the four points could construct
 * a square.
 * 
 * The coordinate (x,y) of a point is represented by an integer array with two integers.
 * 
 * Example:
 * Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 * Output: True
 * Note:
 * 
 * All the input integers are in the range [-10000, 10000].
 * A valid square has four equal sides with positive length and four equal angles (90-degree
 * angles).
 * Input points have no order.
 *
 *
 * 输入四个点(范围(-10000,10000))
 * 判断是否是方形(之间夹角味90度)
 * 输入点没有顺序
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/6/27
 * Time: 上午10:20
 */
public class Solution {
  /**
   * @param root the root of binary tree
   * @param target an integer
   * @return all valid paths
   */
  public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
    // Write your code here
    List<List<Integer>> lists = new ArrayList<>();


    int sum = 0;
    TreeNode node = root;
    sum=node.val;


    return lists;
  }

  private int sum=0;

  public void sum(TreeNode node){

  }


  public static void main(String[] args){
    TreeNode root=new TreeNode(10);
    root.left=new TreeNode(9);
    root.right=new TreeNode(8);

    root.left.left=new TreeNode(7);

    root.right.right=new TreeNode(6);

    root.left.left.left=new TreeNode(5);

    root.right.right.right=new TreeNode(4);

    root.left.left.left.left=new TreeNode(3);

    root.right.right.right.right=new TreeNode(2);

    findAll(root);
  }


  private static void findAll(TreeNode node) {
    System.out.println(node.val);

    if (node.left != null) {
      findAll(node.left);
    }

    if (node.right != null) {
      findAll(node.right);
    }
  }


  public static class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    }
  }
}
