package __11排序算法;/*
    @author wxg
    @date 2021/9/19-10:24
    */

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 23, -567, 7000, -1, 900, 4561};
//          int[] arr = {5, 1, 2, 3, 4, 6, 7, 8, 9};
        //测试快排的执行速度
        // 创建要给80000个的随机的数组
//        int[] arr = new int[8000000];
//        for (int i = 0; i < 8000000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

//        quickSort(arr, 0, arr.length - 1);
        quickSort2(arr, 0, arr.length - 1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
        //System.out.println("arr=" + Arrays.toString(arr));
    }

    /**
     * 快排的本质根据是根据数值的大小进行排序，并不限定基准值一定要为中间位置的值
     * @param arr   待排序的数组
     * @param left  0
     * @param right arr.length-1
     */
    private static void quickSort(int[] arr, int left, int right) {
        //pivot 中轴值，这里的中轴值是一个确定的值，而不是一个确定的位置。也就是说，pivot所代表的值的索引未必是数组的中间位置
        int pivot = arr[(left + right) / 2];
        int l = left; //左下标
        int r = right; //右下标
        int temp; //临时变量，作为交换时使用
        //  到这里l和r的大小与(left + right) / 2没法比较
        while (l < r) {
            //在pivot的左边一直找,找到大于等于pivot值,才退出
            while (arr[l] < pivot) l += 1;
            //在pivot的右边一直找,找到小于等于pivot值,才退出
            while (arr[r] > pivot) r -= 1;
            //如果l >= r说明pivot 的左右两边的值，已经按照左边全部是小于等于pivot值，右边全部是大于等于pivot值
            if (l >= r) break;
            //走到这里，说明已经在左边找到了比arr[pivot]大的值，在右边找到了比arr[pivot]小的值，因此可以进行交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            System.out.println(Arrays.toString(arr));
            //如果交换完后，发现这个arr[l] == pivot值 相等 r--， 前移
            if (arr[l] == pivot) r -= 1;
            //如果交换完后，发现这个arr[r] == pivot值 相等 l++， 后移
            if (arr[r] == pivot) l += 1;
            System.out.printf("左索引的位置为：%d, 右索引的位置为：%d \n", l, r);
        }

        // 如果 l == r, 必须l++, r--, 否则出现栈溢出
        if (l == r) {
            l++;
            r--;
        }
        //向左递归
        if (left < r) quickSort(arr, left, r);
        //向右递归
        if (right > l) quickSort(arr, l, right);
    }

    public static void quickSort2(int[] nums, int left, int right) {
        if (nums.length == 0 || left > right) return;
        int i = left;
        int j = right;
        //这个key是会变的，以每次数组的最左边一个数为key，即比较的对象，i往右移遇到比key大的数就停住，j往左移遇到比key小的数就停住，两个都停住时
        int key = nums[left];
        while (i != j) {
            while (nums[j] >= key && j > i) j--;
            while (nums[i] <= key && i < j) i++;
            //nums[i]与nums[j]互换，前提是i<j(大前提)
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        System.out.println(Arrays.toString(nums));
        //nums[i]永远会比key小，因为它会与nums[j]互换，而nums[j]只有比key小的时候才会停住
        nums[left] = nums[i];
        nums[i] = key;
        quickSort2(nums, left, i - 1);
        quickSort2(nums, i + 1, right);

    }
}
