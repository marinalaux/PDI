package processes;

import commons.Image;

/**
 * Transformações geométricas
 * 
 * @author Marina
 */
public class GeometricTransformations {
    
    /**
     * Transforma a imagem conforme a matriz
     * 
     * @param original
     * @param matrix
     * @return 
     */
    public static Image transforma(Image original, double[][] matrix) {
        Image result = new Image(original.getHeight(), original.getWidth());
        int halfX = original.getWidth() / 2;
        int halfY = original.getHeight() / 2;
        int z = 1;
        
        for (int x = -halfX; x < halfX; x++) {
            for (int y = -halfY; y < halfY; y++) {
                // Calcula a nova posição de x (linha)
                int realX = (int) Math.round((x * matrix[0][0]) + (y * matrix[1][0]) + (z * matrix[2][0]));
                realX += halfX;
                
                // Calcula a nova posição de y (coluna)
                int realY = (int) Math.round((x * matrix[0][1] + (y * matrix[1][1]) + (z * matrix[2][1])));
                realY += halfY;
                
                if (realX >= original.getWidth() || realY >= original.getHeight() ||
                    realX < 0 || realY < 0) {
                    continue;
                }
                result.setPixel(realX, realY, original.getPixels()[x + halfX][y + halfY]);
            }
        }

        return result;
    }
    
}
