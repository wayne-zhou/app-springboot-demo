package com.example.demo.note.graph;

/**
 * Created by zhouwei on 2018/10/26
 * 邻接表
 **/
public class GraphEdge {

    /**
     * 最小生成树
     * Kruskal
     * 先将所有边进行权值的从小到大排序
     * 定义一个一维数组代表连接过的边，数组的下标为边的起点，值为边的终点
     * 按照排好序的集合用边对顶点进行依次连接，连接的边则存放到一维数组中
     * 用一维数组判断是否对已经连接的边能构成回路，有回路则无效，没回路则是一条有效边
     * 重复3，4直至遍历完所有的边为止，即找到最小生成树
     */
    public static int createMinSpanTreeKruskal(Edge[] edges){
        int sum = 0;
        //TODO edges 按照weight升序排序

        // 初始化一个数组，代表连接过的边（下标为连线的起点，值为连线的终点）
        int[] parent = new int[edges.length];
        for(int i =0; i < edges.length; i++){
            parent[i] = 0;
        }

        for(Edge edge : edges){
            // 找到起点和终点在临时连线数组中的最后连接点
            int start = findIndex(parent, edge.start);
            int end = findIndex(parent, edge.end);

            // 通过起点和终点找到的最后连接点是否为同一个点，是则产生回环
            if(start != end){
                // 没有产生回环则将临时数组中，起点为下标，终点为值
                parent[start] = end;
                sum += edge.weight;
                System.out.println("访问到了节点：{" + start + "," + end + "}，权值：" + edge.weight);
            }
        }
        return sum;
    }

    private static int findIndex(int[] parent, int index){
        while (parent[index] > 0){
            index = parent[index];
        }
        return index;
    }

    static class Edge{
        private int start;

        private int end;

        private Integer weight;

        public Edge(int start, int end, Integer weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public Integer getWeight() {
            return weight;
        }
    }

    public static void main(String[] args) {
        Edge[] edges = new Edge[15];
        edges[0] = new Edge(4, 7, 7);
        edges[1] = new Edge(2, 8, 8);
        edges[2] = new Edge(0, 1, 10);
        edges[3] = new Edge(0, 5, 11);
        edges[4] = new Edge(1, 8, 12);
        edges[5]= new Edge(3, 7, 16);
        edges[6]= new Edge(1, 6, 16);
        edges[7]= new Edge(5, 6, 17);
        edges[8]= new Edge(1, 2, 18);
        edges[9]= new Edge(6, 7, 19);
        edges[10] = new Edge(3, 4, 20);
        edges[11]= new Edge(3, 8, 21);
        edges[12]= new Edge(2, 3, 22);
        edges[13]= new Edge(3, 6, 24);
        edges[14]= new Edge(4, 5, 26);

        System.out.println("Kruskal 最小生成树");
        int sumMinWeight = GraphEdge.createMinSpanTreeKruskal(edges);
        System.out.println("sumMinWeight:" + sumMinWeight);
    }
}
