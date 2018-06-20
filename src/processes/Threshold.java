package processes;

import commons.Image;

/**
 * Threshold
 */
public class Threshold {

    /**
     * Aplica threshold
     * 
     * @param original
     * @param threshold
     * @param newValue
     * @return Imagem modificada
     */
    public static Image apply(Image original, int threshold, int newValue) {
        Image result = new Image(original.getHeight(), original.getWidth());
        result.setPixels(original.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (result.getPixels()[i][j] < threshold) {
                    result.setPixel(i, j, newValue);
                }
            }
        }
        return result;
    }
    
}
