package rezero4jv1.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
 * https://stackoverflow.com/questions/1786206/is-there-a-java-equivalent-of-pythons-defaultdict
 */
public class DictOld<K, V> {

    ArrayList<K> keyList;
    ArrayList<V> valueList;

    public DictOld() {
        keyList = new ArrayList<K>();
        valueList = new ArrayList<V>();
    }

    public static void main(String[] args) {
        DictOld<int[], Double> dict = new DictOld<>();
        dict.add(new int[]{0, 0}, 3.0);
        dict.add(new int[]{0, 0}, 3.0);
        dict.add(new int[]{1, 1}, 2.0);
        dict.get(new int[]{0, 0});
        dict.get(new int[]{0, 1});
        dict.get(new int[]{1, 1});

        System.out.println(dict.get(new int[]{0, 0}));
        System.out.println(dict.get(new int[]{3, 1}));
        System.out.println(dict.get(new int[]{1, 1}));

        DictOld<String, Integer> dict2 = new DictOld<>();
        dict2.add("0,0", 4);
        dict2.add("0,1", 6);
        dict2.get("0,0");
        dict2.get("0,1");

        System.out.println(dict2.get("0,0"));
        System.out.println(dict2.get("0,1"));
        System.out.println(dict2.get("0,3"));

    }

    public V get(K key) {
        int n = keyList.size();
        for (int k = 0; k < n; k++) {
            if (key.getClass().isArray()) {
                // キーが配列の場合
                /*
                 * エラー
                if (Arrays.equals(key, keyList.get(k))) {
                    return (V) valueList.get(k);
                }
                 */
                if (key.getClass() == int[].class) {
                    if (Arrays.equals((int[]) key, (int[]) keyList.get(k))) {
                        return (V) valueList.get(k);
                    }
                } else if (key.getClass() == double[].class) {
                    if (Arrays.equals((double[]) key, (double[]) keyList.get(k))) {
                        return (V) valueList.get(k);
                    }
                } else if (key.getClass() == boolean[].class) {
                    if (Arrays.equals((boolean[]) key, (boolean[]) keyList.get(k))) {
                        return (V) valueList.get(k);
                    }
                } else {
                    try {
                        System.out.println("Unimplemented case: " + key.getClass());
                        throw new Exception();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                // キーが配列でない場合
                if (Objects.equals((K) key, (K) (keyList.get(k)))) {
                    return (V) valueList.get(k);
                }
            }
        }
        return null;
    }

    public void add(K key, V value) {
        keyList.add(key);
        valueList.add(value);
    }

}
