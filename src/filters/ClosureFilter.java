package filters;

import commons.Image;

/**
 * Fechamento
 * 
 * @author Marina
 */
public class ClosureFilter {
   
    /**
     * Aplica fechamento na imagem
     * 
     * @param image
     * @return Imagem modificada
     */
    public Image apply(Image image) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        
        result = new DilationFilter().apply(result);
        result = new ErosionFilter().apply(result);
        
        return result;
    }
    
}
