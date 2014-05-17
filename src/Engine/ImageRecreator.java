package Engine;

import DataModel.Circle;
import Engine.FileManager.ImageManager;
import GUI.GUIComponent;

import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 */
public class ImageRecreator {

    private GUIComponent mainFrame;
    private BufferedImage targetImage;
    private BufferedImage currentImage;
    private double compatibilityFactor;

    public ImageRecreator(GUIComponent mainFrame) {
        this.mainFrame = mainFrame;
        compatibilityFactor = 0;
    }

    public void recreateImage(BufferedImage targetImage, BufferedImage currentImage) {
        Circle baseCircle = new Circle();
        baseCircle.randomize(currentImage.getWidth(), currentImage.getHeight());
        GenerationsCreator generationCreator = new GenerationsCreator(baseCircle);
        generationCreator.setBeginningCompatibilityFactor(currentImage, targetImage);
        compatibilityFactor = generationCreator.getCompatibilityFactor();

        while (generationCreator.getSigmaAttribute() > EngineConstants.sigmaMinimum) {
            BufferedImage newGeneration = generationCreator.createNextGeneration(currentImage, targetImage);
            double newCompatibilityFactor = generationCreator.getCompatibilityFactor();

            if (newCompatibilityFactor > compatibilityFactor) {
                compatibilityFactor = newCompatibilityFactor;
                currentImage = ImageManager.bufferedImageDeepCopy(newGeneration);
                mainFrame.updateCurrentImage(currentImage);
            }

        }
    }
}
