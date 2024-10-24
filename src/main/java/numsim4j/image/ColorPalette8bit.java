package numsim4j.image;

import java.awt.*;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ColorPalette8bit extends ColorPalette {

    /**
     *
     */
    public ColorPalette8bit() {
        colors = new Color[512];
        numColors = 256;
        blue = 0;
        green = 128;
        red = 255;
        white = 256;
        black = 511;

        for (int i = 0; i < numColors; i++) {
            int r, g, b;
            double level = (double) i / 255.0;
            double weighted = Math.cos(4.0 * Math.PI * level);
            int col_val = (int) ((-weighted / 2.0 + 0.5) * 256);
            if (level > 1.0) { // 赤
                r = 255;
                g = 0;
                b = 0;
            } // 赤
            else if (level >= 0.75) { // 黄～赤
                r = 255;
                g = col_val;
                b = 0;
            } // 黄～赤
            else if (level >= 0.5) { // 緑～黄
                r = col_val;
                g = 255;
                b = 0;
            } // 緑～黄
            else if (level >= 0.25) { // 水～緑
                r = 0;
                g = 255;
                b = col_val;
            } // 水～緑
            else {// 青～水
                r = 0;
                g = col_val;
                b = 255;
            } // 青～水
            colors[i] = new Color(r, g, b);

            int j = i + 256;
            r = g = b = 511 - j;
            colors[j] = new Color(r, g, b);
        }
    }
}
