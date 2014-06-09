package Engine;

/**
 * Created by tomasz on 16.05.2014.
 * Modified by mariusz
 */
public class EngineConstants {
    public static double c1Attribute = 0.82;
    public static double c2Attribute = 1.2;
    // co ile mutacji ma nastapic modyfikacja sigmy
    public static int mAttribute = 10;
    public static double sigmaMinimum = 0.000005;
    public static double exampleStartingSigma = 0.5;
    // minimalna sigma ktora powoduje zatrzmanie ewolucji cechy
    //public static double sigmaDecisionBorder = 0.2;
    // nizej w wersji stalopozycyjnej
    public static double sigmaDecisionBorder = 20;
    // ilosc kol skladajacych sie na osobnika
    public static int noOfGenes = 200;
    // nie uzywane w aktualnej wersji
    public static double probabilityOfMutatingGen = 0.2;
}
