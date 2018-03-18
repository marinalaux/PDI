package pdi;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Painel de exibição das estatísticas da imagem
 * 
 * @author Marina
 */
public class ImageStatisticsView extends JPanel {

    /**
     * Construtor
     * 
     * @param borderTitle
     * @param media
     * @param mediana
     * @param moda
     * @param variancia
     */
    public ImageStatisticsView(String borderTitle, float media, int mediana, int moda, float variancia) {
        super();
        // Apenas cria a borda do painel caso tenha um título para ele
        if (!borderTitle.isEmpty()) {
            setBorder(BorderFactory.createTitledBorder(borderTitle));
        }
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.add(showImageStatistics(media, mediana, moda, variancia));
        add(statisticsPanel);
    }
    
    /**
     * Exibe estatísticas da imagem
     * 
     * @param media
     * @param mediana
     * @param moda
     * @param variancia
     * @return 
     */
    private JPanel showImageStatistics(float media, int mediana, int moda, float variancia) {
        JPanel statisticsGrid = new JPanel(new GridLayout(4, 2, 20, 0));
        statisticsGrid.add(new JLabel("Média:"));
        statisticsGrid.add(new JLabel(String.valueOf(media)));
        statisticsGrid.add(new JLabel("Mediana:"));
        statisticsGrid.add(new JLabel(String.valueOf(mediana)));
        statisticsGrid.add(new JLabel("Moda:"));
        statisticsGrid.add(new JLabel(String.valueOf(moda)));
        statisticsGrid.add(new JLabel("Variância:"));
        statisticsGrid.add(new JLabel(String.valueOf(variancia)));
        return statisticsGrid;
    }
    
}
