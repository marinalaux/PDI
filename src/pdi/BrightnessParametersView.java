package pdi;

import java.awt.Dimension;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 * Painel de exibição dos parâmetros para aplicação de brilho na imagem
 * 
 * @author Marina
 */
public class BrightnessParametersView extends JPanel {

    /** Parâmetros para aplicação de brilho */
    private BrightnessParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public BrightnessParametersView(Consumer<BrightnessParametersBean> callback) {
        super();
        this.params = new BrightnessParametersBean();
        params.setBrightness(0);
        setBorder(BorderFactory.createTitledBorder("Brilho"));
        JSlider brightness = new JSlider(-100, 100, 0);
        brightness.setPreferredSize(new Dimension(700, 60));
        brightness.setMajorTickSpacing(10);
        brightness.setMinorTickSpacing(5);
        brightness.setPaintLabels(true);
        brightness.setPaintTicks(true);
        brightness.addChangeListener((ChangeEvent e) -> {
            params.setBrightness(brightness.getValue());
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(brightness);
        add(aplicar);
    }
    
}
