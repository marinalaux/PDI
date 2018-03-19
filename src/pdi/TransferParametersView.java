package pdi;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Painel de exibição dos parâmetros para transladar a imagem
 * 
 * @author Marina
 */
public class TransferParametersView extends JPanel {

    /** Parâmetros para translação da imagem */
    private TransferParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public TransferParametersView(Consumer<TransferParametersBean> callback) {
        super();
        this.params = new TransferParametersBean();
        setBorder(BorderFactory.createTitledBorder("Translação"));
        JTextField transferX = new JTextField("0", 5);
        transferX.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                params.setTransferX(Integer.parseInt(transferX.getText()));
            }
        });
        JTextField transferY = new JTextField("0", 5);
        transferY.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                params.setTransferY(Integer.parseInt(transferY.getText()));
            }
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(new JLabel("Transladar em X"));
        add(transferX);
        add(new JLabel("Transladar em Y"));
        add(transferY);
        add(aplicar);
    }

}
