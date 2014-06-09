package Engine.FileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author Tomasz Kubrak
 */


public class ImageManager {

    public static final String examplePath = "resources/example.png";

    public ImageManager() {
    }

    public BufferedImage loadImage(String imagePath) {
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File(imagePath));
        }
        catch (IOException e)
        {
            throw new RuntimeException("ImageManager.loadImage error (" + imagePath + ")");
        }
        return loadedImage;
    }

    public Dimension getImageSize(BufferedImage loadedImage) {
        int height =  loadedImage.getHeight();
        int width = loadedImage.getWidth();
        return new Dimension(width, height);
    }

    public static BufferedImage intializeStartingImage (Dimension dimension) {
        BufferedImage newImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D imageGraphics  = newImage.createGraphics();
        imageGraphics.setPaint(Color.WHITE);
        imageGraphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        return newImage;
    }

    public byte[] convertImageToPixelMap(BufferedImage loadedImage) {
        return ((DataBufferByte)loadedImage.getRaster().getDataBuffer()).getData();
    }

    public static BufferedImage bufferedImageDeepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
