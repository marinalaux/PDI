package commons;

import java.util.Arrays;

/**
 * Estatísticas da imagem
 * 
 * @author Marina
 */
public class ImageStatistics {
    /** Imagem */
    private final Image image;
    /** Média */
    private float media = -1;
    /** Mediana */
    private int mediana = -1;
    /** Moda */
    private int moda = -1;
    /** Variância */
    private float variancia = -1;
    /** Histograma da imagem */
    private int[] histograma;
    
    /**
     * Construtor
     * 
     * @param image 
     */
    public ImageStatistics(Image image) {
        this.image = image;
    }
    
    /**
     * Calcula todas as estatísticas da imagem
     */
    public void computeAll() {
        media = -1;
        computeMedia();
        mediana = -1;
        computeMediana();
        moda = -1;
        computeModa();
        variancia = -1;
        computeVariancia();
        histograma = null;
        loadHistograma();
    }
    
    /**
     * Calcula a média da imagem
     * 
     * @return float
     */
    public float computeMedia() {
        if (media < 0) {
            int soma = 0;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    soma += image.getPixels()[i][j];
                }
            }
            media = (soma / (image.getWidth() * image.getHeight()));
        }
        return media;
    }
    
    /**
     * Calcula a mediana da imagem
     * 
     * @return int
     */
    public int computeMediana() {
        if (mediana < 0) {
            int medianIndex = (image.getWidth() * image.getHeight()) / 2;
            int[] imageArray = matrixToArray();
            Arrays.sort(imageArray);
            mediana = imageArray[medianIndex];
        }
        return mediana;
    }
    
    /**
     * Calcula a moda da imagem
     * 
     * @return int
     */
    public int computeModa() {
        if (moda < 0) {
            int modaColor = -1;
            int modaColorQtd = -1;
            int processingColor = -1;
            int processingColorQtd = 1;
            int[] imageArray = matrixToArray();
            Arrays.sort(imageArray);
            for (int i = 0; i < imageArray.length; i++) {
                if (processingColor != imageArray[i]) {
                    if (processingColorQtd > modaColorQtd) {
                        modaColor = processingColor;
                        modaColorQtd = processingColorQtd;
                    }
                    processingColor = imageArray[i];
                    processingColorQtd = 1;
                } else {
                    processingColorQtd++;
                }
            }
            if (processingColorQtd > modaColorQtd) {
                modaColor = processingColor;
            }
            moda = modaColor;
        }
        return moda;
    }
    
    /**
     * Calcula a variância
     * 
     * @return float
     */
    public float computeVariancia() {
        if (variancia < 0) {
            int soma = 0;
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    float aux = (image.getPixels()[i][j] - media);
                    soma += (aux * aux);
                }
            }
            variancia = soma / (image.getWidth() * image.getHeight());
        }
        return variancia;
    }
    
    /**
     * Carrega o histograma da imagem
     * 
     * @return int[]
     */
    public int[] loadHistograma() {
        if (histograma == null) {
            histograma = new int[256];
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    histograma[image.getPixels()[i][j]]++;
                }
            }
        }
        return histograma;
    }
    
    /**
     * Converte a matriz de pixels para vetor
     * 
     * @return int[]
     */
    private int[] matrixToArray() {
        int[] imageArray = new int[image.getWidth() * image.getHeight()];
        int ind = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                imageArray[ind] = image.getPixels()[i][j];
                ind++;
            }
        }
        return imageArray;
    }
    
}
