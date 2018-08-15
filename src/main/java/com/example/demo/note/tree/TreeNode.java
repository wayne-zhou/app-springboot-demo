package com.example.demo.note.tree;

/**
 * Created by zhouwei on 2018/8/2
 * 节点对象
 **/
public class TreeNode<T> {

  //数据域
  private T  data;

  //权值(Huffman树用)
  private Double weight;

  //双亲指针
  private TreeNode parent;

  //左孩子指针
  private TreeNode left;

  //右孩子指针
  private TreeNode right;

    public TreeNode() {}

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(T data, TreeNode parent, TreeNode left, TreeNode right) {
        this.data = data;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public TreeNode(T data, Double weight) {
        this.data = data;
        this.weight = weight;
    }

    public TreeNode(T data, Double weight, TreeNode left, TreeNode right) {
        this.data = data;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
