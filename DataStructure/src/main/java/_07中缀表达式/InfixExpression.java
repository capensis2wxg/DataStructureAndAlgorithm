package _07中缀表达式;/*
    @author wxg
    @date 2021/9/9-19:17
    */

public class InfixExpression {
    //使用栈计算中缀表达式的值
    public static Object calculate(String expression) {
        /* 此处使用数组的方式处理栈
          思路：1.创建两个栈，一个栈用于存放数字，一个栈用于存放符号
               2.扫描字符串，取出每一个字符
               3.判断取出的字符如果是数字直接入数字栈，如果取出的字符是运算符，以下几步操作
                  3.1）判断栈是否为空，为空直接入栈，
                  3.2）不为空，将此运算符和符号栈的栈顶符号比较优先级，
                      3.2.1）优先级高于栈顶元素，直接入栈，
                      3.2.2）优先级低于或等于栈顶优先级，将数字栈中弹出两个数字，符号栈中弹出符号运算，
                             在将运算的结果入数字栈，在将此时的符号入符号栈。
                4.字符串扫描完成后依次从数字栈中取两个数字，符号栈中取一个符号进行运算，在将运算的结果
                   存放在数字栈中
                5.处理完成后，数字栈中还剩一个数字就是所得结果，符号栈为空。
         */
        int size = expression.length();
        //创建数字栈
        ArrayImplStack numStack = new ArrayImplStack(size);
        //创建符号栈
        ArrayImplStack opStack = new ArrayImplStack(size);
        //下标
        int index = 0;
        //用于拼接多位数字
        String numStr = "";
        do {
            //获取字符串的每个字符
            int ch = expression.substring(index, index + 1).charAt(0);
            //判断是数字还是符号
            if (InfixExpressionTools.isOperator(ch)) {
                //表示是操作符
                //判断操作符栈是否为空
                if (opStack.isEmpty()) {
                    //为空，直接入栈
                    opStack.push(ch);
                } else {
                    //不为空，将ch 与符号栈的栈顶元素比较优先级
                    //if (InfixExpressionTools.priority(ch) >InfixExpressionTools.priority((int) opStack.pop()))
                    // 原来这种写法有bug,这里不需要弹出数据，只需要获取最后一个元素
                    if (InfixExpressionTools.priority(ch) > InfixExpressionTools.priority((int) opStack.peek())) {
                        //优先级高于栈顶的符号优先级，直接入栈
                        opStack.push(ch);
                    } else {
                        //低于或等于栈顶优先级, 数字栈中弹出两个数字, 符号栈中弹出一个符号
                        Object num1 = numStack.pop();
                        Object num2 = numStack.pop();
                        int op = (int) opStack.pop();
                        //执行运算
                        int result = InfixExpressionTools.calculate(op, (int) num1, (int) num2);
                        //运算的结果入数字栈
                        numStack.push(result);
                        //将此时的符号入符号栈
                        opStack.push(ch);
                    }
                }
            } else {
                //表示是数字，直接入数字栈,需要考虑多位数的计算
                //将ch的值转换成数字
                ch = ch - 48;
                numStr += ch;
                //判断是不是最后一位，如果是直接加入
                if (index == size - 1) {
                    //最后一位
                    numStack.push(Integer.parseInt(numStr));
                } else {
                    //不是最后一位，需要判断下一位是否是操作符
                    if (InfixExpressionTools.isOperator(expression.substring(index + 1, index + 2).charAt(0))) {
                        numStack.push(Integer.parseInt(numStr));
                        //  一定要置为空
                        numStr = "";
                    }

                }
            }
            //扫描完成跳出循环
            index++;
        } while (index != size);
        //字符串扫描完成后依次从数字栈中取两个数字，符号栈中取一个符号进行运算，在将运算的结果存放在数字栈中
        while (true) {
            //循环链表
            if (opStack.isEmpty()) break;
            int num1 = (int) numStack.pop();
            int num2 = (int) numStack.pop();
            int ch = (int) opStack.pop();
            int result = InfixExpressionTools.calculate(ch, num1, num2);
            //运算的结果入数字栈
            numStack.push(result);
        }
        return numStack.pop();
    }

    public static void main(String[] args) {
        String expression = "1*5+2-100*5";
        Object calculate = InfixExpression.calculate(expression);
        System.out.println("最后运算的结果" + calculate);
    }

}
