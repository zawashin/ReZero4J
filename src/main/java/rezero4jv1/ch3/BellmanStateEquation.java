// 2マスのグリッドワールドを例題としたベルマン状態方程式
// https://www.anarchive-beta.com/entry/2022/05/22/180000
package rezero4jv1.ch3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BellmanStateEquation {

    public static void main(String[] args) {
        // 試行回数(kの最大値)を指定
        int kMax = 100;
        // 割引率を指定
        double gamma = 0.9;
        // 状態の初期値を指定:(作図用)
        int state0 = 1;
        // 状態を初期化
        int state = state0;
        // 左に行動する確率を指定
        double p_left = 0.5;
        // 確率的方策を作成
        double[] p_a = {p_left, 1.0 - p_left};
        Random random = new Random();
        final int left = 0;
        final int right = 1;

        // 収益を初期化
        double G = 0;

        // 記録用のリストを初期化
        ArrayList<Double> trace_G = new ArrayList<>();

        // 繰り返し試行
        for(int k = 0; k < kMax; k++) {
            // ランダムに行動を決定
            //int a = rnd.nextInt(2);
            double rnd = random.nextDouble();
            int action;
            if(rnd < p_a[0]) {
                action = left;
            } else {
                action = right;
            }
            double r = 0.0;

            if (state == 1) {
                // 現在の状態がL1の場合
                if (action == left) {
                    // 行動が左の場合(壁にぶつかる場合)
                    // 報酬を指定
                    r = -1.0;
                } else if (action == right) {
                    // 行動が右の場合
                    // 状態をL2に更新
                    state = 2;

                    // 報酬を指定
                    r = 1.0;
                }
            } else if (state == 2) {
                // 現在の状態がL2の場合
                if (action == left) {
                    // 行動が左の場合
                    // 状態をL1に更新
                    state = 1;

                    // 報酬を指定
                    r = 0.0;
                } else if (action == right) {
                    // 行動が右の場合
                    // 行動が右の場合(壁にぶつかる場合)
                    // 報酬を指定
                    r = -1.0;
                }
            }

            // 収益を計算
            G += Math.pow(gamma, k) * r;

            // 値を記録
            trace_G.add(G);
        }
        System.out.println(G);
        for(int k = 0; k < kMax;k++) {
            System.out.println(k + "\t" + trace_G.get(k));
        }
        // 最終結果を確認

        // 真の状態価値を指定
        double v_s = -2.25;
        // x軸の値を作成
        double[] k_vals = new double[kMax];
        for(int k = 0; k < k_vals.length; k++) {
            k_vals[k] = v_s;
        }

        //(1)データセットの作成
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
        JFreeChart chart = ChartFactory.createXYLineChart("Bellman State Equation", "k", "value", ds_xy);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2));
        float dash [] = {4f, 5f};
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(2f,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dash, 0.0f));

        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN | Font.ITALIC, 18);
        chart.getTitle().setFont(font);

        font = new Font("Times New Roman", Font.PLAIN | Font.ITALIC, 20);
        chart.getLegend().setItemFont(font);

        //(3)出力処理
        ChartFrame frame = new ChartFrame("Bellman State Equation", chart);
        frame.setSize(912, 770);
        frame.setVisible(true);
    }
 }
