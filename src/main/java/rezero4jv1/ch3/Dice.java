package rezero4jv1.ch3;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dice {

    public static void main(String[] args) {
        // サイコロの面の数を指定
        int num = 6;
        // サイコロの目を作成
        int[] x = new int[num];
        // 各目の確率を作成
        double[] prob = new double[num];
        for (int i = 0; i < num; i++) {
            x[i] = i + 1;
            prob[i] = 1.0 / (double) num;
        }
        System.out.println(x);

        // 期待値を計算
        double expectedValues = 0.0;
        for (int i = 0; i < num; i++) {
            expectedValues += (double) x[i] * prob[i];
        }
        System.out.println(expectedValues);

    }
}
