package _07中缀表达式;/*
    @author wxg
    @date 2021/9/9-19:15
    */

import java.util.EmptyStackException;

public class ArrayImplStack {
    //栈空间大小
    public int maxSize;
    //栈顶
    public int top;
    //数组
    public Object[] arr;

    //初始化
    public ArrayImplStack(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new Object[maxSize];
        this.top = -1;
    }

    //判断栈是否为空
    public boolean isEmpty() {
        return top == -1;
    }

    //判断栈是否满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    //入栈
    public void push(Object obj) {
        //判断是否满
        if (isFull()) {
            throw new RuntimeException("栈存储已满");
        }
        //添加数据
        top++;
        arr[top] = obj;
    }

    //出栈
    public Object pop() {
        //判断栈是否为空
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        //出栈
        Object value = arr[top];
        top--;
        return value;
    }

    //获取栈中最后一个节点的值
    public Object peek() {
        return arr[top];
    }

    //展示栈
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，没有数据~~");
            return;
        }
        //循环遍历
        for (int i = top; i > -1; i--) System.out.printf("stack[%d]=%d\n", i, Integer.parseInt(arr[i].toString()));
    }
}
