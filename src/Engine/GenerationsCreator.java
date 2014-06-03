package Engine;

import DataModel.Circle;
import Engine.FileManager.ImageManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tomasz on 16.05.2014.
 *
 * Klasa reprezentująca jednego osobnika populacji
 */
public class GenerationsCreator {

    static int noOfGenes = 0;
    static Dimension dimension = new Dimension();
    
    // ilosc modyfikowanych cech
    static final int fNumber = 4;
    Random random = new Random();

    // tablica kolek, by wybor kolka byl przejrzysty
    //private List<Circle> circles = new ArrayList<Circle>();
    private Circle[] circles = new Circle[noOfGenes];

    public static void setNoOfGenes(int noOfGenes)
    {
        GenerationsCreator.noOfGenes = noOfGenes;
    }

    public static void setDimension(Dimension dimension)
    {
        GenerationsCreator.dimension = dimension;
    }

    public GenerationsCreator()
    {
        int i = 0;
        while(i < noOfGenes)
        {
            Circle randomCircle = Circle.newRandomCircle(dimension);
            //circles.add(randomCircle);
            circles[i] = randomCircle;
            i++;
        }
    }

    public GenerationsCreator(final GenerationsCreator toBeCopied)
    {
        //Kopiowanie kółek
        /*for (Circle circle : toBeCopied.circles)
        {
            Circle copyOfCircle = new Circle(circle);
            circles.add(copyOfCircle);
        }*/
    	for (int i = 0; i < noOfGenes; ++i)
    	{
    		Circle copyOfCircle = new Circle(toBeCopied.circles[i]);
    		circles[i] = copyOfCircle;
    	}
    }

    // mutuje tylko jedna cecha, dla jednego kolka, z odpowiednia sigma
    public void mutate(final double[] sigma, final int cCounter, final int fCounter)
    {
    	Circle circle = circles[cCounter];
    	if ( fCounter == 0 )
    		circle.mutateCenterPoint(sigma[0]);
    	else if ( fCounter == 1 )
            circle.mutateColor(sigma[1]);
    	else if ( fCounter == 2 ) {
    		circle.mutateRadius(sigma[2]);
    	} else
             circle.mutateAlpha(sigma[3]);
    }

    public BufferedImage getBufferedImage()
    {
        BufferedImage newGenerationImage = ImageManager.intializeStartingImage(dimension);

        //for (Circle circle : circles)
        for ( int i = 0; i < noOfGenes; ++i )
        {
        	Circle circle = circles[i];
            newGenerationImage = paintNewCircle(newGenerationImage, circle);
        }

        return  newGenerationImage;
    }


    private BufferedImage paintNewCircle (BufferedImage currentImage, Circle circleToBeAdded) {
        Graphics2D imageGraphics = currentImage.createGraphics();
        imageGraphics.setPaint(circleToBeAdded.getColorWithAlpha());

        imageGraphics.fillOval(circleToBeAdded.getCenterPoint().x,
                circleToBeAdded.getCenterPoint().y,
                circleToBeAdded.getRadius(),
                circleToBeAdded.getRadius());

        return currentImage;
    }


    @Override
    public String toString()
    {
        String s = "";

        for (Circle circle : circles)
        {
            s += circle + "\n";
        }
        return s;
    }
















/*
    public GenerationsCreator(Circle baseCircle) {
        this.baseCircle = baseCircle;
        this.sigmaAttribute = EngineConstants.exampleStartingSigma;
        this.compatibilityFactor = 0.0;
        this.phiAttribute = 0;
        iterationCounter = 0;
        correctImageCounter = 0;
        this.currentCircle = baseCircle;
    }
    */

    /*
    public BufferedImage createNextGeneration(BufferedImage currentImage, BufferedImage targetImage) {

        BufferedImage newGenerationImage = null;
        newGenerationImage = paintNewCircle(currentImage);

        double newCompatibilityFactor = ImageComparator.compareTwoImages(newGenerationImage, targetImage);
        if (newCompatibilityFactor > compatibilityFactor) {
            compatibilityFactor = newCompatibilityFactor;
            currentCircle = new Circle(newCircle);
            correctImageCounter++;
        }
        iterationCounter++;
        updateAttribues();

        return newGenerationImage;
    }
    */

    /*
    public void setBeginningCompatibilityFactor(BufferedImage currentImage, BufferedImage targetImage) {
        compatibilityFactor = ImageComparator.compareTwoImages(currentImage, targetImage);
    }
    */

    /*
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
    */

}
