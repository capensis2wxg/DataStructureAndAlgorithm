package _06栈;/*
    @author wxg
    @date 2021/9/9-13:18
    */

import java.util.Scanner;

public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(4);
        String key;
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while(loop){
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈（入栈）");
            System.out.println("pop: 表示从栈取出数据（出栈）");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key){
                case "show":
                    arrayStack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    arrayStack.push(value);
                    break;
                case "pop":
                    try {
                        int ret = arrayStack.pop();
                        System.out.printf("出栈的数据是%d\n", ret);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
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


class ArrayStack {
    private final int maxSize;  //    栈的大小
    private final int[] stack;    // 数组，用来模拟栈，数据就放在该数组
    private int top = -1;   //  top表示栈顶，初始化为-1；

    //  构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        //  创建一个数组类型的栈
        stack = new int[this.maxSize];
    }

    //  栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    //  栈空
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 入栈
     *
     * @param value 进入栈中的数据
     */
    //  入栈-push
    public void push(int value) {
        //  先判断栈是否满
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    /**
     * 出栈
     *
     * @return 弹出栈中的数据
     */
    //  出栈-pop,将栈顶的数据返回
    public int pop() {
        if (isEmpty()) throw new RuntimeException("栈空，没有数据~");
        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }
        for (int i = top; i >= 0; i--) System.out.printf("stack[%d]=%d\n", i, stack[i]);
    }
}


