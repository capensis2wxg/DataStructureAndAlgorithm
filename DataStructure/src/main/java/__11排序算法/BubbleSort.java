package __11排序算法;/*
    @author wxg
    @date 2021/9/12-19:38
    */

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class BubbleSort {
    public static void main(String[] args) {
//        int[] arr = {0, 9, -1, 10, 5};
        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr = new int[8];
        for(int i =0; i < 8;i++) {
            //生成一个[0, 8000000) 数
            arr[i] = (int)(Math.random() * 80);
        }
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);
        System.out.println(Arrays.toString(arr));
        // 冒泡排序
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是=" + date2Str);

    }

    public static void bubbleSort(int[] arr) {
        //一趟排序确定一个值,确定
        int temp;
        // 标识变量，表示是否进行过交换
        boolean flag = false;
        for(int i = 0; i < arr.length - 1; i++) {
            // 如果前面的数比后面的数大，则交换
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            // 在一趟排序中，一次交换都没有发生过
            if (!flag) break;
            // 重置flag!!!, 进行下次判断
            else flag = false;
        }
    }
}
