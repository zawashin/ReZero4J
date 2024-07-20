package rezero4j;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Vector2 implements Cloneable, Serializable {

    /**
     *
     */
    public static final DecimalFormat VECTOR2_FORMAT = new DecimalFormat("0.0000E00");
    /**
     *
     */
    public double x;
    /**
     *
     */
    public double y;

    /**
     *
     */
    public Vector2() {
        this.x = this.y = 0.0;
    }

    /**
	 * @param vec
     */
    public Vector2(double vec) {
        this.x = this.y = vec;
    }

    /**
	 * @param x
	 * @param y
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
	 * @param vec
     */
    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Vector2 a = new Vector2(1.0, 2.0);
        System.out.println(a.getClass());
        Vector2 b = a.clone();
        System.out.println(a.x + "\t" + a.y);
        System.out.println(b.x + "\t" + b.y);
        b.multiply(-1.0);
        System.out.println(a.x + "\t" + a.y);
        System.out.println(b.x + "\t" + b.y);
    }

    public static double distanceSq(Vector2 vec1, Vector2 vec2) {
        return Vector2.subtract(vec1, vec2).normSq();
    }

    @Override
    public Vector2 clone() {
        Vector2 v = null;
        try {
            v = (Vector2) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return v;
    }

    /**
     * @param x
     */
    public void set(double x) {
        this.x = this.y = x;
    }

    /**
     * @param x
     * @param y
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public double[] toArray() {
        double[] array = new double[2];
        array[0] = x;
        array[1] = y;
        return array;
    }

    /**
     * @param vec
     */
    public void set(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    /**
     * @param v
     */
    public void add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * @param vec1
     * @param vec2
     * @return
     */
    public static Vector2 add(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    /**
     * @param vec
     */
    public void subtract(Vector2 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
    }

    /**
     * @param vec1
     * @param vec2
     * @return
     */
    public static Vector2 subtract(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    /**
     * @param s
     */
    public void multiply(double s) {
        x *= s;
        y *= s;
    }


    /**
     * @param v
     * @param s
     * @return
     */
    public static Vector2 multiply(Vector2 v, double s) {
        return new Vector2(v.x * s, v.y * s);
    }

    /**
     *
     * @param s
     * @param v
     * @return
     */
    public static Vector2 multiply(double s, Vector2 v) {
        return new Vector2(v.x * s, v.y * s);
    }

    /**
     * @param s
     */
    public void divide(double s) {
        x /= s;
        y /= s;
    }

    /**
     * @param v
     * @param s
     * @return
     */
    public static Vector2 divide(Vector2 v, double s) {
        return new Vector2(v.x / s, v.y / s);
    }

    /**
     *
     */
    public void normalize() {
        double n = norm();
        divide(n);
    }



    /**
     * @param v
     * @return
     */
    public double innerProduct(Vector2 v) {
        return (this.x * v.x + this.y * v.y);
    }

    /**
     * @param v
     * @return
     */
    public double crossZ(Vector2 v) {
        return (this.x * v.y - this.y * v.x);
    }

    /**
     * @return
     */
    public double norm() {
        return (Math.sqrt(x * x + y * y));
    }

    /**
     * @return
     */
    public double normSq() {
        return (x * x + y * y);
    }

    /**
     * @return
     */
    public Vector2 dot() throws ArithmeticException {
        double n = 0.0;
        try {
            if (!(x == 0.0 && y == 0.0)) {
                n = norm();
            }
        } catch(ArithmeticException e){
            e.printStackTrace();
        }
        return new Vector2(x / n, y / n);
    }

    /**
     *
     * @param vec2
     * @return
     */
    public double distance(Vector2 vec2) {
        Vector2 dx = subtract(this, vec2);
        return (dx.norm());
    }

    /**
     *
     * @param vec1
     * @param vec2
     * @return
     */
    public static double distance(Vector2 vec1, Vector2 vec2) {
        Vector2 dx = subtract(vec1, vec2);
        return (dx.norm());
    }
    // Test clone

    public String toString() {
        String string = VECTOR2_FORMAT.format(x) + "\t" + VECTOR2_FORMAT.format(y);
        return string;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector2 other = (Vector2) obj;
        if (other == null) {
            return false;
        } else {
            if (this.x != other.x) {
                return false;
            } else return this.y == other.y;
        }
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
