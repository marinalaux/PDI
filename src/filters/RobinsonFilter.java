package filters;

import processes.GradientConvolutionEightMasks;

/**
 * Filtro de Robinson
 * 
 * @author Marina
 */
public class RobinsonFilter extends GradientConvolutionEightMasks {
    
    /**
     * Aplica filtro de Robinson
     */
    public RobinsonFilter() {
        super(new int[][] {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}},
              new int[][] {{0, -1, -2}, {1, 0, -1}, {2, 1, 0}},
              new int[][] {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}},
              new int[][] {{-2, -1, 0}, {-1, 0, 1}, {0, 1, 2}},
              new int[][] {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}},
              new int[][] {{0, 1, 2}, {-1, 0, 1}, {-2, -1, 0}},
              new int[][] {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}},
              new int[][] {{2, 1, 0}, {1, 0, -1}, {0, -1, -2}});
    }
    
}
