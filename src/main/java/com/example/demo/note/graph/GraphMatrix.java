package com.example.demo.note.graph;

import com.example.demo.common.exception.ApiException;
import com.example.demo.common.exception.ExceptionCode;
import com.example.demo.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhouwei on 2018/10/19
 * 邻接矩阵
 **/
@Slf4j
public class GraphMatrix<T> {
    //最大顶点数
    private int maxSize;

    //顶点集合
    private Vertex[] vertexes;

    //顶点索引
    private int index;

    //邻接矩阵
    private Integer[] [] matrix;

    private static  final Integer MAX_WEIGHT = 1000;

    public GraphMatrix(int maxSize) {
        this.maxSize = maxSize;
        vertexes = new Vertex [maxSize];
        matrix = new Integer[maxSize][maxSize];
        int i =0;
        while (i < maxSize){
            int  j = 0;
            while (j < maxSize){
                if(i == j){
                    matrix[i][j] = 0;
                }else{
                    matrix[i][j] = MAX_WEIGHT;
                }
                j ++;
            }
            i ++;
        }
    }

    /**
     * 添加顶点
     */
    public void addVertex(T data){
        vertexes[index] = new Vertex(index, data);
        index ++;
    }

    /**
     * 操作边（添加、删除）
     * @param weight 权重 =MAX_WEIGHT时表示删除
     * @param isDirection 是否有向
     */
    public void operateEdge(T a, T  b, Integer weight, boolean isDirection) throws Exception{
        Vertex v1 = null, v2 = null;
        for (Vertex vertex : vertexes) {
            if(vertex.getData().equals(a)){
                v1 = vertex;
            }
            if(vertex.getData().equals(b)){
                v2 = vertex;
            }
            if(v1 != null && v2 != null){
                break;
            }
        }

        if((v1 == null || v2 == null) &&  MAX_WEIGHT.equals(weight)){
            log.info("操作顶点不存在，a:{} b:{}", JsonUtil.objectToJson(v1), JsonUtil.objectToJson(v2));
            throw new ApiException(ExceptionCode.PARAM_INVALID);
        }

        matrix[v1.getIndex()][v2.getIndex()] = weight;
        if(!isDirection){
            matrix[v2.getIndex()][v1.getIndex()] = weight;
        }
    }

    /**
     * 获得顶点的出度
     */
    public int getOutDegree(T data) throws Exception{
        Vertex v = getVertex(data);
        if(v == null){
            log.info("查询顶点不存在");
            throw new ApiException(ExceptionCode.PARAM_INVALID);
        }

        int outDegree = 0;
       for(Integer weight : matrix[v.getIndex()]){
           if(weight != 0 && weight < MAX_WEIGHT){
               outDegree ++;
           }
       }
       return outDegree;
    }

    /**
     * 获得顶点的入度
     */
    public int getInDegree(T data) throws Exception{
        Vertex v = getVertex(data);
        if(v == null){
            log.info("查询顶点不存在");
            throw new ApiException(ExceptionCode.PARAM_INVALID);
        }

        int inDegree = 0;
        for(int i =0; i < maxSize; i++){
            if(i != v.index && matrix[i][v.index] < MAX_WEIGHT){
                inDegree ++;
            }
        }
        return inDegree;
    }

    /**
     * 广度优先遍历
     * @param data
     * @return
     */
    public List<Vertex> breadthFirst(T data) throws Exception{
        Vertex v = getVertex(data);
        if(v == null){
            log.info("指定顶点不存在");
            throw new ApiException(ExceptionCode.PARAM_INVALID);
        }

        List<Vertex> result = new ArrayList<>(vertexes.length);
        v.setVisited(true);
        result.add(v);

        LinkedBlockingQueue<Vertex> queue = new LinkedBlockingQueue<>();
        queue.offer(v);
        while (!queue.isEmpty()){
            Vertex vertex = queue.poll();
            for(int i =0; i < maxSize; i++){
                if(MAX_WEIGHT.equals(matrix[vertex.index][i]) || vertexes[i].isVisited){
                    continue;
                }
                vertexes[i].setVisited(true);
                result.add(vertexes[i]);
                queue.offer(vertexes[i]);
            }
        }
        return result;
    }



