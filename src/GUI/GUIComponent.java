package GUI;

import java.awt.image.BufferedImage;

/**
 * @author Tomasz Kubrak
 */
public interface GUIComponent {

    public void updateCurrentImage(BufferedImage currentImage);

    public void algorithmStopped();

}
