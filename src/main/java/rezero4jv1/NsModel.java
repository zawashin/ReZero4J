/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4jv1;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class NsModel implements Cloneable, Serializable {

    /**
     *
     */
    protected static final DecimalFormat DFORMAT = new DecimalFormat("0.0000E00");

    /**
     *
     */
    protected String dataName;
    //protected String dataName = this.toString();
    protected String dataPath;
    protected String dataDir;

    /**
     * @return
     */
    @Override
    public NsModel clone() throws CloneNotSupportedException {
        try {
            return ((NsModel) super.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(NsModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}
