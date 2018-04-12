package processes;

import commons.Image;

/**
 * Aplica convolução conforme as matrizes x e y
 * 
 * @author Marina
 */
public class GradientConvolutionTwoMasks implements ConvolutionFilter {
    
    /** Máscara de convolução para o eixo x */
    private final int[][] xMask;
    /** Máscara de convolução para o eixo y */
    private final int[][] yMask;

    public GradientConvolutionTwoMasks(int[][] xMask, int[][] yMask) {
        this.xMask = xMask;
        this.yMask = yMask;
    }
    
    /**
     * Aplica convolução
     * 
     * @param original
     * @param threshold
     * @return Imagem modificada
     */
    @Override
    public Image apply(Image original, int threshold) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int x = 1; x < original.getWidth() -1; x++) {
            for (int y = 1; y < original.getHeight() -1; y++) {
                double xGradient = 0;
                double yGradient = 0;
                // Percorre a matriz de convolução
                for (int i = 0; i < xMask.length; i++) {
                    for (int j = 0; j < xMask[i].length; j++) {
                        int pixelValue = original.getPixels()[x + i - 1][y + j - 1];
                        xGradient += pixelValue * xMask[i][j];
                        yGradient += pixelValue * yMask[i][j];
                    }
                }
                double gradient = Math.sqrt(Math.pow(xGradient, 2) + Math.pow(yGradient, 2));
                int newPixelValue = gradient > threshold ? 255 : 0;
                result.setPixel(x, y, newPixelValue);
            }
        }
        return result;
    }
    
}
