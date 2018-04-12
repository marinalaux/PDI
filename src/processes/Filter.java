package processes;

import commons.Image;
import commons.Statistics;

/**
 * Filtro
 * 
 * @author Marina
 */
public interface Filter {
    
    /**
     * Aplica filtro
     * 
     * @param image
     * @param statistics
     * @return Imagem modificada
     */
    public Image apply(Image image, Statistics statistics);
    
}
