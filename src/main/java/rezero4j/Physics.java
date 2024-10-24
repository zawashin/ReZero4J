/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4j;

import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Physics {

    /**
     * Permittivity in Vacuity
     */
    public static final double G = -9.8;
    /**
     *
     */
    public static final double NU0 = 7.95775e+5;
    /**
     * Permiability in Vacuity
     */
    public static final double MU0 = 1.25664e-6;
    /**
     *
     */
    public static final HashMap<String, Double> RHO = new HashMap<String, Double>() {
        private static final long serialVersionUID = 1L;

        {
            put("H2O", 1.00e3);
            put("Fe", 7.87e3); // Density of Steel (Fe)
        }
    };

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Physics.RHO.get("Fe"));
        System.out.println(Physics.RHO.get("H2O"));
    }
}
