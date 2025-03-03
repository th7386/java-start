package method;

public class MethodReturn2 {

    public static void main(String[] args) {
        changeAge(10);
        changeAge(20);
    }

    private static void changeAge(int age) {
        if (age < 18) {
            System.out.println(age + "살, 미성년자는 출입이 불가능합니다.");
            return;
        }

        System.out.println(age+"살, 입장하세요.");
    }
}
