package __04KMP算法;

import java.util.Arrays;

/**
 * @author capensis
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        //String str2 = "BBC";
        //[0, 1, 2, 0]
        int[] next = kmpNext(str2);
        System.out.println("next=" + Arrays.toString(next));
        int index = kmpSearch(str1, str2, next);
        // 15了
        System.out.println("index=" + index);
    }

    /**
     * KMP算法匹配
     *
     * @param str1 主串
     * @param str2 字串（模式串）
     * @param next 部分匹配值
     * @return 位置
     */
    private static int kmpSearch(String str1, String str2, int[] next) {
        //遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {
            //需要处理 str1.charAt(i) ！= str2.charAt(j), 去调整j的大小
            //KMP算法核心点, 可以验证...
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            //找到了 // j = 3 i
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 获取到一个字符串(子串) 的部分匹配值表
     *
     * @param dest 字符串
     * @return 部分匹配表
     */
    private static int[] kmpNext(String dest) {
        //创建一个next 数组保存部分匹配值
        int[] next = new int[dest.length()];
        //如果字符串是长度为1 部分匹配值就是0
        next[0] = 0;
        for (int i = 1, j = 0; i < dest.length(); i++) {
            //当dest.charAt(i) != dest.charAt(j) ，我们需要从next[j-1]获取新的j
            //直到我们发现 有  dest.charAt(i) == dest.charAt(j)成立才退出
            //这是kmp算法的核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            //当dest.charAt(i) == dest.charAt(j) 满足时，部分匹配值就是+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
