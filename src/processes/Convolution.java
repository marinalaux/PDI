package processes;

import commons.Image;

/**
 * Aplica convolução conforme matriz em uma imagem
 * 
 * @author Marina
 */
public class Convolution {
    
    /** Máscara de convolução */
    private final int[][] mask;
    /** Fator para divisão do resultado da convolução */
    private final int factor;

    /**
     * Construtor
     * 
     * @param mask
     * @param factor 
     */
    public Convolution(int[][] mask, int factor) {
        this.mask = mask;
        this.factor = factor;
    }
    
    /**
     * Aplica convolução
     * 
     * @param original
     * @return Imagem modificada
     */
    public Image apply(Image original) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int x = 1; x < original.getWidth() -1; x++) {
            for (int y = 1; y < original.getHeight() -1; y++) {
                int newPixelValue = 0;
                // Percorre a matriz de convolução
                for (int i = 0; i < mask.length; i++) {
                    for (int j = 0; j < mask[i].length; j++) {
                        newPixelValue += mask[i][j] * original.getPixels()[x + i - 1][y + j - 1];
                    }
                }
                newPixelValue = newPixelValue / factor;
                result.setPixel(x, y, newPixelValue);
            }
        }
        return result;
    }
    
}
