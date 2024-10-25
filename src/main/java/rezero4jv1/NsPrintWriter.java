/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezero4jv1;

import rezero4j.NsEnvironment;
import rezero4j.NsModel;

import java.io.*;
import java.text.DecimalFormat;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class NsPrintWriter {

    /**
     *
     */
    protected final String charSet = "UTF-8"; // 文字コードセット
    protected String splitter = "\t";

    /**
     *
     */
    protected final DecimalFormat doubleFormat;

    protected String workingDir;
    protected String dataDir;
    /**
     *
     */
    protected String dataPath;

    /**
     *
     */
    protected PrintWriter printWriter;

    /**
     *
     */
    protected boolean append = true; // 追加モード

    /**
     *
     */
    protected boolean autoFlush = true; // 自動フラッシュ

    protected rezero4j.NsEnvironment env;

    /**
     * @param dataName
     * @param ext
     */
    public NsPrintWriter(String dataName, String ext) {
        doubleFormat = new DecimalFormat("0.0000000E00");
        env = new NsEnvironment();
        dataDir = env.getDataDir();
        dataPath = dataDir + dataName + "." + ext;
        printWriter = defaultPrintWriter();
        printWriter.close();
    }

    /**
     * @param model
     */
    public abstract void print(NsModel model);

    /**
     *
     */
    public void close() {
        printWriter.close();
    }


    /**
     * @return
     */
    protected final PrintWriter defaultPrintWriter() {
        return defaultPrintWriter(!append);
    }

    /**
     * @param append
     * @return
     */
    protected final PrintWriter defaultPrintWriter(boolean append) {
        printWriter = null;
        try {
            try {
                printWriter = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(new FileOutputStream(new File(dataPath), append), charSet)),
                        autoFlush);
            } catch (FileNotFoundException ex) {
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("write file error" + ex);
            System.exit(0);
        }
        return printWriter;
    }

}
