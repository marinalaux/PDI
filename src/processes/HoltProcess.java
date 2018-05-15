package processes;

import commons.Image;

/**
 * Afinamento de Holt
 */
public class HoltProcess {
    
    /**
     * Aplica afinamento de Holt
     * 
     * @param image
     * @return Imagem modificada
     */
    public Image apply(Image image) {
        Image result = new Image(image.getHeight(), image.getWidth());
        result.setPixels(image.getPixels());
        
        Image process = new Image(image.getHeight(), image.getWidth());
        process.setPixels(image.getPixels());
        
        boolean change = true;
        boolean odd = false;
        while(change) {
            change = false;
            odd = !odd;
            for (int x = 1; x < process.getWidth() -1; x++) {
                for (int y = 1; y < process.getHeight() -1; y++) {
                    if (v(process.getPixels()[x][y])) {
                        int value = pixelValue(getNeighborhood(x, y, process), odd);
                        if (value != process.getPixels()[x][y]) {
                            change = true;
                        }
                        result.setPixel(x, y, value);
                    }
                }
            }
            process.setPixels(result.getPixels());
        }
        
        return result;
    }
    
    /**
     * Carrega vizinhança de 8 pixels
     * 
     * @return Vizinhança
     */
    private int[][] getNeighborhood (int x, int y, Image image) {
        int[][] neighboor = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x2 = x + i - 1;
                int y2 = y + j - 1;
                neighboor[i][j] = image.getPixels()[x2][y2];
            }
        }
        return neighboor;
    }
    
    /**
     * Calcula valor do pixel após afinamento de Holt
     * 
     * @return Valor do pixel
     */
    private int pixelValue(int[][] pixels, boolean odd) {
        int[] neighborhood = neighborhoodToArray(pixels);
        if (!isEdge(neighborhood)) {
            return pixels[1][1];
        }
        int n = pixels[1][0];
        int s = pixels[1][2];
        int l = pixels[2][1];
        int o = pixels[0][1];
        if (odd) {
            if (v(l) && v(s) && (v(n) || v(o))) {
                return pixels[1][1];
            }
        } else {
            if (v(o) && v(n) && (v(s) || v(l))) {
                return pixels[1][1];
            }
        }
        return 0;
    }
    
    /**
     * Transforma matriz de pixels vizinhos em array
     * 
     * @param pixels
     * @return Array de pixels vizinhos
     */
    private int[] neighborhoodToArray(int[][] pixels) {
        int p2 = pixels[1][0] / 255;
        int p3 = pixels[2][0] / 255;
        int p4 = pixels[2][1] / 255;
        int p5 = pixels[2][2] / 255;
        int p6 = pixels[1][2] / 255;
        int p7 = pixels[0][2] / 255;
        int p8 = pixels[0][1] / 255;
        int p9 = pixels[0][0] / 255;
        return new int[]{p2, p3, p4, p5, p6, p7, p8, p9};
    }
    
    /**
     * É borda
     * 
     * @param n
     * @return boolean
     */
    private boolean isEdge(int[] n) {
        int nConected = n[0] + n[1] + n[2] + n[3] + n[4] + n[5] + n[6] + n[7];
        return (nConected >= 2 && nConected <= 6) && isConected(n);
    }
    
    /**
     * Está conectado
     * 
     * @param n
     * @return boolean
     */
    private boolean isConected(int[] n) {
        int conectividade = (n[0] < n[1] ? 1 : 0)
                          + (n[1] < n[2] ? 1 : 0)
                          + (n[2] < n[3] ? 1 : 0)
                          + (n[3] < n[4] ? 1 : 0)
                          + (n[4] < n[5] ? 1 : 0)
                          + (n[5] < n[6] ? 1 : 0)
                          + (n[6] < n[7] ? 1 : 0)
                          + (n[7] < n[0] ? 1 : 0);
        return conectividade == 1;
    }
    
    /**
     * É pixel do objeto
     * 
     * @param pixel
     * @return boolean
     */
    private boolean v(int pixel) {
        return pixel == 255;
    }
    
}
