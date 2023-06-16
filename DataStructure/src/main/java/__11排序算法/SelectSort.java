package __11排序算法;/*
    @author wxg
    @date 2021/9/15-19:23
    */

import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
//        int [] arr = {101, 34, 119, 1, -1, 90, 123};
        int[] arr = new int[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = (int) (Math.random() * 80000); // 生成一个[0, 8000000) 数
        }
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));
        long start = System.currentTimeMillis();
        System.out.println(start);
        selectSort(arr);
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.printf("时间复杂度为：%d", end - start);
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 选择排序的时间复杂度为O(n^2)
     * @param arr   待排序的数组
     */
    public static void selectSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            //  定义一次排序最小值的索引
            int minIndex = i;
            // 将每个位置的最小值用一个变量保存起来
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if(min > arr[j]) {
                    //  遇到比这个最小值还要小的值就及时更新保存最小的变量
                    min = arr[j];
                    //  同时记录一下这个最小值的索引位置，直到比较结束，可以用来交换arr[0]和arr[minIndex]的值
                    minIndex = j;
                }
            }
            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            System.out.printf("第%d轮后---", i+1);
            System.out.println(Arrays.toString(arr));
        }
    }
}
