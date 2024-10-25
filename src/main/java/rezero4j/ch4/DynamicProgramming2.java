package rezero4j.ch4;

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
public class DynamicProgramming2 {
    public static void main(String[] args) {
        final int L1 = 0;
        final int L2 = 1;
        double[] value = new double[2];
        value[L1] = value[L2] = 0.0;

        double[] newValue = value.clone();
        ArrayList<double[]> traceV = new ArrayList<>();

        int cnt = 0;
        traceV.add(value);
        while(true) {
            newValue[L1] = 0.5 * (-1 + 0.9 * value[L1]) + 0.5 * (1 + 0.9 * value[L2]);
            newValue[L2] = 0.5 * (0 + 0.9 * value[L1]) + 0.5 * (-1 + 0.9 * value[L2]);

            double delta = Math.abs(newValue[L1] - value[L1]);
            delta = Math.max(delta, Math.abs(newValue[L2] - value[L2]));
            value = newValue.clone();
            traceV.add(value);
            cnt++;
            System.out.println(Arrays.toString(value));
            if(delta < 0.0001) break;
        }
        System.out.println(Arrays.toString(value));
        System.out.println(cnt);
        //(1)データセットの作成
        String[] keys = {"Vk[L1]", "Vk[L2])", "Vs[L1]", "Vs[L2])"};
        XYSeries xy1 = new XYSeries(keys[0]);
        for(int k = 0; k < traceV.size(); k++) {
            xy1.add(k, traceV.get(k)[0]);
        }
        XYSeries xy2 = new XYSeries(keys[1]);
        for(int k = 0; k < traceV.size(); k++) {
            xy2.add(k, traceV.get(k)[1]);
        }
        XYSeries xy3 = new XYSeries(keys[2]);
        for(int k = 0; k < traceV.size(); k++) {
            xy3.add(k, -2.25);
        }
        XYSeries xy4 = new XYSeries(keys[3]);
        for(int k = 0; k < traceV.size(); k++) {
            xy4.add(k, -2.75);
        }
        XYSeriesCollection ds_xy = new XYSeriesCollection();
        ds_xy.addSeries(xy1);
        ds_xy.addSeries(xy2);
        ds_xy.addSeries(xy3);
        ds_xy.addSeries(xy4);

        //(2)チャートの作成
        JFreeChart chart = ChartFactory.createXYLineChart("Dynamic Programming State Equation", "k", "value", ds_xy);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2));
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(2));
        float[]  dash = {4f, 5f};
        chart.getXYPlot().getRenderer().setSeriesStroke(2, new BasicStroke(4f,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, dash, 0.0f));
        chart.getXYPlot().getRenderer().setSeriesStroke(3, new BasicStroke(4f,
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
        ChartFrame frame = new ChartFrame("Dynamic Programming State Equation", chart);
        frame.setSize(912, 770);
        frame.setVisible(true);
    }
}
