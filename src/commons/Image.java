package commons;

import java.awt.image.BufferedImage;

/**
 * Imagem
 * 
 * @author Marina
 */
public class Image {
    /** Altura da imagem */
    private final int height;
    /** Largura da imagem */
    private final int width;
    /** Pixels da imagem */
    private final int[][] pixels;
    
    /**
     * Construtor
     * 
     * @param h
     * @param w
     */
    public Image(int h, int w) {
        height = h;
        width = w;
        pixels = new int[width][height];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public int[][] getPixels() {
        return pixels;
    }

    public void setPixels(int[][] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                setPixel(i, j, pixels[i][j]);
            }
        }
    }
    
    public void setPixel(int row, int column, int pixel) {
        pixels[row][column] = pixel;
    }
    
    /**
     * Converte uma matriz de pixels em uma imagem
     * 
     * @return BufferedImage
     */
    public BufferedImage toBufferedImage() {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int valor = pixels[i][j];
                int rgb = valor | valor<<8 | valor<<16 | 0xFF000000;
                result.setRGB(i, j,  rgb);
            }
        }
        return result;
    }
    
}
