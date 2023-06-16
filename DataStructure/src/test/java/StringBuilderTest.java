public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('a');
        stringBuilder.append('b');
        stringBuilder.append('c');
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        System.out.println(stringBuilder1);
    }
}
