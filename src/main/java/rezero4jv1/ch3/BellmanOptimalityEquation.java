// 3.5
// https://www.anarchive-beta.com/entry/2022/05/22/180000
// 2マスのグリッドワールド
package rezero4jv1.ch3;

import rezero4jv1.common.ArrayUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BellmanOptimalityEquation {

    public static void main(String[] args) {
        // 試行回数(kの最大値)を指定
        int kMax = 100;
        // 割引率を指定
        double gamma = 0.9;
        final int left = 0;
        final int right = 1;
        // 状態の初期値を指定:(作図用)
        // 状態を初期化
        int state = left;
        // 収益を初期化
        double G = 0.0;

        // 記録用のリストを初期化
        ArrayList<Double> trace_G = new ArrayList<>();

        int[] actions = {left, right};
        double reword;
        double[] rewards = new double[2];
        int a_idx;
        int action;

        // 繰り返し試行
        for (int k = 0; k < kMax; k++) {
            reword = 0.0;
            if (state == left) {
                // 現在の状態がL1の場合
                // 報酬を指定
                rewards[left] = -1.0;
                rewards[right] = 1.0;

                // 最適(報酬が最大)な行動を選択
                a_idx = ArrayUtils.argmax(rewards);
                action = actions[a_idx];

                // 最適な行動による報酬を取得
                reword = Math.max(rewards[left], rewards[right]);

                // 行動が左の場合:(壁にぶつかる場合)
                if (action == left) {
                    // 何もしない
                } else if (action == right) {
                    // 状態をL2に更新
                    state = right;
                }
            } else if (state == right) {
                // 現在の状態がL2の場合
                // 報酬を指定
                rewards[left] =  0.0;
                rewards[right] = -1.0;

                // 最適(報酬が最大)な行動を選択
                a_idx = ArrayUtils.argmax(rewards);
                action = actions[a_idx];

                // 最適な行動による報酬を取得
                reword = Math.max(rewards[left], rewards[right]);

                // 行動が左の場合
                if (action == left) {
                    // 状態をL1に更新
                    state = left;
                } else if (action == right) {
                    // 行動が右の場合:(壁にぶつかる場合)
                    // 何もしない
                }
            }

            // 収益を計算
            G += Math.pow(gamma, k) * reword;

            // 値を記録
            trace_G.add(G);
        }

        // 最終結果を確認
        System.out.println(G);
        for(int k = 0; k < kMax;k++) {
            System.out.println(k + "\t" + trace_G.get(k));
        }
// 真の最適状態価値を指定
        double true_v = 5.26;
        double[] k_vals = new double[kMax];

// x軸の値を作成
        Arrays.fill(k_vals, true_v);

        String[] keys = {"Gt", "V(s)"};
        XYSeries xy1 = new XYSeries(keys[0]);
        for(int k = 0; k < kMax; k++) {
            xy1.add(k, trace_G.get(k));
        }
        XYSeries xy2 = new XYSeries(keys[1]);
        for(int k = 0; k < k_vals.length; k++) {
            xy2.add(k, k_vals[k]);
        }
        XYSeriesCollection ds_xy = new XYSeriesCollection();
        ds_xy.addSeries(xy1);
        ds_xy.addSeries(xy2);

        //(2)チャートの作成
        JFreeChart chart = ChartFactory.createXYLineChart("Bellman Optimal Equation", "k", "value", ds_xy);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2));
        float[] dash = {4f, 5f};
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(2f,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dash, 0.0f));

        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN | Font.ITALIC, 20);
        chart.getTitle().setFont(font);

        font = new Font("Times New Roman", Font.PLAIN | Font.ITALIC, 18);
        chart.getLegend().setItemFont(font);

        //(3)出力処理
        ChartFrame frame = new ChartFrame("Bellman Optimal Equation", chart);
        frame.setSize(912, 770);
        frame.setVisible(true);
    }
}
