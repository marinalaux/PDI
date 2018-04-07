package pdi;

import commons.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    /** Imagem para exibição */
    private JLabel imageLabel;
    /** Imagem */
    private Image image;

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
        imageLabel = new JLabel();
        imageLabel.addMouseListener(new MouseListener() {
            
            PixelsView view;
            
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if (view == null) {
                    view = new PixelsView(image, e);
                    view.setVisible(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (view != null) {
                    view.dispose();
                    view = null;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        add(imageLabel);
    }
    
    /**
     * Atualiza a imagem exibida
     * 
     * @param image 
     */
    public void updateImage(Image image) {
        this.imageLabel.setIcon(new ImageIcon(image.toBufferedImage()));
        this.image = new Image(image.getHeight(), image.getWidth());
        this.image.setPixels(image.getPixels());
    }
    
}
