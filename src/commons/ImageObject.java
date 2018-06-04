package commons;

/**
 * Objeto da imagem
 */
public class ImageObject {
    
    /** Perímetro do objeto */
    private int perimetro;
    /** Área do objeto */
    private int area;
    /** Circularidade do objeto */
    private double circularidade;
    /** Cor do objeto */
    private int cor;
    
    public int getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(int perimetro) {
        this.perimetro = perimetro;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public double getCircularidade() {
        return circularidade;
    }

    public void setCircularidade(double circularidade) {
        this.circularidade = circularidade;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }
    
}
