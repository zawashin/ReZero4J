package rezero4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

public class NsFrame extends JFrame  implements KeyListener {

    /**
     *
     */
    protected JPanel panel;

    /**
     *
     */
    protected JLabel label;

    /**
     *
     */
    protected NsPainter painter;

    /**
     *
     */
    protected Image bufferedImage;

    //public NsFrame(int width, int height, NsPainter painter) {

    /**
     *
	 * @param painter
     */
    public NsFrame(NsPainter painter) {
        super(); //Sets the title
        this.painter = painter;
        this.bufferedImage = painter.bufferedImage;
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac OS X")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));//Has FlowLayout by default but added it anyway
        label = new JLabel();
        label.setIcon(new ImageIcon(bufferedImage));
        panel.add(label); //Adds Label to Panel
        add(panel); //Adds Panel with all comps to frame
        pack();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     *
     */
    public void repaintLabel() {
        Graphics graphics = bufferedImage.getGraphics();
        painter.clear(graphics);
        painter.paint(graphics);
        label.repaint();
        graphics.dispose();
    }

    @Override
    public void paint(Graphics g) {
        Graphics graphics = bufferedImage.getGraphics();
        painter.clear(graphics);
        painter.paint(graphics);
        label.repaint();
        graphics.dispose();
    }

    @Override
    public void repaint() {
        Graphics graphics = bufferedImage.getGraphics();
        painter.clear(graphics);
        painter.paint(graphics);
        label.repaint();
        graphics.dispose();
    }
    /**
     @Override
    public Graphics getGraphics() {
        //return (Graphics) bufferedImage.getGraphics(); // 真っ白けになるのでNG
        //return label.getGraphics();                    // 落ちる
        //return super.getGraphics();                    // 動くけどOverrideの意味無し
    }
     */

    @Override
    public void keyTyped(KeyEvent e) {
        //使用しないので空にしておきます。
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        /**
         * https://nompor.com/2017/12/10/post-1924/
         * からコピペ
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_UP:
                System.out.println("上が押されました");
                System.exit(0);
            case KeyEvent.VK_SPACE:
                //スペースキー
                System.out.println("スペースが押されました");
                break;
            case KeyEvent.VK_ENTER:
                //エンターキー
                System.out.println("Enterが押されました");
                break;
         // IDEが自動的に挿入
            default:
                throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
        }

         */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //使用しないので空にしておきます。
    }

    @Override
    public void update(Graphics g) {
        label.repaint();
    }

    /**
     *
     */
    public void writeImage() {
        painter.writeImage();
    }

    /**
     *
	 * @param dataName
     */
    public void writeImage(String dataName) {
        painter.writeImage(dataName);
    }

    /**
     *
     * @param ratio
     */
    public void multiplyScale(double ratio) {
        painter.multiplyScale(ratio);
    }
}

