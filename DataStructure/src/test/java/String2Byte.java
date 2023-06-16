public class String2Byte {
    public static void main(String[] args) {
        String str = "10101000";
        //先转成十进制
        int i = Integer.parseInt(str, 2);
        System.out.println(i);
        // 再将十进制转成byte
        System.out.println((byte)i);

    }
}
