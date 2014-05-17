package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 13.05.2014.
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(Dimension dimension, BufferedImage image ) {
        setSize(dimension);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}
