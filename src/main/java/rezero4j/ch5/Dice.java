package rezero4j.ch5;


import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dice {
    int dices;
    Random random;

    public Dice(int dices) {
        this.dices = dices;
        random = new Random(System.currentTimeMillis());
    }
   // def sample(dices=2):
    public double sample() {
        double x = 0;
        for(int i = 0; i < dices; i++) {
            x += random.nextInt(6) + 1.0;
        }
        return x;
    }

    public static void main(String[] args) {
        Dice dice = new Dice(2);

        int trial = 1000;
        double v = 0.0;
        int n = 0;

        for(int i = 0; i < trial; i++) {
            double s = dice.sample();
            n++;
            v += (s - v) / (double) n;
            System.out.println(v);

        }
    }

}
