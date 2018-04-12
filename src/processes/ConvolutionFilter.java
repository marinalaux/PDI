package processes;

import commons.Image;

/**
 * Interface de filtro para convolução
 * 
 * @author Marina
 */
public interface ConvolutionFilter {
    
    /**
     * Aplica filtro de convolução
     * 
     * @param image
     * @param threshold
     * @return Imagem modificada
     */
    public Image apply(Image image, int threshold);
    
}
