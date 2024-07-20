/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4j;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shin
 */
public abstract class NsSolver implements Runnable {

    protected String dataName;
    /**
     *
     */
    protected boolean calculable;

    /**
     *
     */
    protected boolean visualize;

    /**
     *
     */
    protected NsPainter painter;

    /**
     *
     */
    protected NsModel model;

    /**
     *
     */
    protected NsFrame frame;

    /**
     *
     */
    protected int mills; // Sleep Time

    /**
     *
     */
    protected int drawInterval;

    /**
     *
	 * @param model
	 * @param visualize
     */
    public NsSolver(NsModel model, boolean visualize) {
        super();
        this.model = model;
        calculable = true;
        this.visualize = visualize;
        this.dataName = model.getDataName();
        /*
        if(visualizable) {
            painter = new NsPainter(model);
        }
        */
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    /**
     *
     * @param mills
     */
    public void setMills(int mills) {
        this.mills = mills;
    }

    /**
     *
     * @param drawInterval
     */
    public void setDrawInterval(int drawInterval) {
        this.drawInterval = drawInterval;
    }

    /**
     * @return
     */
    public boolean isCalculable() {
        return calculable;
    }

    /**
     * @param calculable
     */
    public void setCalculable(boolean calculable) {
        this.calculable = calculable;
    }

    /**
     * @return
     */
    public boolean isVisualize() {
        return visualize;
    }

    /**
     * @param visualize
     */
    public void setVisualize(boolean visualize) {
        this.visualize = visualize;
    }

    /**
     *
     */
    public abstract void solve() throws CloneNotSupportedException;

    @Override
    public void run() {
        try {
            solve();
        } catch (Exception ex) {
            Logger.getLogger(NsSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
