package processes;

import commons.Image;
import commons.ImageObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Rotulação dos objetos da imagem
 */
public class BinaryLabeling {
    
    /** Cor do fundo */
    private final int background;
    /** Cor dos objetos */
    private final int foreground;
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
     * @param background 
     * @param foreground 
     */
    public BinaryLabeling(Image image, int background, int foreground) {
        this.image = new Image(image.getHeight(), image.getWidth());
        this.image.setPixels(image.getPixels());
        objetos = new ArrayList<>();
        this.background = background;
        this.foreground = foreground;
    }
    
    /**
     * Aplica rotulação de objetos da imagem
     */
    public void apply() {
        cor = 1;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getPixels()[x][y] == foreground) {
                    ImageObject obj = new ImageObject();
                    obj.setCor(cor);
                    colorizePixels(x, y, cor, obj);
                    objetos.add(obj);
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
     * @param obj
     */
    private void colorizePixels(int x, int y, int cor, ImageObject obj) {
        image.setPixel(x, y, cor);
        obj.setQtdPixels(obj.getQtdPixels() + 1);
        if(y > 0 && image.getPixels()[x][y - 1] == foreground) {
            colorizePixels(x, y - 1, cor, obj);
        }
        if(y < image.getHeight() - 1 && image.getPixels()[x][y + 1] == foreground) {
            colorizePixels(x, y + 1, cor, obj);
        }
        if(x > 0 && image.getPixels()[x - 1][y] == foreground) {
            colorizePixels(x - 1, y, cor, obj);
        }
        if(x < image.getWidth() - 1 && image.getPixels()[x + 1][y] == foreground) {
            colorizePixels(x + 1, y, cor, obj);
        }
    }
    
}
