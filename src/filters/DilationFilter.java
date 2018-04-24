package filters;

import processes.MorphologyFilter;

/**
 * Dilatação
 * 
 * @author Marina
 */
public class DilationFilter extends MorphologyFilter {
    
    /**
     * Calcula valor dos pixels para dilatação
     * 
     * @param pixels
     * @return Pixels
     */
    @Override
    public int calcula(int[][] pixels) {
        int maior = 0;
        // Elemento estruturante
        int[][] mask = {{0, 10, 0}, {10, 10, 10}, {0, 10, 0}};
        
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                // Considera apenas os pixels da vizinhança de 4 para dilatação
                if (!isNeighborhood(i, j)) {
                    continue;
                }
                int newPixelValue = pixels[i][j] + mask[i][j];
                if (newPixelValue > 255) {
                    newPixelValue = 255;
                }
                if (maior < newPixelValue) {
                    maior = newPixelValue;
                }
            }
        }
        return maior;
    }

    /**
     * Pixel é vizinhança de 4
     * 
     * @param x
     * @param y
     * @return Boolean
     */
    public boolean isNeighborhood(int x, int y) {
        return x == 1 || y == 1;
    }
    
}
