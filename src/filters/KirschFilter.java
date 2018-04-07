package filters;

import processes.GradientConvolutionEightMasks;

/**
 * Filtro de Kirsch
 * 
 * @author Marina
 */
public class KirschFilter extends GradientConvolutionEightMasks {
    
    /**
     * Aplica filtro de Kirsch
     */
    public KirschFilter() {
        super(new int[][] {{5, -3, -3}, {5, 0, -3}, {5, -3, -3}},
              new int[][] {{-3, -3, -3}, {5, 0, -3}, {5, 5, -3}},
              new int[][] {{-3, -3, -3}, {-3, 0, -3}, {5, 5, 5}},
              new int[][] {{-3, -3, -3}, {-3, 0, 5}, {-3, 5, 5}},
              new int[][] {{-3, -3, 5}, {-3, 0, 5}, {-3, -3, 5}},
              new int[][] {{-3, 5, 5}, {-3, 0, 5}, {-3, -3, -3}},
              new int[][] {{5, 5, 5}, {-3, 0, -3}, {-3, -3, -3}},
              new int[][] {{5, 5, -3}, {5, 0, -3}, {-3, -3, -3}});
    }
    
}
