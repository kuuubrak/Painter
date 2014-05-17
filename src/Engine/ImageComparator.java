package Engine;

import Engine.FileManager.ImageManager;

import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 */
public class ImageComparator {

    byte[] currentImagePixels;
    byte[] targetImagePixels;

    public double compareTwoImages(BufferedImage currentImage, BufferedImage targetImage) {
        double compatibilityFactor;
        loadImagesAsPixelsBuffers(currentImage, targetImage);
        compatibilityFactor = compare();
        return compatibilityFactor;
    }

    private void loadImagesAsPixelsBuffers (BufferedImage currentImage, BufferedImage targetImage) {
        ImageManager imageManager = new ImageManager();
        this.currentImagePixels = imageManager.convertImageToPixelMap(currentImage);
        this.targetImagePixels = imageManager.convertImageToPixelMap(targetImage);
    }

    private double compare() {
        int accordingPixels = 0;
        double compatibilityFactor;

        for (int i = 0; i < targetImagePixels.length; i++) {
            if (currentImagePixels[i] == targetImagePixels[i]) {
                accordingPixels++;
            }
        }
        compatibilityFactor = (double)accordingPixels / targetImagePixels.length;
        return compatibilityFactor;
    }

}
