public class StringIndex {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("110001011101000110010111010001100101110100011001111011100101111100001000111110001010111110100100101110100011001111011100101111100010111010001100101110100011");
        String str = stringBuilder.substring(153,156);
        System.out.println(str);
    }
}
