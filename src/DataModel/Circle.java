package DataModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by tomasz on 12.05.2014.
 */


public class Circle implements Cloneable {

    private static final int maxRandomRadius = 50;
    private static final float maxColorComponentValue = 1.0f;
    private static final float minFieldValue = 0.0f;
    // Required, because Color constructor requires sometimes value in 0.0 - 1.0 range
    private static final float creationWithAlphaFactor = 255;

    private int radius;
    private float alpha;
    private Point centerPoint;
    private Color fillColor;

    public Circle() {
    }

    public Circle(Circle circle) {
        this.radius = circle.radius;
        this.fillColor = createColorWithAlpha(circle.fillColor, alpha);
        this.alpha = circle.alpha;
        this.centerPoint = new Point((int)circle.centerPoint.getX(), (int)circle.centerPoint.getY());
    }

    public Circle(int radius, float alpha, Point centerPoint, Color fillColor) {
        this.radius = radius;
        this.fillColor = createColorWithAlpha(fillColor, alpha);
        this.alpha = alpha;
        this.centerPoint = centerPoint;
    }

    public void randomize(int maxWidth, int maxHeight) {
        Random random = new Random();
        centerPoint = new Point();
        fillColor = createRandomColor(random);
        float alpha = random.nextFloat();
        setAlpha(alpha);
        centerPoint.x = random.nextInt(maxWidth);
        centerPoint.y = random.nextInt(maxHeight);
        radius = (int)(random.nextFloat() * maxRandomRadius);
    }

    public void modifyColorWithRandomValues(float red, float green, float blue, float alpha) {
        float newRed = fillColor.getRed()/creationWithAlphaFactor + red;
        float newGreen = fillColor.getGreen()/creationWithAlphaFactor + green;
        float newBlue = fillColor.getBlue()/creationWithAlphaFactor + blue;
        float newAlpha = this.alpha + alpha;

        newRed = validateValue(newRed, maxColorComponentValue);
        newGreen = validateValue(newGreen, maxColorComponentValue);
        newBlue = validateValue(newBlue, maxColorComponentValue);
        newAlpha = validateValue(newAlpha, maxColorComponentValue);

        fillColor = new Color(newRed, newGreen, newBlue, newAlpha);
        this.alpha = newAlpha;
    }

    public void modifySizeAndLocationWithRandomValues(float randomX, float randomY, Dimension maxDimension, float randomRadius) {
            int newRadius = (int) (this.radius + randomRadius * maxRandomRadius);
            double newX = this.centerPoint.getX() + randomX * maxDimension.getWidth();
            double newY = this.centerPoint.getY() + randomY * maxDimension.getHeight();
            newRadius = (int) validateValue((float)newRadius, (float) maxRandomRadius);
            newX = (int)validateValue((float)newX, (float)maxDimension.getWidth());
            newY = (int)validateValue((float)newY, (float)maxDimension.getHeight());
            this.radius = newRadius;
            this.centerPoint.setLocation(newX, newY);
    }

    private float validateValue(float value, float maxValue) {
        if (value > maxValue) {
            value = maxValue;
        } else if (value < minFieldValue) {
            value = minFieldValue;
        }
        return value;
    }

    private Color createRandomColor (Random random) {
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        Color newColor = new Color(red, green, blue);
        return newColor;
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

    private Color createColorWithAlpha(Color color, float alpha) {
            Color colorWithAlpha = new Color(color.getRed() / creationWithAlphaFactor,
                                         color.getGreen() / creationWithAlphaFactor,
                                         color.getBlue() / creationWithAlphaFactor,
                                         alpha);
        return  colorWithAlpha;
    };
}
