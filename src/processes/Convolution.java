package processes;

import commons.Image;

/**
 * Aplica convolução conforme matriz em uma imagem
 * 
 * @author Marina
 */
public class Convolution {
    
    /**
     * Aplica convolução
     * 
     * @param original
     * @param matrix
     * @return Imagem modificada
     */
    public static Image apply(Image original, int[][] matrix) {
        return apply(original, matrix, (matrix.length * matrix[0].length));
    }
    
    /**
     * Aplica convolução
     * 
     * @param original
     * @param matrix
     * @param factor
     * @return Imagem modificada
     */
    public static Image apply(Image original, int[][] matrix, int factor) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int x = 1; x < original.getWidth() -1; x++) {
            for (int y = 1; y < original.getHeight() -1; y++) {
                int newPixelValue = 0;
                // Percorre a matriz de convolução
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        newPixelValue += matrix[i][j] * original.getPixels()[x + i - 1][y + j - 1];
                    }
                }
                newPixelValue = newPixelValue / factor;
                result.setPixel(x, y, newPixelValue);
            }
        }
        
        return result;
    }
    
}
