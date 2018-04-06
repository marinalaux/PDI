package filters;

import commons.Image;

/**
 * Filtro para aplicação de brilho e contraste
 * 
 * @author Marina
 */
public class BrightnessContrastFilter {
    
    /**
     * Aplica brilho
     * 
     * @param image
     * @param b
     * @return Image
     */
    public static Image applyBrightness(Image image, int b) {
        return apply(image, b, 1);
    }
    
    /**
     * Aplica contraste
     * 
     * @param image
     * @param c
     * @return Image
     */
    public static Image applyContrast(Image image, double c) {
        return apply(image, 0, c);
    }
    
    /**
     * Aplica brilho e contraste
     * 
     * @param image
     * @param b
     * @param c
     * @return Image
     */
    public static Image apply(Image image, int b, double c) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                int newPixelValue = (int)(c * image.getPixels()[x][y] + b);
                if (newPixelValue > 255) {
                    newPixelValue = 255;
                }
                if (newPixelValue < 0) {
                    newPixelValue = 0;
                }
                result.setPixel(x, y, newPixelValue);
            }
        }
        return result;
    }
    
}
