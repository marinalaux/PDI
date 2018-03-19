package pdi;

/**
 * Bean dos parâmetros para redimensionamento da imagem
 * 
 * @author Marina
 */
public class ResizeParametersBean {
    
    /** Redimensionamento da imagem */
    private double resize;

    public void setResize(double resize) {
        this.resize = resize;
    }
    
    /**
     * Retorna a matriz para redimensionar a imagem
     * 
     * @return Matriz para redimensionar
     */
    public double[][] getMatrix() {
        return new double[][] {{resize,      0, 0},
                               {     0, resize, 0},
                               {     0,      0, 1}};
    }
    
}
