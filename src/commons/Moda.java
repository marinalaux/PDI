package commons;

import java.util.Arrays;

/**
 * Moda
 * 
 * @author Marina
 */
public class Moda implements Statistics {

    @Override
    public int compute(int[] matrix) {
        int modaColor = -1;
        int modaColorQtd = -1;
        int processingColor = -1;
        int processingColorQtd = -1;
        Arrays.sort(matrix);
        for (int i = 0; i < matrix.length; i++) {
            if (processingColor != matrix[i]) {
                if (processingColorQtd > modaColorQtd) {
                    modaColor = processingColor;
                    modaColorQtd = processingColorQtd;
                }
                processingColor = matrix[i];
                processingColorQtd = 1;
            } else {
                processingColorQtd++;
            }
        }
        if (processingColorQtd > modaColorQtd) {
            modaColor = processingColor;
        }
        return modaColor;
    }

}
