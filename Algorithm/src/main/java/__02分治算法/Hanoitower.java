package __02分治算法;

/**
 * @author capensis
 */
@SuppressWarnings({"IfCanBeSwitch", "SingleStatementInBlock"})
public class Hanoitower {
    public static void main(String[] args) {
        hanoiTower(8, 'A', 'B', 'C');
    }

    /**
     * 使用分治算法移动汉诺塔
     * @param num 塔牌的数量
     * @param a     source
     * @param b     mid
     * @param c     dest
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        //如果只有一个盘
        if(num == 1) {
            System.out.println("第1个盘从 " + a + "->" + c);
        } else {
            //如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的一个盘 2. 上面的所有盘
            //1. 先把最上面的所有盘 A->B， 移动过程会使用到 c
            hanoiTower(num - 1, a, c, b);
            //2. 把最下边的盘 A->C
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            //3. 把B塔的所有盘 从 B->C , 移动过程使用到 a塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
