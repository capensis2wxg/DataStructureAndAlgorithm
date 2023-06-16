package _02队列;/*
    @author wxg
    @date 2021/9/5-15:04
    */

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author capensis
 */
public class ArraySimulationQueue {
    public static void main(String[] args) {
        //创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
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


/**
 *<pre>该数组模拟的队列存在一个问题：
 *  一旦{@link #front==rear}时，也就是队列内的数据被判断为空时，这时front和rear的值都是maxSize-1。
 *  也就是说，在这种情况下，数组其实是满的，是无法插入数据的, 即数组无法二次复用
 *  根本原因在于：数组已满的条件判断错误, 未考虑头指针和尾指针的变化情况。</pre>
 */
class ArrayQueue{
    /** 表示数组的最大容量 **/
    private final int maxSize;
    /** 队列头 */
    private int front;
    /** 队列尾 */
    private int rear;
    /** 该数组用于存放数据，模拟队列 */
    private final int[] arr;
    /** 构造器 */
    public ArrayQueue(int arrMaxSize){
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
        //  给队列和队尾赋值
        front = -1;
        rear = -1;
    }

    /**
     * 判断队列是否满
     * @return  boolean
     */
    public boolean isFull(){
        /*
         *  这里判断队列已满的条件是存在问题的，因为当调用getQueue()时，rear并未减小。
         *  因此，一旦rear的值第一次为maxSize时，之后永远都为maxSize
         */
        return rear == maxSize - 1;
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
        // 首先判断队列是否已满
        if(isFull()){
            System.out.println("队列已满");
            return;
        }
        rear++; //让尾指针后移
        arr[rear] = n;
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
        //  这里get数据时，虽然可以保证弹出的是指定的数据，但是却没有减小rear的值
        front++;
        return arr[front];
    }

    /**
     * 遍历数组
     */
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空");
            return;
        }
        IntStream.range(0, arr.length).forEach(i -> System.out.printf("arr[%d]=%d\n", i, arr[i]));
    }

    /**
     * 显示队列的头数据
     * @return  头数据
     */
    public int headQueue(){
        if (isEmpty()) {
            throw new AssertionError("队列已空");
        }
        return arr[front+1];
    }
}
