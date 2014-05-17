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
 * Created by tomasz on 12.05.2014.
 */


public class ImageManager {

    public static final String examplePath = "resources/example.png";

    public ImageManager() {
    }

    public BufferedImage loadImage(String imagePath) {
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
        }
        return loadedImage;
    }

    public Dimension getImageSize(BufferedImage loadedImage) {
        int height =  loadedImage.getHeight();
        int width = loadedImage.getWidth();
        Dimension imageDimension = new Dimension(width, height);
        return imageDimension;
    }

    public BufferedImage intializeStartingImage (Dimension dimension) {
        BufferedImage newImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D imageGraphics  = newImage.createGraphics();
        imageGraphics.setPaint(Color.WHITE);
        imageGraphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        return newImage;
    }

    public byte[] convertImageToPixelMap(BufferedImage loadedImage) {
        byte[] pixels = ((DataBufferByte)loadedImage.getRaster().getDataBuffer()).getData();
        return pixels;
    }

    public static BufferedImage bufferedImageDeepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
