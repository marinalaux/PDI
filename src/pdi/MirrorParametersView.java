package pdi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * Painel de exibição dos parâmetros para espelhamento da imagem
 * 
 * @author Marina
 */
public class MirrorParametersView extends JPanel {

    /** Parâmetros para espelhamento */
    private MirrorParametersBean params;
    
    /**
     * Construtor
     * 
     * @param callback
     */
    public MirrorParametersView(Consumer<MirrorParametersBean> callback) {
        super();
        this.params = new MirrorParametersBean();
        setBorder(BorderFactory.createTitledBorder("Espelhamento"));
        JCheckBox horizontal = new JCheckBox("Horizontal");
        horizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                params.setHorizontal(horizontal.isSelected());
            }
        });
        JCheckBox vertical = new JCheckBox("Vertical");
        vertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                params.setVertical(vertical.isSelected());
            }
        });
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener((e) -> callback.accept(params));
        add(horizontal);
        add(vertical);
        add(aplicar);
    }
    
}
