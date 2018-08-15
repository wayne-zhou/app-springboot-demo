package com.example.demo.note.tree;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhouwei on 2018/8/2
 **/
public class BinaryTree<E> {

    //根节点
    private TreeNode root;

    public BinaryTree() {
        this.root = new TreeNode();
    }

    public BinaryTree(E data) {
        this.root = new TreeNode(data);
    }

    /**
     * 判断二叉树是否为空
     */
    public boolean empty(){
        return root.getData() == null;
    }

    /**
     * 获取根节点
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * 获取节点数
     */
    public int size(){
        return size(root);
    }

    private int size(TreeNode node){
        if(node == null){
            return 0;
        }
        return 1 + size(root.getLeft()) + size(root.getRight());
    }

    /**
     * 获取深度
     */
    public int deep(){
        return deep(root);
    }

    private int deep(TreeNode node){
        if(node == null){
            return 0;
        }
        int leftDeep = deep(node.getLeft());
        int rightDeep = deep(node.getRight());
        // 记录其所有左、右子树中较大的深度
        int max = leftDeep > rightDeep ? leftDeep : rightDeep;
        // 返回其左右子树中较大的深度 + 1
        return max + 1;
    }

    /**
     * 前序遍历
     */
    public List<E> preIterator(){
        List<E> list = new ArrayList<>();
        preIterator(root, list);
        return list;
    }

    private void preIterator(TreeNode<E> node, List<E> list){
        if(node == null){
            return;
        }
        //先根节点
        list.add(node.getData());
        //左子树
        preIterator(node.getLeft(), list);
        //右子树
        preIterator(node.getRight(), list);
    }

    /**
     * 中序遍历
     */
    public List<E> inIterator(){
        List<E> list = new ArrayList<>();
        inIterator(root, list);
        return list;
    }

    private void inIterator(TreeNode<E> node, List<E> list){
        if(node == null){
            return;
        }
        //左子树
        inIterator(node.getLeft(), list);
        //先根节点
        list.add(node.getData());
        //右子树
        inIterator(node.getRight(), list);
    }

    /**
     * 中序遍历
     */
    public List<E> postIterator(){
        List<E> list = new ArrayList<>();
        postIterator(root, list);
        return list;
    }

    private void postIterator(TreeNode<E> node, List<E> list){
        if(node == null){
            return;
        }
        //左子树
        postIterator(node.getLeft(), list);
        //右子树
        postIterator(node.getRight(), list);
        //先根节点
        list.add(node.getData());
    }

    /**
     * 层序遍历
     */
    public List<E> breadthIterator(){
        List<E> list = new ArrayList<>();
        LinkedBlockingQueue<TreeNode<E>> queue = new LinkedBlockingQueue();
        queue.offer(root);
        while (!queue.isEmpty()){
            TreeNode<E> node = queue.poll();
            list.add(node.getData());
            if(node.getLeft() != null){
                queue.offer(node.getLeft());
            }
            if(node.getRight() != null){
                queue.offer(node.getRight());
            }
        }
        return list;
    }

    /**
     * 为指定节点添加子节点
     * 子节点存在则更新数据
     */
    public TreeNode addNode(TreeNode parent, E data, boolean isLeft){
        if(parent == null){
            return null;
        }

        TreeNode node = isLeft ? parent.getLeft() : parent.getRight();
        if(node == null){
            node = new TreeNode(data);
            node.setParent(parent);
            if(isLeft){
                parent.setLeft(node);
            }else{
                parent.setRight(node);
            }
        }else{
            node.setData(data);
        }

        return node;
    }

    /**
     * 按照前序的数据创建二叉树
     * 空节点用null占位(扩展二叉树)
     * @param dataList
     */
    public void createTreeByPre(List<E> dataList){
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        createTreeByPre(dataList, new AtomicInteger(0));
    }

    private TreeNode createTreeByPre(List<E> dataList, AtomicInteger index){
        TreeNode node = null;
        E data = dataList.get(index.get());
        if(data == null){
            return node;
        }

        node = index.get() == 0 ? root : new TreeNode();
        node.setData(data);
        index.addAndGet(1);
        node.setLeft(createTreeByPre(dataList, index));
        index.addAndGet(1);
        node.setRight(createTreeByPre(dataList, index));
        return node;
    }

    /**
     * 按照前序和中序创建二叉树
     */
    public void createTreeByPreAndIn(List<E> preList, List<E> inList){
        createTreeByPreAndIn(preList, inList, root);
    }

    private TreeNode createTreeByPreAndIn(List<E> preList, List<E> inList, TreeNode root){
        if(CollectionUtils.isEmpty(preList) || CollectionUtils.isEmpty(inList)){
            return null;
        }

        E rootData = preList.get(0);
        TreeNode node = root  != null ? root : new TreeNode();
        node.setData(rootData);

        //判断根节点在中序队列中的位置
        int i = 0;
        for(;i < inList.size(); i++){
            if(inList.get(i).equals(rootData)) {
                break;
            }
        }

        node.setLeft(createTreeByPreAndIn(preList.subList(1, i+1), inList.subList(0, i), null));
        node.setRight(createTreeByPreAndIn(preList.subList(i+1, preList.size()), inList.subList(i+1, inList.size()), null));
        return node;
    }

    /**
     * 按照后序和中序创建二叉树
     */
    public void createTreeByPostAndIn(List<E> postList, List<E> inList){
        createTreeByPostAndIn(postList, inList, root);
    }

