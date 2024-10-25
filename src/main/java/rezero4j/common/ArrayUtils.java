package rezero4j.common;

import java.util.ArrayList;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ArrayUtils {

    public static void print(double[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            System.out.println(array[i]);
        }
    }

    public static double sum(double[] array) {
        int n = array.length;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static double sum(ArrayList<Double> array) {
        int n = array.size();
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += array.get(i);
        }
        return sum;
    }

    public static int argmax(double[] array) {
        int n = array.length;
        int max = 0;
        double maxValue = array[max];
        for (int i = 1; i < n; i++) {
            if (array[i] > maxValue) {
                max = i;
                maxValue = array[i];
            }
        }
        return max;
    }

    public static int argmax(ArrayList<Double> array) {
        int n = array.size();
        int max = 0;
        double maxValue = array.get(max);
        for (int i = 1; i < n; i++) {
            if (array.get(i) > maxValue) {
                max = i;
                maxValue = array.get(i);
            }
        }
        return max;
    }

    public static int argmin(double[] array) {
        int n = array.length;
        int min = 0;
        double minValue = array[min];
        for (int i = 1; i < n; i++) {
            if (array[i] < minValue) {
                min = i;
                minValue = array[i];
            }
        }
        return min;
    }

    public static int argmin(ArrayList<Double> array) {
        int n = array.size();
        int min = 0;
        double minValue = array.get(min);
        for (int i = 1; i < n; i++) {
            if (array.get(i) < minValue) {
                min = i;
                minValue = array.get(i);
                ;
            }
        }
        return min;
    }

    public static double max(double[] array) {
        int n = array.length;
        double maxValue = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    //最大値が複数ある場合に対応
    public static int[] argmaxima(double[] xs) {
        ArrayList<Integer> list = new ArrayList<>();
        //return ArrayUtils.argmax(xs);
        double max = xs[0];
        for (int i = 1; i < xs.length; i++) {
            if (xs[i] > max) max = xs[i];
        }
        int n = 0;
        for (int i = 0; i < xs.length; i++) {
            if (xs[i] == max) list.add(i);
            n++;
        }
        int[] indexes = new int[list.size()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = list.get(i);
        }
        return indexes;
    }

    public static double max(double[][] matrix) {
        int m = matrix.length;
        double maxValue = matrix[0][0];
        for (int i = 0; i < m; i++) {
            int n = matrix[i].length;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > maxValue) {
                    maxValue = matrix[i][j];
                }
            }
        }
        return maxValue;
    }

    public static double min(double[] array) {
        int n = array.length;
        double minValue = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    public static double min(double[][] matrix) {
        int m = matrix.length;
        double minValue = matrix[0][0];
        for (int i = 0; i < m; i++) {
            int n = matrix[i].length;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < minValue) {
                    minValue = matrix[i][j];
                }
            }
        }
        return minValue;
    }

    public static double mean(double[] array) {
        int n = array.length;
        double mean = array[0];
        for (int i = 1; i < n; i++) {
            mean += array[i];
        }
        return mean / n;
    }

    public static double[] mean(double[][] values, int axis) {
        double[] meanValue = null;
        if (axis == 0) {
            int n = values.length;
            meanValue = new double[values.length];
            for (int i = 0; i < n; i++) {
                meanValue[i] = 0.0;
                for (int j = 0; j < values[i].length; j++) {
                    meanValue[i] += values[i][j];
                }
                meanValue[i] /= (double) n;
            }
            return meanValue;
        } else if (axis == 1) {
            int n = values[0].length;
            meanValue = new double[values[0].length];
            for (int j = 0; j < n; j++) {
                meanValue[j] = 0.0;
                for (int i = 0; i < values.length; i++) {
                    meanValue[j] += values[i][j];
                }
                meanValue[j] /= (double) n;
            }
            return meanValue;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static double variance(double[] array) {
        int n = array.length;
        double mean = array[0];
        for (int i = 1; i < n; i++) {
            mean += array[i];
        }
        mean /= n;
        double variance = 0.0;
        for (int i = 1; i < n; i++) {
            double d = array[i] - mean;
            variance += d * d;
        }
        return variance / n;
    }

    public static double standardDeviation(double[] array) {
        return Math.sqrt(variance(array));
    }

    public static int[][] cloneOfArray2D(int[][] source) {
        int height = source.length;
        int[][] copy = new int[height][];
        for (int j = 0; j < height; j++) {
            copy[j] = source[j].clone();
        }
        return copy;
    }

    public static double[][] cloneOfArray2D(double[][] source) {
        int height = source.length;
        double[][] copy = new double[height][];
        for (int j = 0; j < height; j++) {
            copy[j] = source[j].clone();
        }
        return copy;
    }

    public static int[][][] cloneOfArray3D(int[][][] source) {
        int height = source.length;
        int width = source[0].length;
        int[][][] copy = new int[height][width][];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                copy[j][i] = source[j][i].clone();
            }
        }
        return copy;
    }

    public static double[][][] cloneOfArray3D(double[][][] source) {
        int height = source.length;
        int width = source[0].length;
        double[][][] copy = new double[height][width][];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                copy[j][i] = source[j][i].clone();
            }
        }
        return copy;
    }
}
