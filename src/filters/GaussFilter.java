package filters;

import commons.Image;
import processes.Convolution;

/**
 * Filtro de Gauss
 *
 * @author Marina
 */
public class GaussFilter {

    /**
     * Aplica filtro de Gauss
     *
     * @param original
     * @return Image
     */
    public static Image apply(Image original) {
        return Convolution.apply(original,
                new int[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}},
                16);
    }

}
