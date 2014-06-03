package Engine;

import DataModel.Circle;
import Engine.FileManager.ImageManager;
import GUI.GUIComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by tomasz on 16.05.2014.
 *
 * Klasa zawierająca główny algorytm genetyczny.
 */
public class ImageRecreator {

    private final int noOfGenes;
    // kazda cecha ma osobna sigme, poniewaz ma osobny rozklad
    private double[] sigma = new double[GenerationsCreator.fNumber];
    private int m;

    private GUIComponent mainFrame;

    public ImageRecreator(GUIComponent mainFrame) {
        this.mainFrame = mainFrame;
        noOfGenes = EngineConstants.noOfGenes;
        for ( int i=0; i < GenerationsCreator.fNumber; ++i )
        	sigma[i] = EngineConstants.exampleStartingSigma;
        m = EngineConstants.mAttribute;
    }

    public void recreateImage(BufferedImage targetImage) {
        //Ustawianie parametrów populacji
        Dimension imageSize = new Dimension(targetImage.getWidth(), targetImage.getHeight());
        GenerationsCreator.setDimension(imageSize);
        GenerationsCreator.setNoOfGenes(noOfGenes);

        // sigmy sa osobne dla cech, to i ich liczniki musza byc osobne
        int[] noOfChosenChildren = new int[GenerationsCreator.fNumber];
    	int[] noOfChosenParents = new int[GenerationsCreator.fNumber];
        for ( int i=0; i < GenerationsCreator.fNumber; ++i )
        {
        	noOfChosenChildren[i] = 0;
        	noOfChosenParents[i] = 0;
        }
        // licznik kolek i cech, poniewaz modyfikujemy po jednym per mutacja
        int cCounter = 0;
        int fCounter = 0;
        // jezeliby losowac modyfikowana ceche/kolo
        Random random = new Random();
        // do wydruku koncowej oceny dopasowania
        double compatibilityFactor = 0;
        
        // 1. Tworzenie osobnika początkowego
        GenerationsCreator parent = new GenerationsCreator();
        mainFrame.updateCurrentImage(parent.getBufferedImage());

        do {
            // 2. Generowanie potomka
            GenerationsCreator child = new GenerationsCreator(parent);
            child.mutate(sigma,cCounter,fCounter);

            // 3. Wybieranie lepszego osobnika
            //TODO nie trzeba liczyć dwa razy współczynnika dopasowania dla rodzica
            BufferedImage parentImage = parent.getBufferedImage();
            double parentCompatibilityFactor = ImageComparator.cTI(parentImage, targetImage);
            BufferedImage childImage = child.getBufferedImage();
            double childCompatibilityFactor = ImageComparator.cTI(childImage, targetImage);

            //System.out.println("parentCompatibilityFactor: " + parentCompatibilityFactor);
            //System.out.println("childCompatibilityFactor: " + childCompatibilityFactor);
            //System.out.println(child);

            if (childCompatibilityFactor < parentCompatibilityFactor)
            {
                parent = child;
                compatibilityFactor = childCompatibilityFactor;
                mainFrame.updateCurrentImage(childImage);
                noOfChosenChildren[fCounter] += 1;
            }
            else
            {
                noOfChosenParents[fCounter] += 1;
            }

            // 4 i 5. Aktualizacja proporcji wybranych y-ków
            if (noOfChosenChildren[fCounter] + noOfChosenParents[fCounter] == m)
            {
            	// nie liczymy na zmiennopozycyjnych, wiec przesuniecie dziesietne
                int phi = noOfChosenChildren[fCounter] * 100 / m;

                if (phi < EngineConstants.sigmaDecisionBorder)
                {
                    sigma[fCounter] *= EngineConstants.c1Attribute;
                }
                else if (phi > EngineConstants.sigmaDecisionBorder)
                {
                    sigma[fCounter] *= EngineConstants.c2Attribute;
                }


                //System.out.println("phi: " + phi + "; sigma: " + sigma + "; noOfChosenChildren: " + noOfChosenChildren);
 
                noOfChosenChildren[fCounter] = 0;
                noOfChosenParents[fCounter] = 0;
            }
            
            // wybor kolejnych cech dla danego kola
            // jezeli obsluzono wszystkie cechy, wybor nastepnego kola
            // ignoruj cechy ktore osiagnely wlasne kryterium stopu
            // wyjdz z petli z cecha bez stopu lub globalny stop
            for ( int i = 0; i < GenerationsCreator.fNumber; ++i)
            {
            	fCounter = (fCounter + 1) % GenerationsCreator.fNumber;
            	if ( fCounter == 0 )
            		cCounter = (cCounter + 1) % noOfGenes;
            		//cCounter = random.nextInt(noOfGenes);
            	if ( sigma[fCounter] > EngineConstants.sigmaMinimum )
            		break;
            }
        	
            //try
            //{
            //    Thread.sleep(1000);
            //}
            //catch (InterruptedException e)
            //{
            //    e.printStackTrace();
            //}

        // jezeli wybrano ceche ze spelnionym warunkiem stopu, to globalny stop
        } while (sigma[fCounter] > EngineConstants.sigmaMinimum);
    	//wydruk oceny wyniku
        System.out.println( "result: " + compatibilityFactor );
    }
}
