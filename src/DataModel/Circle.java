package DataModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by tomasz on 12.05.2014.
 * Modified by mariusz.
 * Pojedynczy okrag skladajacy sie na osobnika populacji
 * Okreslone sa procesy stochastyczne generujace jego specyfike
 * te zwiazane z mutacja okreslone sa przez rozklad normalny ze zmiennym odchyleniem standardowym i stala srednia
 * (metody mutate*)
 * te zwiazane z generowaniem pierwszego osobnika sa okreslone przez rozklad jednorodny na zdefiniowanych przedzialach
 * (metody *random*)
 */


public class Circle implements Cloneable {
    private final Dimension dimension;

    // do modyfikacji odchylenia standardowego rozkladu Gaussa; jezeli caly zakres bylby za duzy
    private static final int gaussDivisor = 1;
    
    private static final int maxRandomRadius = 50;
    private static final int minRandomRadius = 3;

    //private static final float maxColorComponentValue = 1.0f;
    //private static final float minFieldValue = 0.0f;
    // Required, because Color constructor requires sometimes value in 0.0 - 1.0 range
    private static final float creationWithAlphaFactor = 255;

    // promien
    private int radius;
    // przezroczystosc
    private float alpha;
    private Point centerPoint;
    private Color fillColor;

    public static Circle newRandomCircle(Dimension dimension)
    {
        Circle circle = new Circle(dimension);
        circle.randomize(dimension.width, dimension.height);
        return circle;
    }

    public Circle(Circle circle) {
        this.dimension = circle.dimension;
        this.radius = circle.radius;
        this.fillColor = createColorWithAlpha(circle.fillColor, alpha);
        this.alpha = circle.alpha;
        this.centerPoint = new Point((int)circle.centerPoint.getX(), (int)circle.centerPoint.getY());
    }

    public Circle(Dimension dimension, int radius, float alpha, Point centerPoint, Color fillColor) {
        this.dimension = dimension;
        this.radius = radius;
        this.fillColor = createColorWithAlpha(fillColor, alpha);
        this.alpha = alpha;
        this.centerPoint = centerPoint;
    }

    private Circle(final Dimension dimension) {
        this.dimension = dimension;
    }

    public void mutateCenterPoint(double sigma)
    {
        Random normalGenerator = new Random();

        centerPoint.x = (int) Math.abs((centerPoint.x + normalGenerator.nextGaussian() * sigma * dimension.getWidth()/gaussDivisor) % dimension.getWidth());
        centerPoint.y = (int) Math.abs((centerPoint.y + normalGenerator.nextGaussian() * sigma * dimension.getHeight()/gaussDivisor) % dimension.getHeight());
    }

    public void mutateColor(double sigma)
    {
        Random normalGenerator = new Random();

        int newRed = (int) Math.abs((fillColor.getRed() + normalGenerator.nextGaussian() * sigma * 255/gaussDivisor) % 255);
        int newGreen = (int) Math.abs((fillColor.getGreen() + normalGenerator.nextGaussian() * sigma * 255/gaussDivisor) % 255);
        int newBlue = (int) Math.abs((fillColor.getBlue() + normalGenerator.nextGaussian() * sigma * 255/gaussDivisor) % 255);
        fillColor = new Color(newRed, newGreen, newBlue);

        // modyfikacja alfy odbywa sie teraz niezaleznie od modyfikacji koloru
        //alpha = (float) Math.abs((alpha + normalGenerator.nextGaussian() * sigma/gaussDivisor) % 1);
    }
    
    // modyfikacja koloru
    public void mutateAlpha( double sigma )
    {
    	Random normalGenerator = new Random();
    	alpha = (float) Math.abs((alpha + normalGenerator.nextGaussian() * sigma/gaussDivisor) % 1);
    }

    public void mutateRadius(double sigma)
    {
        Random normalGenerator = new Random();

        radius = (int) Math.abs((radius-minRandomRadius + normalGenerator.nextGaussian() * sigma * (maxRandomRadius-minRandomRadius)/gaussDivisor) % maxRandomRadius-minRandomRadius) + minRandomRadius;
    }

    private void randomize(int maxWidth, int maxHeight) {
        Random random = new Random();

        centerPoint = new Point();
        fillColor = createRandomColor(random);

        alpha = random.nextFloat();
        centerPoint.x = random.nextInt(maxWidth);
        centerPoint.y = random.nextInt(maxHeight);

        radius = (int)(random.nextFloat() * (maxRandomRadius-minRandomRadius) + minRandomRadius);
    }

    private Color createRandomColor (Random random) {
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        Color newColor = new Color(red, green, blue);
        return newColor;
    }

    @Override
    public String toString()
    {
        String s = "radius: " + radius + "; alpha: " + alpha + "; X: " + centerPoint.x + "; Y: " + centerPoint.y + "; R: " + fillColor.getRed() + "; G: " + fillColor.getGreen() + "; B: " + fillColor.getBlue();
        return s;
    }


    public void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.fillColor = createColorWithAlpha(this.fillColor, alpha);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public float getAlpha() {
        return alpha;

    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getColorWithAlpha() {
        Color colorWithAlpha = new Color(fillColor.getRed() / creationWithAlphaFactor,
                fillColor.getGreen() / creationWithAlphaFactor,
                fillColor.getBlue() / creationWithAlphaFactor,
                alpha);
        return  colorWithAlpha;
    };

    private Color createColorWithAlpha(Color color, float alpha) {
            Color colorWithAlpha = new Color(color.getRed() / creationWithAlphaFactor,
                                         color.getGreen() / creationWithAlphaFactor,
                                         color.getBlue() / creationWithAlphaFactor,
                                         alpha);
        return  colorWithAlpha;
    };
}
