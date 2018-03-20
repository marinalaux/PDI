package pdi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Painel de exibição dos parâmetros de rotação da imagem
 * 
 * @author Marina
 */
public class RotationParametersView extends JPanel {

    /** Parâmetros para rotação da imagem */
    private RotationParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public RotationParametersView(Consumer<RotationParametersBean> callback) {
        super();
        this.params = new RotationParametersBean();
        params.setRight(true);
        params.setLeft(false);
        setBorder(BorderFactory.createTitledBorder("Rotação"));
        JComboBox rotationType = new JComboBox(new String[] {"Direita", "Esquerda"});
        rotationType.setSelectedIndex(0);
        rotationType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotationType.getSelectedIndex() == 0) {
                    params.setRight(true);
                    params.setLeft(false);
                } else {
                    params.setLeft(true);
                    params.setRight(false);
                }
            }
        });
        JTextField rotationDegrees = new JTextField(3);
        rotationDegrees.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                params.setAngle(Integer.parseInt(rotationDegrees.getText()));
            }
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(new JLabel("Direção"));
        add(rotationType);
        add(new JLabel("Ângulo"));
        add(rotationDegrees);
        add(aplicar);
    }
    
}
