/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4j;

import java.awt.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class ColorPalette {
    /**
     *
     */
    protected int numColors;

    /**
     *
     */
    protected Color[] colors;

    /**
     *
     */
    protected int blue,

    /**
     *
     */
    green,

    /**
     *
     */
    red;

    /**
     *
     */
    protected int black,

    /**
     *
     */
    white;

    /**
     * @return
     */
    public int getNumColors() {
        return numColors;
    }

    /**
     * @return
     */
    public Color[] getColors() {
        return (colors);
    }

    /**
     * @param i
     * @return
     */
    public Color getColor(int i) {
        return (colors[i]);
    }

    /**
     * @return
     */
    public Color getWhite() {
        return (colors[white]);
    }

    /**
     * @return
     */
    public Color getBlack() {
        return (colors[black]);
    }

    /**
     * @return
     */
    public Color getBlue() {
        return (colors[blue]);
    }

    /**
     * @return
     */
    public Color getGreen() {
        return (colors[green]);
    }

    /**
     * @return
     */
    public Color getRed() {
        return (colors[red]);
    }

}
