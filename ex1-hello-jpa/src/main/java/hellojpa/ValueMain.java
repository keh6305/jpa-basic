package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
//        int a = 20;
//        int b = a;

//        b = 10;

//        Integer a = new Integer(10);
//        Integer b = a;

//        System.out.println("a = " + a);
//        System.out.println("b = " + b);

        Address address1 = new Address("city", "street", "1005");
        Address address2 = new Address("city", "street", "1005");

        System.out.println("(address1 == address2) = " + (address1 == address2));
        System.out.println("address1.equals(address2) = " + address1.equals(address2));
    }
}