    /**
     * 深度优先遍历
     * @param data
     * @return
     */
    public List<Vertex> depthFirst(T data) throws Exception{
        Vertex v = getVertex(data);
        if(v == null){
            log.info("指定顶点不存在");
            throw new ApiException(ExceptionCode.PARAM_INVALID);
        }

        List<Vertex> result = new ArrayList<>(vertexes.length);
        v.setVisited(true);
        result.add(v);

        Stack<Vertex> stack = new Stack<>();
        Map<Integer, Integer> indexMap = new HashMap<>(); //记录顶点的边的访问偏移量
        stack.push(v);

        while (!stack.isEmpty()){
            Vertex vertex = stack.pop();
            int vIndex = indexMap.get(vertex.getIndex()) != null ? indexMap.get(vertex.getIndex()) : 0;
            if(vIndex == vertex.getIndex()){
                vIndex ++;
            }
            if(vIndex == maxSize){
                continue;
            }

            stack.push(vertex);
            if(!MAX_WEIGHT.equals(matrix[vertex.getIndex()][vIndex])){
                if(!vertexes[vIndex].isVisited()){
                    vertexes[vIndex].setVisited(true);
                    result.add(vertexes[vIndex]);
                    stack.push(vertexes[vIndex]);
                }
            }

            indexMap.put(vertex.getIndex(), ++vIndex);
        }

        return result;
    }

    /**
     * 最小生成树
     * prim算法
     * 定义一个临时的一维数组，用于存放可用的连接边，数组下标为顶点序号，值为权值
     * 任选一个点作为起点，以起点的所有权值对数组进行初始化
     * 找出数组中最小权值的边，即为最小生成树中的一条有效边
     * 将找到的最小边在数组中赋值为0，代表已经使用过。并将数组与找到顶点的所有边进行比较，若顶点的边的权值比当前数组存放的可用边的权值小，则进行覆盖
     * 重复循环2，3，4的操作直至遍历完所有顶点
     */
    public int createMinSpanTreePrim(){
        Integer[] lowcost = new Integer[maxSize];
        System.arraycopy(matrix[0], 0, lowcost, 0 , maxSize);
        int sum = 0;
        for(int i = 0; i < maxSize; i ++){
            //寻找最小权值
            int minIndex = -1;
            for(int j = 0; j < maxSize; j ++){
                if(lowcost[j] > 0 && lowcost[j] < MAX_WEIGHT && (minIndex == -1 || lowcost[j] < lowcost[minIndex])){
                    minIndex = j;
                }
            }

            if(minIndex == -1){
                break;
            }

            System.out.println("访问到了节点："+ vertexes[minIndex].getData() +", 权值："+ lowcost[minIndex]);
            sum += lowcost[minIndex];
            lowcost[minIndex] = 0;

            // 将存放最小权值的数组与下一个节点的所有连接点对比，找出最小权值
            for (int j = 0; j < maxSize; j++) {
                if (matrix[minIndex][j] < lowcost[j]) {
                    lowcost[j] = matrix[minIndex][j];
                }
            }
        }
        return sum;
    }



    /**
     * 将图内所有元素设置为初始状态
     */
    private void initVisited(){
        for(int i = 0; i<maxSize; i++){
            vertexes[i].isVisited = false;
        }
    }

    //查找指定顶点
    private Vertex getVertex(T data){
        Vertex v =null;
        for (Vertex vertex : vertexes) {
            if(vertex.getData().equals(data)){
                v = vertex;
            }
        }
        return v;
    }

    /**
     * 顶点
     */
    class Vertex<T>{
        private int index;

        private T data ;

        private boolean isVisited;

        public Vertex(int index, T data) {
            this.index = index;
            this.data = data;
        }

        public int getIndex() {
            return index;
        }

        public T getData() {
            return data;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }
    }

    public Integer[][] getMatrix() {
        return matrix;
    }

    public static void main(String[] args) throws Exception{
        GraphMatrix<String> graph = new GraphMatrix<>(9);

        graph.addVertex("0");
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");
        graph.addVertex("6");
        graph.addVertex("7");
        graph.addVertex("8");

        graph.operateEdge("0", "1", 10, false);
        graph.operateEdge("0", "5", 11, false);
        graph.operateEdge("1", "2", 18, false);
        graph.operateEdge("1", "6", 16, false);
        graph.operateEdge("1", "8", 12, false);
        graph.operateEdge("2", "3", 22, false);
        graph.operateEdge("2", "8", 8, false);
        graph.operateEdge("3", "4", 20, false);
        graph.operateEdge("3", "6", 24, false);
        graph.operateEdge("3", "7", 16, false);
        graph.operateEdge("3", "8", 21, false);
        graph.operateEdge("4", "5", 26, false);
        graph.operateEdge("4", "7", 7, false);
        graph.operateEdge("5", "6", 17, false);
        graph.operateEdge("6", "7", 19, false);

        System.out.println("邻接矩阵：" +JsonUtil.objectToJson(graph.matrix));

        System.out.println("广度遍历：" );
        graph.breadthFirst("0").forEach(item -> System.out.print(item.getData()+"\t"));
        System.out.println();

        graph.initVisited();
        System.out.println("深度遍历：" );
        graph.depthFirst("0").forEach(item -> System.out.print(item.getData()+"\t"));
        System.out.println();

        System.out.println("prim 最小生成树");
        int sumMinWeight = graph.createMinSpanTreePrim();
        System.out.println("sumMinWeight:" + sumMinWeight);
    }

}
