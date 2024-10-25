package rezero4jv1.misc;

import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ExtHashMap<K, V> extends HashMap<K, V> {

    public ExtHashMap() {
        super();
    }

    public static void main(String[] args) throws Exception {
        ExtHashMap<Integer, Double> map = new ExtHashMap<>();
        map.put(1,2.0);
        map.get(2);
        System.out.println(map.get(0));
        System.out.println(map.get(1));
        System.out.println(map.get(2));
    }

    @Override
    public V get(Object key) {
        if(containsKey(key)) {
            return super.get(key);
        } else {
            // Stab
            // 結局Null対応が必要？
            return null;
        }
    }
}
