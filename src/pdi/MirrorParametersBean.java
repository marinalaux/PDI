package pdi;

/**
 * Bean dos parâmetros para espelhamento
 * 
 * @author Marina
 */
public class MirrorParametersBean {
    
    /** Espelhamento horizontal */
    private boolean horizontal;
    /** Espelhamento vertical */
    private boolean vertical;

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
    
    /**
     * Retorna matriz para espelhamento
     * 
     * @return Matriz para espelhamento
     */
    public double[][] getMatrix() {
        return new double[][] {{horizontal?-1:1,             0, 0},
                               {              0, vertical?-1:1, 0},
                               {              0,             0, 1}};
    }
    
}
