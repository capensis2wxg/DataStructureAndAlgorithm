package _02队列;/*
    @author wxg
    @date 2021/9/5-15:04
    */

import java.util.Queue;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author capensis
 */
public class SimulatedCircularQueue {
    public static void main(String[] args) {
        //创建一个队列, 这里的有效数据是3个
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4);
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        //  输出一个菜单
        while (loop) {
            System.out.println("s(show)： 显示队列");
            System.out.println("e(exit)： 退出程序");
            System.out.println("a(add)： 添加数据到队列");
            System.out.println("g(get)： 从队列中取出数据");
            System.out.println("h(head)： 查看队列的头数据");
            //  接受一个输入的字符
            char key = scanner.next().charAt(0);
            //  判断调用何种方法
            switch (key){
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}


class CircleArrayQueue {
    /** 表示数组的最大容量 */
    private final int maxSize;
    /** 队列头 */
    private int front;
    /** 队列尾 */
    private int rear;
    /** 该数组用于存放数组，模拟队列 */
    private final int[] arr;

    /** 构造器 */
    public CircleArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    /**
     * 判断队列是否满
     * @return  boolean
     */
    public boolean isFull() {
        // 这里说明front到rear一共存在maxSize个数据, rear的取值一直在[0,maxSize-1]之间，+1 的目的是为了确保环形数组的实现
        return (rear + 1) % maxSize == front;
    }

    /**
     * 判断队列是否为空
     * @return boolean
     */
    public boolean isEmpty(){
        return rear == front;
    }
    /**
     * 添加数据到队列中
     * @param n 数据
     */
    public void addQueue(int n){
        /*
         首先判断队列是否已满,如果(rear + 1) % maxSize == front,
         即：
            如果front = 0, rear=maxSize-1时，arr[maxSize-1]就越界了,此时存在maxSize-1个数据；
            如果front = 1, rear = maxSize, 此时存在maxSize-1个数据
         */
        if(isFull()){
            System.out.println("队列已满");
            return;
        }
        arr[rear] = n;
        //  将rear后移, 这里考虑必须取模
        rear = (rear + 1) % maxSize;
    }

    /**
     *  获取队列的数据
     * @return  int 数据
     */
    public int getQueue(){
        //  判断队列是否为空
        if(isEmpty()) {
            throw new RuntimeException("队列已空，不能获取数据");
        }
        int value = arr[front];
        //  这里get数据时，虽然可以保证弹出的是指定的数据，但是却没有减小rear的值
        front = (front + 1) % maxSize;
        return value;
    }

    /** 求出当前队列有效数据的个数 */
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }
    /**
     * 遍历数组
     */
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空");
            return;
        }
        IntStream.range(front, front + size()).forEach(i -> System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]));
    }

    /**
     * 显示队列的头数据
     * @return  头数据
     */
    public int headQueue(){
        if(isEmpty()) {
            throw new RuntimeException("队列已空");
        }
        return arr[front];
    }
}