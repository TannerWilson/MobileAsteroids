package edu.u0851647utah.asteroids;

/**
 * Created by Tanner on 12/16/2016.
 */
public class TestSprite {

    float centerX;
    float centerY;
    float height;
    float width;
    float rotation;

    float translateX = 0.0f;
    float translateY = 0.0f;
    float scaleX = 0.0f;
    float scaleY = 0.0f;

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        translateX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        translateY = centerY;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void draw()
    {

    }
}
