package Engine;

import DataModel.Circle;
import Engine.FileManager.ImageManager;
import GUI.GUIComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tomasz on 16.05.2014.
 *
 * Klasa zawierająca główny algorytm genetyczny.
 */
public class ImageRecreator {

    private final int noOfGenes;
    private double sigma;
    private int m;

    private GUIComponent mainFrame;

    public ImageRecreator(GUIComponent mainFrame) {
        this.mainFrame = mainFrame;
        noOfGenes = EngineConstants.noOfGenes;
        sigma = EngineConstants.exampleStartingSigma;
        m = EngineConstants.mAttribute;
    }

    public void recreateImage(BufferedImage targetImage) {
        //Ustawianie parametrów populacji
        Dimension imageSize = new Dimension(targetImage.getWidth(), targetImage.getHeight());
        GenerationsCreator.setDimension(imageSize);
        GenerationsCreator.setNoOfGenes(noOfGenes);

        int noOfChosenChildren = 0;
        int noOfChosenParents = 0;

        // 1. Tworzenie osobnika początkowego
        GenerationsCreator parent = new GenerationsCreator();
        mainFrame.updateCurrentImage(parent.getBufferedImage());

        do {
            // 2. Generowanie potomka
            GenerationsCreator child = new GenerationsCreator(parent);
            child.mutate(sigma);

            // 3. Wybieranie lepszego osobnika
            //TODO nie trzeba liczyć dwa razy współczynnika dopasowania dla rodzica
            BufferedImage parentImage = parent.getBufferedImage();
            double parentCompatibilityFactor = ImageComparator.compareTwoImages(parentImage, targetImage);
            BufferedImage childImage = child.getBufferedImage();
            double childCompatibilityFactor = ImageComparator.compareTwoImages(childImage, targetImage);

            System.out.println("parentCompatibilityFactor: " + parentCompatibilityFactor);
            System.out.println("childCompatibilityFactor: " + childCompatibilityFactor);
            System.out.println(child);

            if (childCompatibilityFactor < parentCompatibilityFactor)
            {
                parent = child;
                mainFrame.updateCurrentImage(childImage);
                noOfChosenChildren++;
            }
            else
            {
                noOfChosenParents++;
            }

            // 4 i 5. Aktualizacja proporcji wybranych y-ków
            if (noOfChosenChildren + noOfChosenParents == m)
            {
                double phi = noOfChosenChildren / m;

                if (phi < EngineConstants.sigmaDecisionBorder)
                {
                    sigma *= EngineConstants.c1Attribute;
                }
                else if (phi > EngineConstants.sigmaDecisionBorder)
                {
                    sigma *= EngineConstants.c2Attribute;
                }

                noOfChosenChildren = 0;
                noOfChosenParents = 0;
            }

            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        } while (sigma > EngineConstants.sigmaMinimum);
    }
}
