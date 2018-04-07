package filters;

import processes.GradientConvolutionTwoMasks;

/**
 * Filtro de Marr e Hildreth
 * 
 * @author Marina
 */
public class MarrAndHildrethFilter extends GradientConvolutionTwoMasks {
    
    /**
     * Aplica filtro de Marr e Hildreth
     */
    public MarrAndHildrethFilter() {
        super(new int[][] {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}, 
              new int[][] {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}});
    }
    
}
