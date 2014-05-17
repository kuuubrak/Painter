package Engine;

import DataModel.Circle;
import Engine.FileManager.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by tomasz on 16.05.2014.
 */
public class GenerationsCreator {

    private Circle baseCircle;
    private Circle currentCircle;
    private Circle newCircle;
    private double phiAttribute;
    private double sigmaAttribute;
    private double compatibilityFactor;
    private int iterationCounter;
    private int correctImageCounter;

    public GenerationsCreator(Circle baseCircle) {
        this.baseCircle = baseCircle;
        this.sigmaAttribute = EngineConstants.exampleStartingSigma;
        this.compatibilityFactor = 0.0;
        this.phiAttribute = 0;
        iterationCounter = 0;
        correctImageCounter = 0;
        this.currentCircle = baseCircle;
    }

    public double getCompatibilityFactor() {
        return compatibilityFactor;
    }

    public double getPhiAttribute() {
        return phiAttribute;
    }

    public double getSigmaAttribute() {
        return sigmaAttribute;
    }

    public BufferedImage createNextGeneration(BufferedImage currentImage, BufferedImage targetImage) {

        BufferedImage newGenerationImage = null;
        newGenerationImage = paintNewCircle(currentImage);
        ImageComparator comparator = new ImageComparator();
        double newCompatibilityFactor = comparator.compareTwoImages(newGenerationImage, targetImage);
        if (newCompatibilityFactor > compatibilityFactor) {
            compatibilityFactor = newCompatibilityFactor;
            currentCircle = new Circle(newCircle);
            correctImageCounter++;
        }
        iterationCounter++;
        updateAttribues();

        return newGenerationImage;
    }

    public void setBeginningCompatibilityFactor(BufferedImage currentImage, BufferedImage targetImage) {
        ImageComparator comparator = new ImageComparator();
        compatibilityFactor = comparator.compareTwoImages(currentImage, targetImage);
    }

    private void generateNewCircle(BufferedImage currentImage) {
        Random normalGenerator = new Random();
        newCircle = new Circle(currentCircle);

        float randomPositionX = (float)(normalGenerator.nextGaussian() * sigmaAttribute);
        float randomPositionY = (float)(normalGenerator.nextGaussian() * sigmaAttribute);
        float randomColorRed = (float) (normalGenerator.nextGaussian() * sigmaAttribute);
        float randomColorGreen = (float)(normalGenerator.nextGaussian() * sigmaAttribute);
        float randomColorBlue = (float)(normalGenerator.nextGaussian() * sigmaAttribute);
        float randomColorAlpha = (float) (normalGenerator.nextGaussian() * sigmaAttribute);
        float randomRadius = (float)(normalGenerator.nextGaussian() * sigmaAttribute);

        Dimension imageDimension = new Dimension(currentImage.getWidth(), currentImage.getHeight());

        newCircle.modifyColorWithRandomValues(randomColorRed, randomColorGreen, randomColorBlue, randomColorAlpha);
        newCircle.modifySizeAndLocationWithRandomValues(randomPositionX, randomPositionY, imageDimension, randomRadius);
    }

    private BufferedImage paintNewCircle (BufferedImage currentImage) {
        BufferedImage newImage = ImageManager.bufferedImageDeepCopy(currentImage);
        generateNewCircle(newImage);
        Graphics2D imageGraphics = newImage.createGraphics();
        imageGraphics.setColor(newCircle.getFillColor());
        imageGraphics.fillOval(newCircle.getCenterPoint().x, newCircle.getCenterPoint().y, newCircle.getRadius(), newCircle.getRadius());
        return newImage;
    }

    private void updateAttribues () {
        if (iterationCounter == EngineConstants.mAttribute) {
            iterationCounter = 0;
            phiAttribute = (double)correctImageCounter / (double) EngineConstants.mAttribute;
            if (phiAttribute > EngineConstants.sigmaDecisionBorder) {
                sigmaAttribute = EngineConstants.c2Attribute * sigmaAttribute;
            } else if (phiAttribute < EngineConstants.sigmaDecisionBorder) {
                sigmaAttribute = EngineConstants.c1Attribute * sigmaAttribute;
            }
            correctImageCounter = 0;
        }
    }

}
