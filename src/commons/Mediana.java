package commons;

import java.util.Arrays;

/**
 * Mediana
 * 
 * @author Marina
 */
public class Mediana implements Statistics {

    @Override
    public int compute(int[] matrix) {
        int medianIndex = matrix.length / 2;
        Arrays.sort(matrix);
        return matrix[medianIndex];
    }
    
}
