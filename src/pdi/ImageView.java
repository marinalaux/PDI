package pdi;

import commons.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Painel de exibição de imagem
 * 
 * @author Marina
 */
public class ImageView extends JPanel {

    /** Imagem */
    private JLabel image;

    /**
     * Construtor
     */
    public ImageView() {
        this("");
    }
    
    /**
     * Construtor
     * 
     * @param borderTitle
     */
    public ImageView(String borderTitle) {
        super();
        // Apenas cria a borda do painel caso tenha um título para ele
        if (!borderTitle.isEmpty()) {
            setBorder(BorderFactory.createTitledBorder(borderTitle));
        }
        image = new JLabel();
        add(image);
    }
    
    /**
     * Atualiza a imagem exibida
     * 
     * @param image 
     */
    public void updateImage(Image image) {
        this.image.setIcon(new ImageIcon(image.toBufferedImage()));
    }
    
}
