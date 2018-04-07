package filters;

import processes.GradientConvolutionTwoMasks;

/**
 * Filtro de Sobel
 * 
 * @author Marina
 */
public class SobelFilter extends GradientConvolutionTwoMasks {
    
    /**
     * Aplica filtro de Sobel
     */
    public SobelFilter() {
        super(new int[][] {{1, 0, -1}, {2, 0, -2}, { 1,  0, -1}}, 
              new int[][] {{1, 2,  1}, {0, 0,  0}, {-1, -2, -1}});
    }
    
}