    private TreeNode createTreeByPostAndIn(List<E> postList, List<E> inList, TreeNode root){
        if(CollectionUtils.isEmpty(postList) || CollectionUtils.isEmpty(inList)){
            return null;
        }

        E rootData = postList.get(postList.size()-1);
        TreeNode node = root  != null ? root : new TreeNode();
        node.setData(rootData);

        //判断根节点在中序队列中的位置
        int i = 0;
        for(;i < inList.size(); i++){
            if(inList.get(i).equals(rootData)) {
                break;
            }
        }

        node.setLeft(createTreeByPostAndIn(postList.subList(0, i), inList.subList(0, i), null));
        node.setRight(createTreeByPostAndIn(postList.subList(i, postList.size()-1), inList.subList(i+1, inList.size()), null));
        return node;
    }

    /**
     * 按照层序和中序创建二叉树
     */
    public void createTreeByBreadthAndIn(List<E> breadthList, List<E> inList){
        Integer index = 0;
        Map<E, Integer> indexMap = new HashMap<>();
        for (E e : breadthList) {
            indexMap.put(e, index++);
        }
        createTreeByBreadthAndIn(inList, indexMap, root);
    }

    private TreeNode createTreeByBreadthAndIn(List<E> inList, Map<E, Integer> indexMap, TreeNode root){
        if(CollectionUtils.isEmpty(inList)){
            return null;
        }

        //通过层序确定root在中序中的位置
        int indexOfBreadth = indexMap.size()-1;
        int indexOfIn = 0;
        for(int i =0; i < inList.size(); i++){
            int n = indexMap.get(inList.get(i));
            if(n < indexOfBreadth){
                indexOfIn = i;
                indexOfBreadth = n;
            }
        }

        E rootData = inList.get(indexOfIn);
        TreeNode node = root  != null ? root : new TreeNode();
        node.setData(rootData);

        node.setLeft(createTreeByBreadthAndIn(inList.subList(0, indexOfIn), indexMap,null));
        node.setRight(createTreeByBreadthAndIn( inList.subList(indexOfIn+1, inList.size()), indexMap,null));
        return node;
    }

    //创建Huffman树
    public void createHuffmanTre(List<TreeNode<E>> nodes){
        while (nodes.size() > 1){
            quickSort(nodes, 0 , nodes.size()-1);

            TreeNode leftNode = nodes.get(0);
            TreeNode rightNode = nodes.get(1);

            TreeNode parNode = new TreeNode(new Object(), leftNode.getWeight()+rightNode.getWeight(), leftNode, rightNode);
            nodes.remove(0);
            nodes.set(0, parNode);
        }

        root = nodes.get(0);
    }

    private void quickSort(List<TreeNode<E>> nodes, int low, int high){
        int start = low;
        int end = high;
        TreeNode key = nodes.get(start);
        while(end > start){
            //先从后往前比
            while(end > start && nodes.get(end).getWeight() >= key.getWeight()){
                end --;
            }
            if(nodes.get(end).getWeight() < key.getWeight()){
                swap(nodes, start, end);
            }
            //从前往后比
            while(end > start && nodes.get(start).getWeight() <= key.getWeight()){
                start ++;
            }
            if(nodes.get(start).getWeight() > key.getWeight()){
                swap(nodes, start, end);
            }
        }

        //一次遍历完后key的位置就确定了
        if(start > low){//递归key的左边
            quickSort(nodes, low, start-1);
        }
        if(end < high){//递归key的右边
            quickSort(nodes, end+1, high);
        }
    }

    private void swap(List<TreeNode<E>> nodes, int i, int j){
        TreeNode temp = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, temp);
    }


    public static void main(String[] args) {
        BinaryTree<String> tree = new BinaryTree();

        List<String> dataList = Arrays.asList("A", "B", "C", null, null, "D", "E", null, null, "F", null, null,"G", null, null);
        tree.createTreeByPre(dataList);
        System.out.println(tree.preIterator().toString());
        System.out.println(tree.inIterator().toString());
        System.out.println(tree.postIterator());
        System.out.println(tree.breadthIterator());

        List<String> inList = Arrays.asList("C", "B", "E","D", "F", "A", "G");

        List<String> preList = Arrays.asList("A", "B", "C","D", "E", "F", "G");
        tree.createTreeByPreAndIn(preList, inList);
        System.out.println("\n前序中序构建，打印后续、层序-------------------------------------------");
        System.out.println(tree.postIterator());
        System.out.println(tree.breadthIterator());

        List<String> postList = Arrays.asList("C", "E", "F","D", "B", "G", "A");
        tree.createTreeByPostAndIn(postList, inList);
        System.out.println("\n后序中序构建，打印前续、层序-------------------------------------------");
        System.out.println(tree.preIterator());
        System.out.println(tree.breadthIterator());

        List<String> breadthList = Arrays.asList("A", "B", "G","C", "D", "E", "F");
        tree.createTreeByBreadthAndIn(breadthList, inList);
        System.out.println("\n层序中序构建，打印前续、后序-------------------------------------------");
        System.out.println(tree.preIterator());
        System.out.println(tree.postIterator());

        List<TreeNode<String>> nodeList = Lists.newArrayList(
                new TreeNode<>("A",  40D),
                new TreeNode<>("B",  8D),
                new TreeNode<>("C",  10D),
                new TreeNode<>("D",  30D),
                new TreeNode<>("E",  10D),
                new TreeNode<>("F",  2D)
        );
        tree.createHuffmanTre(nodeList);
        System.out.println("\n Huffman树，打印层序-------------------------------------------");
        System.out.println(tree.preIterator());
        System.out.println(tree.inIterator());
        System.out.println(tree.breadthIterator());
    }

}
