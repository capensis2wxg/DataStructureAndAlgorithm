import org.junit.Test;

public class RTest {
    public static void main(String[] args) {

    }


    @Test
    public void test(){
        int a = 3;
        int b = a;
        b = b + 3;
        System.out.println(a);

    }


    @Test
    public void TestA(){
        A a = new A();
        A b = a;
        System.out.println(a==b);   // true
        b.setName("jack");
        System.out.println(a.getName());

    }


}


class A{
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}