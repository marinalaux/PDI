package filters;

import processes.GradientConvolutionTwoMasks;

/**
 * Filtro de Roberts
 * 
 * @author Marina
 */
public class RobertsFilter extends GradientConvolutionTwoMasks {
    
    /**
     * Aplica filtro de Roberts
     */
    public RobertsFilter() {
        super(new int[][] {{0, 0, 0}, {0, -1, 0}, {0, 0, 1}}, 
              new int[][] {{0, 0, 0}, {0, 0, -1}, {0, 1, 0}});
    }
    
}
