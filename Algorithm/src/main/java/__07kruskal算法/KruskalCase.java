package __07kruskal算法;/*
    @author wxg
    @date 2021/10/28-15:35
    */

import java.util.Arrays;

public class KruskalCase {
    private int edgeNum; //边的个数
    private final char[] vertexs; //顶点数组
    private final int[][] matrix; //邻接矩阵
    //使用 INF 表示两个顶点不能连通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //克鲁斯卡尔算法的邻接矩阵
        int[][] matrix = {
                       /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        //大家可以在去测试其它的邻接矩阵，结果都可以得到最小生成树

        //创建KruskalCase 对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        //输出构建的邻接矩阵
        kruskalCase.print();
        EData[] edges = kruskalCase.getEdges();
        kruskalCase.sortEdges(edges);
        for (EData edge : edges) System.out.println(edge);
        //调用克鲁斯卡尔算法
        kruskalCase.kruskal();

    }

    //构造器
    public KruskalCase(char[] vertexs, int[][] matrix) {
        //初始化顶点数和边的个数
        int vlen = vertexs.length;
        //初始化顶点, 复制拷贝的方式
        this.vertexs = new char[vlen];
        System.arraycopy(vertexs, 0, this.vertexs, 0, vertexs.length);
        //初始化边, 使用的是复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for(int i = 0; i < vlen; i++) System.arraycopy(matrix[i], 0, this.matrix[i], 0, vlen);
        //统计边的条数
        for(int i =0; i < vlen; i++) for (int j = i + 1; j < vlen; j++) if (this.matrix[i][j] != INF) edgeNum++;
    }

    public void kruskal() {
        int index = 0; //表示最后结果数组的索引
        int[] ends = new int[edgeNum]; //用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
        //创建结果数组, 保存最后的最小生成树
        EData[] rets = new EData[edgeNum];
        //获取图中所有的边的集合，一共有12边
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges) + " 共"+ edges.length); //12
        //按照边的权值大小进行排序(从小到大)
        sortEdges(edges);
        //遍历edges 数组，将边添加到最小生成树中时，判断准备加入的边否形成了回路，如果没有，就加入rets, 否则不能加入
        for(int i=0; i < edgeNum; i++) {
            //获取到第i条边的第1个顶点(起点)
            int p1 = getPosition(edges[i].start); //p1=4, 2, 3, 2-->3--->5，不能加入
            //获取到第i条边的第2个顶点
            int p2 = getPosition(edges[i].end); //p2 = 5, 3, 4, 4-->5，不能加入
            //获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends, p1); //m = 4, 2, 3, 3
            //获取p2这个顶点在已有最小生成树中的终点
            int n = getEnd(ends, p2); // n = 5, 3, 5,
            //是否构成回路
            if(m != n) { //没有构成回路
                /*
                    设置m在"已有最小生成树"中的终点 <E,F> [0,0,0,0,5,0,0,0,0,0,0,0]
                 这个赋值方式表明：该位置的值所代表的车站就是该位置的索引值所代表的车站的终点
                 */
                ends[m] = n;
                rets[index++] = edges[i]; //有一条边加入到rets数组
            }
        }
        //<E,F> <C,D> <D,E> <B,F> <E,G> <A,B>。
        //统计并打印 "最小生成树", 输出  rets
        System.out.println("最小生成树为");
        for(int i = 0; i < index; i++) System.out.println(rets[i]);
    }

    //打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为: \n");
        for(int i = 0; i < vertexs.length; i++) {
            for(int j=0; j < vertexs.length; j++) System.out.printf("%12d", matrix[i][j]);
            System.out.println();//换行
        }
    }

    /**
     * 功能: 获取图中边，放到EData[] 数组中，后面我们需要遍历该数组
     * 是通过matrix 邻接矩阵来获取
     * EData[] 形式 [['A','B', 12], ['B','F',7], .....]
     * @return EData[]
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for(int i = 0; i < vertexs.length; i++) {
            for(int j=i+1; j <vertexs.length; j++)
                if (matrix[i][j] != INF) edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
        }
        return edges;
    }

    /**
     * 功能: 获取下标为i的顶点的终点(), 用于后面判断两个顶点的终点是否相同
     * @param ends ：数组就是记录了各个顶点对应的终点是哪个,ends数组是在遍历过程中逐步形成
     * @param i : 表示传入的顶点对应的下标
     * @return 返回的就是 下标为i的这个顶点对应的终点的下标, 一会回头还有来理解
     */
    private int getEnd(int[] ends, int i) { // i = 4 [0,0,0,0,5,0,0,0,0,0,0,0]
        //这里从某个顶点开始采用链接方式寻找终点，所以一定要采用循环的方式，找到最后的终点
        while(ends[i] != 0) i = ends[i];
        return i;
    }

    /**
     * 功能：对边进行排序处理, 冒泡排序
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges) {
        for(int i = 0; i < edges.length - 1; i++) {
            for(int j = 0; j < edges.length - 1 - i; j++) {
                if(edges[j].weight > edges[j+1].weight) {//交换
                    EData tmp = edges[j];
                    edges[j] = edges[j+1];
                    edges[j+1] = tmp;
                }
            }
        }
    }

    /**
     * 获取某个车站在数组中的索引位置
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    private int getPosition(char ch) {
        //找到
        for(int i = 0; i < vertexs.length; i++) if (vertexs[i] == ch) return i;
        //找不到,返回-1
        return -1;
    }

}


//创建一个类EData ，它的对象实例就表示一条边
class EData {
    char start; //边的一个点
    char end; //边的另外一个点
    int weight; //边的权值
    //构造器
    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    //重写toString, 便于输出边信息
    @Override
    public String toString() {
        return "EData [<" + start + ", " + end + "> = " + weight + "]";
    }
}