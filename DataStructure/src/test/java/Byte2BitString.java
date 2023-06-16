public class Byte2BitString {
    public static void main(String[] args) {
        System.out.println(byteToBitString(false, (byte) 3));
    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b){
        //使用变量保存 b
        int temp = b; //将 b 转成 int
        //如果是正数我们还存在补高位
        // |= 两个二进制对应位为0时该位为0，否则为1
        if(flag) temp |= 256; //按位与 256  1 0000 0000  | 0000 0001 => 1 0000 0001(补码),再将这个补码转成原码
        System.out.println(temp);
        String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制的补码1 0000 0001
        System.out.println(str);
        if(flag) return str.substring(str.length() - 8);
        else return str;

    }
}
