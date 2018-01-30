package edu.u0851647utah.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Tanner on 12/16/2016.
 */
public abstract class  Sprite {



    FloatBuffer spriteBuffer;


    float time;


    float scaleX = 0.0f;
    float scaleY = 0.0f;
    float velocityX = 0.1f;
    float velocityY = 0.1f;
    float rotation;

    float[] spriteValues = new float[16];
    String type = "sprite";

    Shader shader = null;

    public abstract float getScaleX();

    public abstract float getScaleY();

    public abstract float getxPos();

    public abstract float getyPos();
    public abstract float getTime();
    public abstract void setScale(float val);


    public void start()
    {

    }

    public void update(double changeTime)
    {

    }

    public String getType()
    {
        return type;
    }

    public void changeLocation(float xPos, float yPos, float rotation, float scaleX, float scaleY)
    {

    }

    public void draw()
    {

    }

//    public float getCenterX() {
//        return centerX;
//    }
//
//    public void setCenterX(float centerX) {
//        translateX = centerX;
//    }
//
//    public float getCenterY() {
//        return centerY;
//    }
//
//    public void setCenterY(float centerY) {
//        translateY = centerY;
//    }
//
//    public float getHeight() {
//        return height;
//    }
//
//    public void setHeight(float height) {
//        this.height = height;
//    }
//
//    public float getWidth() {
//        return width;
//    }
//
//    public void setWidth(float width) {
//        scaleX = width * 0.5f;
//    }
//
//    public float getRotation() {
//        return rotation;
//    }
//
//    public void setRotation(float rotation) {
//        this.rotation = rotation;
//    }
}
