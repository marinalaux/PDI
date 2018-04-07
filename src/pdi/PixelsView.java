package pdi;

import commons.Image;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Visualização dos pixels da imagem
 * 
 * @author Marina
 */
public class PixelsView extends JFrame {
    
    public PixelsView(Image image, MouseEvent event) {
        getContentPane().setLayout(new GridLayout(21, 21, 1, 1));
        setUndecorated(true);
        setSize(600, 600);
        Point mousePositionOnScreen = event.getLocationOnScreen();
        int x = Math.max(0, mousePositionOnScreen.x - getWidth()/2);
        int y = 400;
        setLocation(x, y);
        getContentPane().setBackground(Color.WHITE);
        
        Point mousePositionOnImage = event.getPoint();
        int startX = Math.max(0, (int)mousePositionOnImage.getX() -10);
        int startY = Math.max(0, (int)mousePositionOnImage.getY() -10);
        int endX = Math.min(startX + 21, image.getWidth());
        int endY = Math.min(startY + 21, image.getHeight());
        
        for (int j = startY; j < endY; j++) {
            for (int i = startX; i < endX; i++) {
                int v = image.getPixels()[i][j];
                JLabel l = new JLabel(String.valueOf(v));
                l.setOpaque(true);
                l.setBackground(new Color(v, v, v));
                if (v < 128) {
                    l.setForeground(Color.WHITE);
                }
                getContentPane().add(l);
            }
        }
    }
    
}
