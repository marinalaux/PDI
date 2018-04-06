package pdi;

import java.awt.Dimension;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 * Painel de exibição dos parâmetros para aplicação de contraste na imagem
 * 
 * @author Marina
 */
public class ContrastParametersView extends JPanel {

    /** Parâmetros para aplicação de contraste */
    private ContrastParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public ContrastParametersView(Consumer<ContrastParametersBean> callback) {
        super();
        this.params = new ContrastParametersBean();
        params.setContrast(1);
        setBorder(BorderFactory.createTitledBorder("Contraste"));
        JLabel contrastValue = new JLabel("1.0");
        JSlider contrast = new JSlider(0, 50, 10);
        contrast.setPreferredSize(new Dimension(700, 60));
        contrast.setMajorTickSpacing(5);
        contrast.setMinorTickSpacing(1);
        contrast.setPaintTicks(true);
        contrast.addChangeListener((ChangeEvent e) -> {
            params.setContrast((double) contrast.getValue() / 10);
            contrastValue.setText(String.valueOf(params.getContrast()));
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(contrastValue);
        add(contrast);
        add(aplicar);
    }
    
}
