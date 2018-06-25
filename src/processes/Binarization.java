package processes;

import commons.Image;

/**
 * Threshold
 */
public class Binarization {

    /**
     * Aplica threshold
     * 
     * @param original
     * @param threshold
     * @return Imagem modificada
     */
    public static Image apply(Image original, int threshold) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (result.getPixels()[i][j] < threshold) {
                    result.setPixel(i, j, 0);
                } else {
                    result.setPixel(i, j, 255);                    
                }
            }
        }
        return result;
    }
    
}
