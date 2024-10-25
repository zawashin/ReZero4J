package rezero4jv1.misc;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ArrayTest {

    public static void main(String[] args) {

        int[] intArray = new int[0];
        double[] doubleArray = new double[0];
        boolean[] booleanArray = new boolean[1];
        System.out.println(intArray.getClass());
        System.out.println(doubleArray.getClass());
        System.out.println(booleanArray.getClass());
        if(booleanArray instanceof boolean[]) {
            System.out.println("boolean");
        }
    }
}
