package DataModel;

import java.awt.*;

/**
 * Created by tomasz on 12.05.2014.
 */


public class Circle {

    // Required, because Color constructor requires sometimes value in 0.0 - 1.0 range
    private static final float creationWithAlphaFactor = 255;
    private float radius;
    private float alpha;
    private Point centerPoint;
    private Color fillColor;

    public Circle(float radius, float alpha, Point centerPoint, Color fillColor) {
        this.radius = radius;
        this.fillColor = createColorWithAlpha(fillColor, alpha);
        this.alpha = alpha;
        this.centerPoint = centerPoint;
    }

    public void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.fillColor = createColorWithAlpha(this.fillColor, alpha);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public float getRadius() {
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
