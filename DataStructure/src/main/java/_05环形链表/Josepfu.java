package _05环形链表;/*
    @author wxg
    @date 2021/9/7-16:36
    */

public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.add(30);
        circleSingleLinkedList.list();

        circleSingleLinkedList.countBoy(1, 5, 30);
    }
}


//创建一个环形的单向链表
class CircleSingleLinkedList {
    //  创建一个first节点，当前没有编号
    private Child first = null;

    /**
     * 添加小孩节点，构建成一个环形的链表
     *
     * @param num 小孩的个数
     */
    public void add(int num) {
        if (num < 2) {
            System.out.println("num的值太小(>=3)");
            return;
        }
        Child auxiliaryPointer = null;  //辅助指针，帮助构建环形链表
        for (int i = 1; i <= num; i++) {
            //  new出来的child的两个属性值一个是0，一个是null
            Child child = new Child(i);
            if (i == 1) {
                /*
                    将第一个节点赋给了first，这里first的一顿操作其实就是在操作child，因为两个变量具有相同的内存地址。
                    但是，这里却不能直接使用child来直接调用setNext
                 */
                first = child;
                first.setNext(first);   //让curChild指向第一个小孩节点
                auxiliaryPointer = first;
            } else {
                //  这里其实就是让first.setNext()
                auxiliaryPointer.setNext(child);
                /*
                 PS: 这里的赋值操作使得curChild和child两个基本引用数据类型有了相同的内存地址。
                     所以，所有对于curChild的操作(包括指向下一个child)都同样的作用于child
                 */
                child.setNext(first);   //每次加入一个节点，就闭合形成环形链表
                //  这里是为了方便下一个节点加入之后,重新修改child的指向
                auxiliaryPointer = child;
            }
        }
    }

    public void list() {
        if (first == null) {
            System.out.println("没有任何小孩节点");
            return;
        }
        Child auxiliaryPointer = first;
        while (true) {
            System.out.printf("%d->", auxiliaryPointer.getNo());
            if (auxiliaryPointer.getNext() == first) break;
            auxiliaryPointer = auxiliaryPointer.getNext();
        }
        System.out.println();
    }

    /**
     * 出圈
     *
     * @param startNo  从第几个小孩开始
     * @param countNum 表示数几下
     * @param nums     表示起初孩子的总数
     */
    public void countBoy(int startNo, int countNum, int nums) {
        //  先进行数据校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误，请重新输入");
            return;
        }
        /*
            如果一个小孩节点被删除，那么这个节点的前一个节点最好用一个指针随时盯着，这样重组链表示会比较方便。
            由于这个小孩节点可能会是第一个小孩节点，因此，这个帮助指针最好直到末尾
         */
        Child helper = first;
        while (helper.getNext() != first) {
            helper = helper.getNext();
        }

        // 报数小孩不一定从第一个开始，如果从startNo开始，那么将其视为1的话，helper和first要移动startNo-1次
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //  出圈，需要重组连表，countNum如果为1，则循环0次；因此两个指针需要循环countNum-1次
        while (helper != first) {
            //此时只剩一个人
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //  此时first所指的需要出圈
            System.out.printf("%d-out\n", first.getNo());
            first = first.getNext();
            helper.setNext(first);

        }
        System.out.printf("stayed-%d", first.getNo());


    }
}

//创建一个Boy类，表示一个节点
class Child {
    private int no; //编号
    //  这是一个迭代
    private Child next; //指向下一个节点， 默认null

    public Child(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Child getNext() {
        return next;
    }

    public void setNext(Child next) {
        this.next = next;
    }
}

