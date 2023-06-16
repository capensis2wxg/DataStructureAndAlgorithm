package _03单链表;

import java.util.Stack;
/**
 * @author capensis
 * @date 2021/9/6-13:45
*/
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        // 创建几个节点对象
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        System.out.println("hero1的内存地址：" + hero1.hashCode());
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        System.out.println("================================未添加前遍历==============================");
        singleLinkedList.list();
        System.out.println("================================添加之后遍历===============================");
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);
        singleLinkedList.list();
        System.out.println("================================修改hero1的节点信息==========================");
        HeroNode heroNodeUpdate = new HeroNode(1, "黑三郎", "呼保义");
        singleLinkedList.update(heroNodeUpdate);
        singleLinkedList.list();
        System.out.println("hero1的内存地址：" + singleLinkedList.head.next.hashCode());
        System.out.println("hero1实际存储的内容：" + hero1);
        //  错误示范
        HeroNode heroNodeUpdate1 = new HeroNode(5, "黑三郎", "呼保义");
        System.out.println("heroNodeUpdate1的内存地址：" + heroNodeUpdate1.hashCode());
        singleLinkedList.update(heroNodeUpdate1);
        singleLinkedList.list();
        System.out.println("================================删除hero1的节点信息=========================");
        singleLinkedList.del(1);
        singleLinkedList.list();
        //  错误示范
        singleLinkedList.del(5);
        singleLinkedList.list();
        System.out.println("================================按照no大小升序进行添加======================");
        //  错误添加, 这里hero1指向的内存地址的对象内容已经被修改了
        System.out.println("hero1实际存储的内容：" + hero1);
        System.out.println("hero1的内存地址：" + hero1.hashCode());
        /* singleLinkedList.addByOrder(hero1); */
        singleLinkedList.addByOrder(new HeroNode(1, "宋江", "及时雨"));
        // 正确添加
        singleLinkedList.del(3);
        singleLinkedList.list();
        System.out.println("================================addByOrder遍历=============================");
        // 这里hero3变量指向的内存地址的对象内容没有被修改
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.list();
        System.out.println("================================获取有效节点的个数==========================");
        System.out.println(singleLinkedList.getLength(singleLinkedList.head));
        System.out.println("================================查找倒数第K个节点======================== =");
        System.out.println(singleLinkedList.findLastIndexNode(singleLinkedList.head, 2));
        System.out.println("================================反转节点==================================");
        singleLinkedList.reverseList(singleLinkedList.head);
        singleLinkedList.list();
        System.out.println("================================从尾到头打印==================================");
        singleLinkedList.reversePrint(singleLinkedList.head);
    }
}

/**
 * 定义一些操作链表的方法
 */
class SingleLinkedList{
    /**  先定义一个头节点, 头节点不要动，不存放具体的数据 */
    public final HeroNode head = new HeroNode(0, "", "");

    /**
     * 不考虑编号顺序添加节点(尾插法)  <p>
     * 1：找到当前链表的最后一个节点  <p>
     * 2：将最后这个节点的next属性指向新的节点
     * @param heroNode  要添加的节点
     */
     public void add(HeroNode heroNode){
         // head节点不能动，所以需要一个辅助的头节点进行位移从而遍历
         HeroNode temp = head;
         // 遍历节点，找到最后一个节点
         while (temp.next != null) {
             temp = temp.next;
         }
         // while退出循环，此时链表要么只有一个头节点，要么就是遍历到了最后一个节点
         temp.next = heroNode;
     }

    /**
     * 根据排名将英雄插入到指定位置，如果排名已经存在，则添加失败，并给出提示
     * @param heroNode  要添加的节点
     */
     public void addByOrder(HeroNode heroNode){
         HeroNode temp = head;
         boolean flag = false;
         while(true){
             // 说明temp已经在链表的最后
             if(temp.next == null){
                 break;
             }
             // 位置找到, 就在temp的后面插入
             if(temp.next.no > heroNode.no) {
                 break;
             }else if (temp.next.no == heroNode.no) {
                 flag = true;
                 break;
             }
             temp = temp.next;
         }
         // 判断flag的值
         if(flag) {
             System.out.printf("准备插入的英雄的编号 %d 已经存在了，不能加入\n", heroNode.no);
         }else {
             // 插入到链表中, temp的后面
             heroNode.next = temp.next;
             temp.next = heroNode;
         }
     }

