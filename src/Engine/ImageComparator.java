package Engine;

import Engine.FileManager.ImageManager;

import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 */
public class ImageComparator {

    public static double compareTwoImages(BufferedImage currentImage, BufferedImage targetImage) {
        double compatibilityFactor;
        byte[] currentImagePixels =  loadImageAsPixelsBuffers(currentImage);
        byte[] targetImagePixels = loadImageAsPixelsBuffers(targetImage);
        compatibilityFactor = compare(currentImagePixels, targetImagePixels);
        return compatibilityFactor;
    }

    private static byte[] loadImageAsPixelsBuffers (BufferedImage image) {
        ImageManager imageManager = new ImageManager();
        return imageManager.convertImageToPixelMap(image);
    }

    /**
     *
     * @param currentImagePixels
     * @param targetImagePixels
     * @return im mniejsza wartość tym lepiej
     */
    private static double compare(byte[] currentImagePixels, byte[] targetImagePixels) {
        long accordingPixels = 0;
        double compatibilityFactor;

        for (int i = 0; i < targetImagePixels.length; i++) {
            accordingPixels += Math.abs(targetImagePixels[i] - currentImagePixels[i]);
            //if (currentImagePixels[i] == targetImagePixels[i]) {
            //    accordingPixels++;
            //}
        }
        compatibilityFactor = (double)accordingPixels / targetImagePixels.length;
        return compatibilityFactor;
    }

}
