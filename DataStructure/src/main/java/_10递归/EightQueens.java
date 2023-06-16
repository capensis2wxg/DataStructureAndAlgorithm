package _10递归;/*
    @author wxg
    @date 2021/9/11-21:26
    */

public class EightQueens {
    //定义一个max表示共有多少个皇后
    int max = 8;
    int[] array = new int[max];
    //  记录总共有多少种摆法
    static int count = 0;
    static int judgeCount = 0;

    public static void main(String[] args) {
        //测试一把, 8皇后是否正确
        EightQueens queue8 = new EightQueens();
        queue8.check(0);
        System.out.printf("一共有%d解法", count);
        System.out.printf("一共判断冲突的次数%d次", judgeCount); // 1.5w

    }

    /**
     * 编写一个方法，放置第n个皇后
     * 特别注意：
     *    这里是存在回溯情形的，原因如下：
     *          如果当n=7时, array[n] = i一旦执行，就会进入if(judge(n)),如果judge()返回true, 那么随机就进入了check(), 第八个也就随着确定，并返回ture;
     *          但是，此时n=7下的 for(int i = 0; i < max; i++){}并未走完，只走到了array[n] = i，剩下的循环继续走;
     *          如果走通，就找到了一种摆法；如果走不通，check(7)就走完了,可是外层的check(6)的循环才执行到arr[n] = i
     *          如此循环，把所有的摆法都找到.
     * @param n 摆放第几个位置
     */
    private void check(int n) {
        if(n == max) {  //n = 8 , 其实8个皇后就既然放好
            print();
            return;
        }
        for(int i = 0; i < max; i++){
            array[n] = i;
            if(judge(n)) check(n+1);
            //如果冲突，就继续执行array[n] = i; 即将第n个皇后，放置在本行的后移的一个位置
        }

    }


    /**
     * 查看当我们放置第n个皇后, 就去检测该皇后是否和前面已经摆放的皇后冲突
     * @param n 表示第n个皇后
     * @return true || false
     */
    private boolean judge(int n) {
        judgeCount++;
        /*
			1. array[i] == array[n]  表示判断 第n个皇后是否和前面的n-1个皇后在同一列
			2. Math.abs(n-i) == Math.abs(array[n] - array[i]) 表示判断第n个皇后是否和第i皇后是否在同一斜线
			    n = 1  放置第 2列 1 n = 1 array[1] = 1
			    Math.abs(1-0) == 1  Math.abs(array[n] - array[i]) = Math.abs(1-0) = 1
			3. 判断是否在同一行, 没有必要，n 每次都在递增
         */
        for(int i = 0; i < n; i++) if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])) return false;
        return true;
    }

    /**
     * 将皇后摆放的位置输出
     */
    private void print() {
        count++;
        for (int j : array) System.out.print(j + " ");
        System.out.println();
    }

}
