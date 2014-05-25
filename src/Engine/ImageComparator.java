package Engine;

import Engine.FileManager.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 */
public class ImageComparator {

    public static double cTI(BufferedImage currentImage, BufferedImage targetImage)
    {
        long pixDiffSum = 0;
        for (int i = 0; i < targetImage.getHeight(); i++)
        {
            for (int j = 0; j < targetImage.getWidth(); j++)
            {
                Color currentColor = new Color(currentImage.getRGB(j, i));
                Color targetColor = new Color(targetImage.getRGB(j, i));

                int pixDiff = Math.abs(currentColor.getRed() - targetColor.getRed()) +
                        Math.abs(currentColor.getGreen() - targetColor.getGreen()) +
                        Math.abs(currentColor.getBlue() - targetColor.getBlue()) +
                        Math.abs(currentColor.getAlpha() - targetColor.getAlpha());
                pixDiffSum += pixDiff;

                //System.out.format("%4d ", pixDiff);
            }
            //System.out.println();
        }
        return pixDiffSum / (double) (targetImage.getHeight() * targetImage.getWidth());
    }

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

        System.out.println("Długość tablicy pikseli: " + targetImagePixels.length);
        for (int i = 0; i < targetImagePixels.length; i++) {
            accordingPixels += Math.abs(targetImagePixels[i] - currentImagePixels[i]);
            System.out.print(targetImagePixels[i] + " ");
        }

        for (int i = 0; i < targetImagePixels.length; i++) {
            System.out.print(currentImagePixels[i] + " ");
        }
        compatibilityFactor = (double)accordingPixels / targetImagePixels.length;
        return compatibilityFactor;
    }

}
