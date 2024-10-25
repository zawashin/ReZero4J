package rezero4jv1.misc;

import java.util.ArrayDeque;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ArrayDequeTest {
    public static void main(String[] args) {
        int max = 2;
        ArrayDeque<String> ad = new ArrayDeque<String>(max);
        System.out.println(ad.size());
        ad.add("111");
        System.out.println(ad.getFirst() + " + " + ad.getLast());
        System.out.println(ad.size());
        ad.add("222");
        System.out.println(ad.getFirst() + " + " + ad.getLast());
        System.out.println(ad.size());
        ad.add("333");
        if(ad.size() > max) {
            ad.pollFirst();
        }
        System.out.println(ad.getFirst() + " + " + ad.getLast());
        System.out.println(ad.size());
        ad.add("444");
        if(ad.size() > max) {
            ad.pollFirst();
        }
        System.out.println(ad.getFirst() + " + " + ad.getLast());
        System.out.println(ad.size());
    }
}