package pdi;

/**
 * Bean dos parâmetros para rotação da imagem
 * 
 * @author Marina
 */
public class RotationParametersBean {
    
    /** Graus de rotação */
    private int angle;
    /** Rotaciona para esquerda */
    private boolean left;
    /** Rotaciona para direita */
    private boolean right;

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    
    /**
     * Retorna matriz para rotação da imagem
     * 
     * @return Matriz para rotação
     */
    public double[][] getMatrix() {
        int rightMultiplier = 1;
        int leftMultiplier = 1;
        if (right) {
            rightMultiplier = rightMultiplier * -1;
        }
        if (left) {
            leftMultiplier = leftMultiplier * -1;
        }
        return new double[][] {
            {                  Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)) * leftMultiplier, 0},
            {Math.sin(Math.toRadians(angle)) * rightMultiplier,                  Math.cos(Math.toRadians(angle)), 0},
            {                                                0,                                                0, 1}};
    }
    
}
