package Engine.FileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Created by tomasz on 12.05.2014.
 */


public class ImageLoader {

    public static final String examplePath = "resources/example.png";
    private BufferedImage loadedImage = null;

    public ImageLoader() {
    }

    public byte[] loadImageAsPixelBuffer(String imagePath) {
        byte[] bufferWithImagePixels;
        try {
            loadedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
        }
        bufferWithImagePixels = convertImageToPixelMap(loadedImage);

        return bufferWithImagePixels;
    }

    public Dimension getImageSize() {
        int height =  loadedImage.getHeight();
        int width = loadedImage.getWidth();
        Dimension imageDimension = new Dimension(width, height);
        return imageDimension;
    }

    private byte[] convertImageToPixelMap(BufferedImage loadedImage) {
        byte[] pixels = ((DataBufferByte)loadedImage.getRaster().getDataBuffer()).getData();
        return pixels;
    }

}
