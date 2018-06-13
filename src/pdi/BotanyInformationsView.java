package pdi;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Painel de informações da imagem botânica
 */
public class BotanyInformationsView extends JPanel {

    /**
     * Construtor
     * 
     * @param treatment
     */
    public BotanyInformationsView(String treatment) {
        super();
        setBorder(BorderFactory.createTitledBorder("Tratamento da folha"));
        JLabel treatmentLabel = new JLabel(treatment);
        add(treatmentLabel);
    }
    
}
