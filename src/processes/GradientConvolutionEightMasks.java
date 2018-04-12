package processes;

import commons.Image;
import java.util.Arrays;

/**
 * Aplica convolução a partir de oito máscaras
 * 
 * @author Marina
 */
public class GradientConvolutionEightMasks implements ConvolutionFilter {

    /** Primeira máscara de convolução */
    private final int[][] mask1;
    /** Segunda máscara de convolução */
    private final int[][] mask2;
    /** Terceira máscara de convolução */
    private final int[][] mask3;
    /** Quarta máscara de convolução */
    private final int[][] mask4;
    /** Quinta máscara de convolução */
    private final int[][] mask5;
    /** Sexta máscara de convolução */
    private final int[][] mask6;
    /** Sétima máscara de convolução */
    private final int[][] mask7;
    /** Oitava máscara de convolução */
    private final int[][] mask8;
    
    public GradientConvolutionEightMasks(int[][] mask1, int[][] mask2, int[][] mask3, 
            int[][] mask4, int[][] mask5, int[][] mask6, int[][] mask7, int[][] mask8) {
        this.mask1 = mask1;
        this.mask2 = mask2;
        this.mask3 = mask3;
        this.mask4 = mask4;
        this.mask5 = mask5;
        this.mask6 = mask6;
        this.mask7 = mask7;
        this.mask8 = mask8;
    }

    @Override
    public Image apply(Image original, int threshold) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int x = 1; x < original.getWidth() -1; x++) {
            for (int y = 1; y < original.getHeight() -1; y++) {
                double[] gradient = new double[8];
                // Percorre a matriz de convolução
                for (int i = 0; i < mask1.length; i++) {
                    for (int j = 0; j < mask1[i].length; j++) {
                        int pixelValue = original.getPixels()[x + i - 1][y + j - 1];
                        gradient[0] += pixelValue * mask1[i][j];
                        gradient[1] += pixelValue * mask2[i][j];
                        gradient[2] += pixelValue * mask3[i][j];
                        gradient[3] += pixelValue * mask4[i][j];
                        gradient[4] += pixelValue * mask5[i][j];
                        gradient[5] += pixelValue * mask6[i][j];
                        gradient[6] += pixelValue * mask7[i][j];
                        gradient[7] += pixelValue * mask8[i][j];
                    }
                }
                Arrays.sort(gradient);
                int newPixelValue = gradient[7] > threshold ? 255 : 0;
                result.setPixel(x, y, newPixelValue);
            }
        }
        return result;
    }
    
}
