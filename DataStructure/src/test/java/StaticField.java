public class StaticField {
    private static int number = 1;

    public static void main(String[] args) {
        System.out.println(number);
        operation();
        System.out.println(number);

    }

    public static int operation(){
        number = 3 * 4;
        return number;
    }
}
