package __15树结构应用;/*
    @author wxg
    @date 2021/10/10-9:16
    */

import java.util.ArrayList;
import java.util.Collections;

public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6,1};
        Node root = createHuffmanTree(arr);
        //测试一把
        preOrder(root);
    }

    //编写一个前序遍历的方法
    public static void preOrder(Node root){
        if(root != null) root.preOrder();
        else System.out.println("是空树，不能遍历");
    }

    /**
     * 创建赫夫曼树的方法
     * @param arr 需要创建成哈夫曼树的数组
     * @return 创建好后的赫夫曼树的root结点
     */
    public static Node createHuffmanTree(int[] arr){
        //遍历arr数组， 将数组中的每个元素换成一个Node,将数组放入到ArrayList中
        ArrayList<Node> nodes = new ArrayList<>();
        for (int value : arr) nodes.add(new Node(value));
        while(nodes.size() > 1){
            Collections.sort(nodes);
            //取出根节点权值最小的两颗二叉树
            //(1) 取出权值最小的结点（二叉树）
            Node leftNode = nodes.get(0);
            //(2) 取出权值第二小的结点（二叉树）
            Node rightNode = nodes.get(1);
            //(3)构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //(4)从ArrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //(5)将parent加入到nodes
            nodes.add(parent);
        }
        return nodes.get(0);

    }
}


/**
 * // 创建结点类 为了让Node 对象持续排序Collections集合排序, 让Node 实现Comparable接口
 */
class Node implements Comparable<Node>{
    int value; //节点权值
    Node left; // 指向左子节点
    Node right; // 指向右子节点

    public Node(int value){
        this.value = value;
    }

    // 写一个前序遍历
    public void preOrder(){
        //打印该对象, this会调用自己的toString()方法
        System.out.println(this);
        if(this.left != null) this.left.preOrder();
        if(this.right != null) this.right.preOrder();
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
