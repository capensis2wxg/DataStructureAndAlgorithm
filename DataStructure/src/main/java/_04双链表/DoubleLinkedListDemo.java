package _04双链表;
/**
 * @author capensis
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) throws InterruptedException {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        //  创建几个节点对象
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        System.out.println("================================未添加前遍历==============================");
        doubleLinkedList.list();
        System.out.println("================================添加之后遍历===============================");
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        doubleLinkedList.list();
        System.out.println("================================修改hero1的节点信息==========================");
        HeroNode heroNodeUpdate = new HeroNode(1, "黑三郎", "呼保义");
        doubleLinkedList.update(heroNodeUpdate);
        doubleLinkedList.list();
        //  错误示范
        HeroNode heroNodeUpdate1 = new HeroNode(5, "黑三郎", "呼保义");
        doubleLinkedList.update(heroNodeUpdate1);
        doubleLinkedList.list();
        System.out.println("================================删除hero1的节点信息=========================");
        doubleLinkedList.del(1);
        doubleLinkedList.list();
        //  错误示范
        doubleLinkedList.del(5);
        // 这里会循环调用，因为add()中的temp=hero4的地址内容被修改,造成hero1中的hero4地址内容也被修改
        //doubleLinkedList.add(hero1);
        doubleLinkedList.add(new HeroNode(1, "宋江", "及时雨"));
        doubleLinkedList.list();
        }
    }



class DoubleLinkedList {
    /** 先初始化一个节点，头节点不要动，不存放具体的数据 */
    private final HeroNode head = new HeroNode(0, "", "");

    /**
     * 添加节点
     * @param heroNode  节点
     * @keyPoint  使用辅助节点
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
        heroNode.pre = temp;
    }


    /**
     * 遍历双向链表的节点
     * @keyPoint 使用辅助节点
     */
    public void list() {
        int count = 0;
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        while (temp != null) {
            System.out.println(temp);
            count++;
            // 移动到下一个节点继续遍历
            temp = temp.next;
            if(count > 12){
               break;
            }
        }
    }

    /**
     * 修改节点信息
     * @param newHeroNode   新节点
     * @keyPoint 这里的修改是一种基于内存地址的修改
     */
    public void update(HeroNode newHeroNode){
        // 现判断节点是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        // 根据节点的no查找节点，需要临时节点辅助遍历
        HeroNode temp = head.next;
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
        // 如果这里flag为false, 说明没有找到对应的节点
        if(!flag) {
            System.out.printf("没有找到编号为 %d 的节点，不能修改\n", newHeroNode.no);
        }
    }

    /**
     * 删除节点信息
     * 1：遍历找到目标节点
     * 2：修改pre和next属性，通过自我删除
     * @param no   要删除的节点的no
     */
    public void del(int no){
        if(head.next == null) {
            System.out.println("链表为空，无法删除");
            return;
        }
        HeroNode temp = head.next;
        while(true){
            if(temp.no == no) {
                //  删除操作
                temp.pre.next = temp.next;
                //如果要删除最后一个节点，那么 temp.next.pre = temp.pre 就会出现空指针
                if(temp.next != null) {
                    temp.next.pre = temp.pre;
                }
                // 保证只删除第一次出现的带有相等no的节点
                break;
            }
            temp = temp.next;
            if(temp == null) {
               //   如果要删除的节点不存在，则说明遍历后temp.next = null
                System.out.printf("要删除的 %d 节点不存在\n", no);
                break;
            }
        }
    }
}


/**
 * 双向链表的节点
 */
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    /** 指向上一个节点，默认为null */
    public HeroNode pre;
    /** 指向下一个节点, 默认为null */
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" + "no=" + no + ", name='" + name + '\'' + ", nickname='" + nickname + '\'' + '}';
    }

}