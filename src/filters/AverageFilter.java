package filters;

import processes.Convolution;

/**
 * Filtro de média
 * 
 * @author Marina
 */
public class AverageFilter extends Convolution {
    
    /**
     * Aplica filtro de média
     */
    public AverageFilter() {
        super(new int[][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 9);
    }
    
}
