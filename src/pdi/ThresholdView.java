package pdi;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Painel de exibição do threshold
 * 
 * @author Marina
 */
public class ThresholdView extends JPanel {

    /** Parâmetros */
    private ThresholdBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public ThresholdView(Consumer<ThresholdBean> callback) {
        super();
        this.params = new ThresholdBean();
        setBorder(BorderFactory.createTitledBorder("Threshold"));
        JTextField thresholdValue = new JTextField(3);
        thresholdValue.setText("0");
        JSlider threshold = new JSlider(0, 255, 0);
        threshold.setPreferredSize(new Dimension(700, 60));
        threshold.setMajorTickSpacing(10);
        threshold.setMinorTickSpacing(5);
        threshold.setPaintTicks(true);
        thresholdValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                threshold.setValue(Integer.parseInt(thresholdValue.getText()));
                params.setThreshold(threshold.getValue());
            }
        });
        threshold.addChangeListener((ChangeEvent e) -> {
            thresholdValue.setText(String.valueOf(threshold.getValue()));
            params.setThreshold(threshold.getValue());
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(thresholdValue);
        add(threshold);
        add(aplicar);
    }
    
}
