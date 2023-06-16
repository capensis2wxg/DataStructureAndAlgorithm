package _07中缀表达式;/*
    @author wxg
    @date 2021/9/9-19:14
    */

public class InfixExpressionTools {
    //判断是否是操作符 + 是43, , -是45 ，*是42 ，/是47
    public static boolean isOperator(int ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
    }
    //比较运算符的优先级
    public static int priority(int ch){
        if(ch =='*' || ch == '/' ||ch == '%'){
            return 1;
        }else if(ch == '+' || ch =='-'){
            return 0;
        }else{
            return -1;
        }
    }
    //执行运算
    public static int calculate(int ch,int num1,int num2){
        int res = 0;
        switch (ch){
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
            case '%':
                res = num2 % num1;
                break;
            default:
                System.out.println("参数不合法");
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println(calculate(43, 1, 2));
    }
}
