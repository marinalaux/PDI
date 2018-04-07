package filters;

import processes.Convolution;

/**
 * Filtro de Gauss
 *
 * @author Marina
 */
public class GaussFilter extends Convolution {

    /**
     * Construtor
     */
    public GaussFilter() {
        super(new int[][] {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}}, 16);
    }

}
