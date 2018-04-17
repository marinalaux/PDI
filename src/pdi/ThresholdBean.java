package pdi;

/**
 * Bean de threshold
 * 
 * @author Marina
 */
public class ThresholdBean {
    
    /** Threshold / Limiarização */
    private int threshold;
    /** Aplica Gauss */
    private boolean gauss;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public boolean isGauss() {
        return gauss;
    }

    public void setGauss(boolean gauss) {
        this.gauss = gauss;
    }
    
}
