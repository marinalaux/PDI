package filters;

import commons.Image;

/**
 * Abertura
 * 
 * @author Marina
 */
public class OpeningFilter {

    /**
     * Aplica abertura na imagem
     * 
     * @param image
     * @return Imagem modificada
     */
    public Image apply(Image image) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        
        result = new ErosionFilter().apply(result);
        result = new DilationFilter().apply(result);
        
        return result;
    }
    
}
