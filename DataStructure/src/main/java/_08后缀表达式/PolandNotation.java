package _08后缀表达式;/*
    @author wxg
    @date 2021/9/9-21:23
    */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        ArrayList<String> list = getListing(suffixExpression);
        int calculate = calculate(list);
        System.out.println("计算的结果是：" + calculate);


    }

    /**
     * 将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
     * @param suffixExpression  逆波兰表达式
     * @return  集合
     */
    public static ArrayList<String> getListing(String suffixExpression){
        //将suffixExpression分割
        String[] split = suffixExpression.split(" ");
        return new ArrayList<>(Arrays.asList(split));
    }

    public static int calculate(ArrayList<String> list){
        Stack<String> stack = new Stack<>();
        for (String item : list) {
            if(item.matches("\\d+")){
                //  入栈
                stack.push(item);
            }else{
                //pop出两个数，并运算，将计算的结果在压入栈中
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res;
                switch (item) {
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num1 / num2;
                        break;
                    default:
                        throw new RuntimeException("运算符有误");
                }
                //  把res压栈
                stack.push("" + res);
            }
        }
        // 最后留在stack中的数据就是运算结果
        return Integer.parseInt(stack.pop());

    }

}
