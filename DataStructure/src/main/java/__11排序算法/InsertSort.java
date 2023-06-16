package __11排序算法;/*
    @author wxg
    @date 2021/9/15-19:58
    */

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 89};
        // 创建要给80000个的随机的数组
//        int[] arr = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }

        System.out.println("插入排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        insertSort(arr); //调用插入排序算法

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);

        //System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * @param arr   待排序的数组
     */
    private static void insertSort(int[] arr) {
        int insertVal;
        int insertIndex;
        //  这里表示将 索引1 到 索引length-1 依次插入
        for (int i = 1; i < arr.length; i++) {
            //  定义要插入的数,用一个变量先装起来
            insertVal = arr[i];
            //  将arr[i]的前一个数字确定下来
            insertIndex = i - 1;
            //  这个循环只是为了找到插入值要插入的索引位置，插入的位置就是insertIndex，这里的insertIndex值得是插入位置的前一个位置
            while(insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }
            //  一旦退出循环，则意味着找到了要插入的所索引，进行赋值
            if(insertIndex+1 != i) arr[insertIndex+1] = insertVal;
            System.out.printf("第%d轮插入", i);
            System.out.println(Arrays.toString(arr));
        }
    }

}
