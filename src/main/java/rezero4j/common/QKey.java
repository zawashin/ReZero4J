package rezero4j.common;

import java.nio.IntBuffer;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class QKey {
    protected int[] state;
    protected int action;
    protected IntBuffer key;

    public QKey(int[] state, int action) {
        this.state = state.clone();
        this.action = action;
        int[] array = new int[state.length + 1];
        System.arraycopy(state, 0, array, 0, state.length);
        array[state.length] = action;
        key = IntBuffer.wrap(array);
    }

    public int getAction() {
        return action;
    }

    public int[] getState() {
        return state;
    }

    public IntBuffer getKey() {
        return key;
    }

    public static IntBuffer key(int[] state, int action) {
        int[] array = new int[state.length + 1];
        System.arraycopy(state, 0, array, 0, state.length);
        array[state.length] = action;
        return IntBuffer.wrap(array);
    }

}
