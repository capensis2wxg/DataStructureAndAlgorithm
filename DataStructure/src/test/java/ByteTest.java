import java.util.ArrayList;
import java.util.Arrays;

public class ByteTest {
    public static void main(String[] args) {
        byte[] b = {97, 98, 99, 100, 1};
        System.out.println(Arrays.toString(b));
        System.out.println(new String(b));

        ArrayList<Byte> list = new ArrayList<>();
        list.add((byte) 'a');
        list.add((byte) 97);
        list.add((byte) 98);
        System.out.println(new String(String.valueOf(list)));
    }
}
