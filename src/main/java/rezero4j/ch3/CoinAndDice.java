package rezero4j.ch3;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class CoinAndDice {

    public static void main(String[] args) {

        // サイコロの面の数を指定
        int numRolls = 6;
        int numCoins = 2;
        // サイコロの目を作成
        int[] x = new int[numRolls];
        // 各目の確率を作成
        double[] prob = new double[numRolls];
        for (int i = 0; i < numRolls; i++) {
            x[i] = i + 1;
            prob[i] = 1.0 / (double) numRolls;
        }
        // サイコロの目が偶数のときのコインの確率を指定

        double[] probEven = {0.2, 0.8};
        // サイコロの目が奇数のときのコインの確率を指定
        double[] probOdd = {0.5, 0.5};

        // 全ての組み合わせの和を計算
        double expectedValue = 0.0;
        for (int die = 0; die < numRolls; die++) {
            for (int coin = 0; coin < numCoins; coin++) {
                if (die % 2 != 0) {
                    // i+1が偶数の場合
                    expectedValue += prob[die] * probEven[coin] * x[die] * coin;
                } else {
                    // i+1が奇数の場合
                    expectedValue += prob[die] * probOdd[coin] * x[die] * coin;
                }
                System.out.println(x[die] + " " + coin + " " + expectedValue);
            }
        }
        System.out.println(expectedValue);
    }
}