    /**
     * 遍历链表节点
     */
    public void list(){
         // 判断链表是否为空
         if(head.next == null) {
             System.out.println("链表为空");
             return;
         }
         HeroNode temp = head.next;
         while (temp != null) {
             System.out.println(temp);
             // 移动到下一个节点继续遍历
             temp = temp.next;
         }
     }

    /**
     * 修改节点信息
     * @param newHeroNode   新节点
     */
    public void update(HeroNode newHeroNode){
        // 现判断节点是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        // 根据节点的no查找节点，需要临时节点辅助遍历
        HeroNode temp = head.next;
        System.out.println("temp的内存地址：" + temp.hashCode());
        boolean flag = false;
        //判断链表是否为空
        while (temp != null) {
            if(temp.no == newHeroNode.no){
                flag = true;
                temp.name = newHeroNode.name;
                temp.nickname = newHeroNode.nickname;
                break;
            }
            temp = temp.next;
        }
        if(!flag) {
            System.out.printf("没有找到编号为 %d 的节点，不能修改\n", newHeroNode.no);
        }
    }

    /**
     * 删除节点信息   <p>
     * 1：遍历找到目标节点   <p>
     * 2：修改next属性
     * @param no   要删除的节点的no
     */
    public void del(int no){
        HeroNode temp = head;
        boolean flag = false;
        while(true){
            if(temp.next == null){
                break;
            }
            if(temp.next.no == no){
                // 找到待删除节点的前一个节点temp
                flag = true;
                break;
            }
            // temp后移, 遍历
            temp = temp.next;
        }
        if(flag){
           // 删除
           temp.next = temp.next.next;
        } else{
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }

    /**
     * 获取有效节点的个数
     * @param head  头节点
     * @return  节点个数
     */
    public int getLength(HeroNode head){
        if(head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    /**
     * 查找单链表中的倒数第k个节点
     * @param head  头节点
     * @return  倒数第k个节点
     */
    public HeroNode findLastIndexNode(HeroNode head, int index){
        if(head.next == null) {
            return null;
        }
        int size = getLength(head);
        //  可以先对index进行校验
        if(index <=0 || index > size) {
            return null;
        }
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 反转单链表
     * @param head  头节点
     */
    public void reverseList(HeroNode head){
        //  如果存在空链表和单节点链表，则不需要反转就可以
        if(head.next == null || head.next.next == null) {
            return;
        }
        HeroNode cur = head.next;
        //  定义一个存储下一个节点的空白节点
        HeroNode next;
        //将原来链表中的每一个节点依次取出，依次反转插入到新的链表中。因此，这里需要创建一个新链表的头节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        //  遍历取出
        while(cur != null){
            //  先保留住下一个节点，以便这个节点供下次使用
            next = cur.next;
            // 将cur的下一个节点指向新的链表的最前端
            cur.next = reverseHead.next;
            // 将要插入节点紧跟在头节点的后面
            reverseHead.next = cur;
            // cur后移
            cur = next;
        }
        //  将head.next 指向 reverseHead.next, 实现单链表的反转
        head.next = reverseHead.next;

    }

    public void reversePrint(HeroNode head){
        // 空链表, 不能打印
        if(head.next == null) {
            return;
        }
        HeroNode cur = head.next;
        Stack<HeroNode> heroNodesStack = new Stack<>();
        //  压栈
        while(cur != null){
            heroNodesStack.push(cur);
            cur = cur.next;
        }
        while(heroNodesStack.size() > 0) {
            // 出栈
            System.out.println(heroNodesStack.pop());
        }
    }

}


/**
 * 定义HeroNode，每个HeroNode对象都是一个节点
 */
class HeroNode{
    /** 定义节点信息(数据域) */
    public int no;
    public String name;
    public String nickname;
    /** 指向下一个节点(指针域) */
    public HeroNode next;

    /** 构造器 */
    public HeroNode(int no, String name, String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" + "no=" + no + ", name='" + name + '\'' + ", nickname='" + nickname + '}';
    }
}