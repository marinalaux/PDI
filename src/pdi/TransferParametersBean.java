package pdi;

/**
 * Bean dos parâmetros para transladar a imagem
 * 
 * @author Marina
 */
public class TransferParametersBean {
    
    /** Quantidade de pixels a transladar horizontalmente */
    private int transferX;
    /** Quantidade de pixels a transladar verticalmente */
    private int transferY;

    public void setTransferX(int transferX) {
        this.transferX = transferX;
    }

    public void setTransferY(int transferY) {
        this.transferY = transferY;
    }
    
    /**
     * Retorna a matriz para transladar a imagem
     * 
     * @return Matriz para transladar
     */
    public double[][] getMatrix() {
        return new double[][] {{        1,         0, 0},
                               {        0,         1, 0},
                               {transferX, transferY, 1}};
    }
    
}
