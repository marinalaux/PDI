package filters;

import processes.MorphologyFilter;

/**
 * Erosão
 * 
 * @author Marina
 */
public class ErosionFilter extends MorphologyFilter {

    /**
     * Calcula menor valor dos pixels para erosão
     * 
     * @param pixels
     * @return Menor valor de pixel
     */
    @Override
    public int calcula(int[][] pixels) {
        int menor = 255;
        // Elemento estruturante
        int[][] mask = {{0, 10, 0}, {10, 10, 10}, {0, 10, 0}};
        
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                // Considera apenas os pixels da vizinhança de 4 para erosão
                if (!isNeighborhood(i, j)) {
                    continue;
                }
                int newPixelValue = pixels[i][j] - mask[i][j];
                if (newPixelValue < 0) {
                    newPixelValue = 0;
                }
                if (menor > newPixelValue) {
                    menor = newPixelValue;
                }
            }
        }
        return menor;
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
