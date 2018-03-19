package pdi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Painel de exibição dos parâmetros para redimensionamento da imagem
 * 
 * @author Marina
 */
public class ResizeParametersView extends JPanel {

    /** Parâmetros para redimensionamento da imagem */
    private ResizeParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public ResizeParametersView(Consumer<ResizeParametersBean> callback) {
        super();
        this.params = new ResizeParametersBean();
        params.setResize(1);
        setBorder(BorderFactory.createTitledBorder("Redimensionamento"));
        JLabel resizeNumber = new JLabel("-1x");
        JComboBox resizeType = new JComboBox(new String[] {"Ampliar", "Reduzir"});
        resizeType.setSelectedIndex(1);
        JSlider resize = new JSlider(1, 10, 1);
        resize.setMajorTickSpacing(1);
        resize.setMinorTickSpacing(1);
        resize.setPaintLabels(true);
        resize.setPaintTicks(true);
        resizeType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resizeNumber.setText(updateResizeLabel(resizeType.getSelectedIndex() == 0, resize.getValue()));
                params.setResize(computeResize(resizeType.getSelectedIndex() == 0, resize.getValue()));
            }
        });
        resize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                resizeNumber.setText(updateResizeLabel(resizeType.getSelectedIndex() == 0, resize.getValue()));
                params.setResize(computeResize(resizeType.getSelectedIndex() == 0, resize.getValue()));
            }
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(resizeType);
        add(resize);
        add(resizeNumber);
        add(aplicar);
    }
    
    /**
     * Retorna label de exibição da ampliação/redução da imagem
     * 
     * @param resize
     * @param resizeType
     * @return Label de ampliação/redução
     */
    private String updateResizeLabel(boolean zoomIn, int resize) {
        // Se está ampliando a imagem
        if (zoomIn) {
            return String.valueOf(resize)+ "x";
        } else {
            return "-" + String.valueOf(resize)+ "x";
        }
    }
    
    /**
     * Calcula redimensionamento da imagem
     * 
     * @param zoomIn
     * @param resize
     * @return Redimensionamento
     */
    private double computeResize(boolean zoomIn, int resize) {
        // Se está ampliando a imagem
        if (zoomIn) {
            return resize;
        } else {
            return (double)1 / resize;
        }
    }
    
}
