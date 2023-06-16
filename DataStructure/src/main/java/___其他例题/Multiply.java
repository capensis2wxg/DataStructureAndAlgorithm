package ___其他例题;/*
    @author wxg
    @date 2021/9/11-16:47
    */

import java.util.Arrays;

public class Multiply {
    public static void main(String[] args) {
        Multiply multiply = new Multiply();
        String product = multiply.multiply("25", "25");
        System.out.println(product);
    }

    /**
     * 计算两个数字型字符串的乘积
     * @param num1  数字型字符串1
     * @param num2  数字型字符串2
     * @return  乘积
     */
    public String multiply(String num1, String num2) {
        char[] ch1 = num1.toCharArray();
        char[] ch2 = num2.toCharArray();
        int length1 = ch1.length;
        int length2 = ch2.length;
        char[] ret = new char[length1 + length2];
        Arrays.fill(ret, '0');
        if (length1 == 0 || length2 == 0) return "";
        for (int i = length1 - 1; i >= 0; i--) {
            int add = 0;
            for (int j = length2 - 1; j >= 0; j--) {
                int mul = (ch1[i] - '0') * (ch2[j] - '0');
                int sum = ret[i + j + 1] + add + mul % 10 - '0';
                //  表示1的个数
                ret[i + j + 1] = (char) (sum % 10 + '0');
                //  表示10的个数
                add = mul / 10 + sum / 10;
            }
            //  表示第一个因子从低位到高位与另一个因子相乘后得到多少个10
            ret[i] += add;
        }
        String str = String.valueOf(ret);
        for (int i = 0; i < length1 + length2; i++) {
            if (ret[i] != '0') {
                return str.substring(i);
            }
        }
        return "0";
    }
}
