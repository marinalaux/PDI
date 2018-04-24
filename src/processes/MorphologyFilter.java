package processes;

import commons.Image;

/**
 * Filtro para morfologia matemática
 * 
 * @author Marina
 */
public abstract class MorphologyFilter {
    
    /**
     * Aplica filtro morfológico
     * 
     * @param image
     * @return Imagem modificada
     */
    public Image apply(Image image) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        
        for (int x = 1; x < image.getWidth() -1; x++) {
            for (int y = 1; y < image.getHeight() -1; y++) {
                int[][] subimage = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
                
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int x2 = x + i - 1;
                        int y2 = y + j - 1;
                        // Se não for a vizinhança de 4
                        if (!isNeighborhood(x2, y2, x, y)) {
                            continue;
                        }
                        subimage[i][j] = image.getPixels()[x2][y2];
                    }
                }
                int newPixelsValue = calcula(subimage);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int x2 = x + i - 1;
                        int y2 = y + j - 1;
                        // Se não for a vizinhança de 4
                        if (!isNeighborhood(x2, y2, x, y)) {
                            continue;
                        }
                        result.setPixel(x2, y2, newPixelsValue);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Pixel é vizinhança de 4
     * 
     * @param x
     * @param y
     * @param xReference
     * @param yReference
     * @return Boolean
     */
    public boolean isNeighborhood(int x, int y, int xReference, int yReference) {
        return x == xReference || y == yReference;
    }
    
    public abstract int calcula(int[][] pixels);
    
}
