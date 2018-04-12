package filters;

import commons.Image;
import commons.Statistics;
import processes.Filter;

/**
 * Aplica filtro na imagem
 * 
 * @author Marina
 */
public class GenericFilter implements Filter {

    @Override
    public Image apply(Image image, Statistics statistics) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        for (int x = 1; x < image.getWidth() -1; x++) {
            for (int y = 1; y < image.getHeight() -1; y++) {
                int[] matrix = new int[9];
                int ind = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int xPosition = x + i - 1;
                        int yPosition = y + j - 1;
                        matrix[ind] = image.getPixels()[xPosition][yPosition];
                        ind++;
                    }
                }
                result.setPixel(x, y, statistics.compute(matrix));
            }
        }
        return result;
    }
    
}
