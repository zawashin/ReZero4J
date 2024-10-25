package rezero4jv1;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import rezero4j.ColorPalette;
import rezero4j.ColorPalette8bit;
import rezero4j.NsEnvironment;
import rezero4j.NsModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class NsPainter {

    /**
     *
     */
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00000E00");

    /**
     *
     */
    public static final DecimalFormat IMAGE_NUM_FORMAT = new DecimalFormat("0000");

    /**
     *
     */
    protected final String png = "png";

    /**
     *
     */
    protected String dataPath;

    /**
     *
     */
    protected double xmax,

    /**
     *
     */
    xmin, ymax,

    /**
     *
     */
    ymin,

    /**
     *
     */
    dmax;

    /**
     *
     */
    protected double x0,

    /**
     *
     */
    y0;

    /**
     *
     */
    protected double scale;

    /**
     *
     */
    protected int width;

    /**
     *
     */
    protected int height;

    /**
     *
     */
    protected int xOffset,

    /**
     *
     */
    yOffset;

    /**
     *
     */
    protected Image bufferedImage;

    /**
     *
     */
    protected ColorPalette palette;

    /**
     *
     */
    protected rezero4j.NsModel model;

    /**
     *
     */
    protected Color bgColor;

    /**
	 * @param width
	 * @param height
	 * @param model
     */
    public NsPainter(int width, int height, rezero4j.NsModel model) {
        this.width = width;
        this.height = height;
        this.model = model;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        palette = new ColorPalette8bit();
        bgColor = palette.getWhite();
    }

    /**
     *
	 * @param width
	 * @param height
	 * @param model
	 * @param palette
     */
    public NsPainter(int width, int height, NsModel model, ColorPalette palette) {
        this.width = width;
        this.height = height;
        this.model = model;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.palette = palette;
        bgColor = palette.getWhite(); // 標準で背景は白
    }

    /**
     *
     * @param bgColor
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    /**
     *
     * @param palette
     */
    public void setPalette(ColorPalette palette) {
        this.palette = palette;
    }

    /**
     *
     * @return
     */
    public String getDataPath() {
        return dataPath;
    }

    /**
     *
     * @param dataPath
     */
    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public Image getBufferedImage() {
        return bufferedImage;
    }

    /**
     * @param ratio
     */
    public void multiplyScale(double ratio) {
        this.scale *= ratio;
        Graphics g = bufferedImage.getGraphics();
        paint(g);
        g.dispose();
    }

    /**
     * @return
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * @return
     */
    public double getX0() {
        return x0;
    }

    /**
     * @param x0
     */
    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * @return
     */
    public int getXoffset() {
        return xOffset;
    }

    /**
     * @param xOffset
     */
    public void setXoffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * @return
     */
    public double getY0() {
        return y0;
    }

    /**
     * @param y0
     */
    public void setY0(double y0) {
        this.y0 = y0;
    }

    /**
     * @return
     */
    public int getYoffset() {
        return yOffset;
    }

    /**
     * @param yOffset
     */
    public void setYoffset(int yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * @param g
     */
    public void clear(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, width, height);
    }

    /**
     * @param g
     */
    public void fillWhite(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
    }

    /**
     * @param g
     */
    public void fillBlack(Graphics g) {
        g.setColor(palette.getBlack());
        g.fillRect(0, 0, width, height);
    }


    public void writeImage() {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        }
        Graphics g = bufferedImage.getGraphics();
        clear(g);
        paint(g);

        NsEnvironment env = new NsEnvironment();
        dataPath = env.getDataDir() + model.getDataName() + "." + png;
        try {
            ImageIO.write((RenderedImage) bufferedImage, png, new File(dataPath));
        } catch (IOException e) {
        }
    }

    /**
     *
     * @param dataName
     */
    public void writeImage(String dataName) {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        }
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        clear(g);
        paint(g);
        NsEnvironment env = new NsEnvironment();
        dataPath = env.getDataDir() + dataName + "." + png;

        try {
            ImageIO.write((RenderedImage) bufferedImage, png, new File(dataPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param g
     */
    public abstract void paint(Graphics g);

}
