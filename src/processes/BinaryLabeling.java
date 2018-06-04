package processes;

import commons.Image;
import commons.ImageObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Rotulação dos objetos da imagem
 */
public class BinaryLabeling {
    
    /** Cor do fundo = branco */
    private static final int BACKGROUND = 255;
    /** Cor dos objetos = preto */
    private static final int FOREGROUND = 0;
    /** Imagem */
    private final Image image;
    /** Objetos da imagem */
    private final List<ImageObject> objetos;
    /** Cor do objeto em processamento */
    private int cor;

    /**
     * Construtor
     * 
     * @param image 
     */
    public BinaryLabeling(Image image) {
        this.image = new Image(image.getHeight(), image.getWidth());
        this.image.setPixels(image.getPixels());
        objetos = new ArrayList<>();
    }
    
    /**
     * Aplica rotulação de objetos da imagem
     */
    public void apply() {
        cor = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getPixels()[x][y] == FOREGROUND) {
                    ImageObject obj = new ImageObject();
                    obj.setCor(cor);
                    objetos.add(obj);
                    colorizePixels(x, y, cor);
                    cor++;
                }
            }
        }
    }
    
    /**
     * Retorna imagem com os objetos identificados
     * 
     * @return Imagem
     */
    public Image getLabeledImage() {
        return image;
    }
    
    /**
     * Retorna os objetos identificados na imagem
     * 
     * @return Objetos
     */
    public List<ImageObject> getObjects() {
        return objetos;
    }
    
    /**
     * Colore pixels do objeto encontrado
     * 
     * @param x
     * @param y
     * @param cor
     */
    private void colorizePixels(int x, int y, int cor) {
        image.setPixel(x, y, cor);
        if(y > 0 && image.getPixels()[x][y - 1] == FOREGROUND) {
            colorizePixels(x, y - 1, cor);
        }
        if(y < image.getHeight() - 1 && image.getPixels()[x][y + 1] == FOREGROUND) {
            colorizePixels(x, y + 1, cor);
        }
        if(x > 0 && image.getPixels()[x - 1][y] == FOREGROUND) {
            colorizePixels(x - 1, y, cor);
        }
        if(x < image.getWidth() - 1 && image.getPixels()[x + 1][y] == FOREGROUND) {
            colorizePixels(x + 1, y, cor);
        }
    }
    
}
