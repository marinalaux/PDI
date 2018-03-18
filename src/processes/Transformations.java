package processes;

import commons.ImageStatistics;
import commons.Image;

/**
 * Transformações em imagem
 * 
 * @author Marina
 */
public class Transformations {
    
    /**
     * Transforma pixels acima da média para branco
     * 
     * @param image
     * @param statistics
     * @return Image
     */
    public static Image aboveAvgToWhite(Image image, ImageStatistics statistics) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (result.getPixels()[i][j] > statistics.computeMedia()) {
                    result.setPixel(i, j, 255);
                }
            }
        }
        return result;
    }    
    
    /**
     * Transforma pixels abaixo da mediana para preto
     * 
     * @param image
     * @param statistics
     * @return Image
     */
    public static Image belowMedianToBlack(Image image, ImageStatistics statistics) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (result.getPixels()[i][j] < statistics.computeMediana()) {
                    result.setPixel(i, j, 0);
                }
            }
        }
        return result;
    }
    
    /**
     * Transforma pixels acima da moda para branco e demais para preto
     * 
     * @param image
     * @param statistics
     * @return Image
     */
    public static Image aboveModeToWhiteOthersBlack(Image image, ImageStatistics statistics) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (result.getPixels()[i][j] > statistics.computeModa()) {
                    result.setPixel(i, j, 255);
                } else {
                    result.setPixel(i, j, 0);
                }
            }
        }
        return result;
    }
    
    /**
     * Inverte o valor dos pixels da imagem
     * 
     * @param image
     * @return Image
     */
    public static Image reversePixelsValue(Image image) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int i = 0; i < result.getWidth(); i++) {
            for (int j = 0; j < result.getHeight(); j++) {
                int pixel = 255 - result.getPixels()[i][j];
                result.setPixel(i, j, pixel);
            }
        }
        return result;
    }
    
}
