package processes;

import commons.Image;

/**
 *
 * @author Marina
 */
public interface Filter {
    
    public Image apply(Image image, int threshold);
    
}
