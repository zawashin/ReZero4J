/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4j;

import java.io.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class NsReader {

    /**
     *
     */
    protected final String CHAR_SET = "UTF-8"; // 文字コードセット

    /**
     *
     */
    protected final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("0.00000E00");

    /**
     *
     */
    protected String workingDir;
    protected String dataDir;
    /**
     *
     */
    protected String dataPath;

    /**
     *
     */
    protected String dataName;

    /**
     *
     */
    protected BufferedReader breader;

    protected NsEnvironment env;

    /**
     *
     */
    protected NsModel model;

    /**
     *
	 * @param dataName
	 * @param ext
     */
    public NsReader(String dataName, String ext) {
        this.dataName = dataName;
        env = new NsEnvironment();
        dataDir = env.getDataDir();
        dataPath = dataDir + dataName + "." + ext;
        try {
            File file = new File(this.dataPath);
            FileReader reader = new FileReader(file);
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(NsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            breader = defaultBufferedReader();
            try {
                breader.close();
            } catch (IOException ex) {
                Logger.getLogger(NsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     *
     */
    public abstract void read();

    /**
     *
     * @return
     */
    public abstract NsModel getModel();

    /**
     *
     */
    public final void close() {
        try {
            breader.close();
        } catch (IOException ex) {
            Logger.getLogger(NsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return
     */
    protected final BufferedReader defaultBufferedReader() {
        try {
            try {
                breader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataPath)), CHAR_SET)); // 省略するとシステム標準
            } catch (FileNotFoundException ex) {
            }
        } catch (UnsupportedEncodingException ex) {
        }
        return breader;
    }

}
