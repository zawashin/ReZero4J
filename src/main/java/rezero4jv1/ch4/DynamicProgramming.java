package rezero4jv1.ch4;

import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class DynamicProgramming {
    public static void main(String[] args) {
        String[] keys = {"L1", "L2"};
        HashMap<String, Double> value = new HashMap<>();
        value.put(keys[0], 0.0);
        value.put(keys[1], 0.0);

        HashMap newValue = (HashMap) value.clone();

        int cnt = 0;
        while(true) {
            newValue.put(keys[0], 0.5 * (-1 + 0.9 * value.get(keys[0]))
                    + 0.5 * (1 + 0.9 * value.get(keys[1])));
            newValue.put(keys[1], 0.5 * (0 + 0.9 * value.get(keys[0]))
                    + 0.5 * (-1 + 0.9 * value.get(keys[1])));

            double delta = Math.abs((double) newValue.get(keys[0]) - value.get(keys[0]));
            delta = Math.max(delta, Math.abs(((double) newValue.get(keys[1])) - value.get(keys[1])));
            value = (HashMap) newValue.clone();
            cnt++;
            if(delta < 0.0001) break;
        }
        System.out.println(value);
        System.out.println(cnt);
    }
}
