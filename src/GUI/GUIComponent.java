package GUI;

import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 */
public interface GUIComponent {

    public void updateCurrentImage(BufferedImage currentImage);

    public void algorithmStopped();

}
