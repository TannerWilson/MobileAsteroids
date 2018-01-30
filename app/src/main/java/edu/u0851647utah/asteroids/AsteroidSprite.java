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
import java.util.Random;

/**
 * Created by Tanner on 12/16/2016.
 */
public class AsteroidSprite extends Sprite {

    FloatBuffer TexCoordVB;
    ShortBuffer IndexVB;
    FloatBuffer mTriangleVB;
    FloatBuffer spriteBuffer;

    int textureHandle;
    int index = 1;
    float time;
    float velocityX = 0.1f;
    float velocityY = 0.1f;

    boolean isBigAsteroid;

    float[] spriteValues = new float[16];

    float xPos;
    float yPos;
    float rotation = 0;
    String type = "asteroid";


    public AsteroidSprite(Sprite parent)
    {
        parent.setScale(0.15f);

        scaleX = 0.15f;
        scaleY = 0.15f;

        velocityX = parent.velocityX;
        velocityY = parent.velocityY;

        xPos = parent.getxPos() + 0.05f;
        yPos = parent.getyPos() + 0.05f;

        rotation = parent.rotation + 2;

    }

    public AsteroidSprite(boolean bigAsteroid)
    {
        isBigAsteroid = bigAsteroid;
        if(bigAsteroid)
        {
            scaleX = 0.3f;
            scaleY = 0.3f;

            Random rand = new Random();

            while(true)
            {
                xPos = rand.nextInt(21 - 10) / 10.0f;
                yPos = rand.nextInt(21 - 10) / 10.0f;

                int neg = rand.nextInt(1);

                if(neg == 1)
                {
                    xPos = -xPos;
                    yPos = -yPos;
                }

                float shipX = Game.getInstance().ship.xPos;
                float shipY = Game.getInstance().ship.yPos;
                float shipScaleX =  Game.getInstance().ship.scaleX;
                float shipScaleY =  Game.getInstance().ship.scaleY;

                double radians = rand.nextFloat()*2*Math.PI;
                rotation = (float )Math.toDegrees(radians);


                if(!(xPos < shipX + shipScaleX &&
                    xPos + scaleX > shipScaleX &&
                    yPos < shipY + shipScaleY &&
                    xPos + scaleY > shipY))
                {
                      break;
                }

            }
        }
        else
        {
            scaleX = 0.15f;
            scaleY = 0.15f;
        }
    }

    Shader shader = null;

    @Override
    public void start()
    {
        shader = new Shader();



        // points of quad
        float tri[] = {
                0.0f, 1.0f, 0.0f, // 0
                0.0f, 0.0f, 0.0f, // 1
                1.0f, 1.0f, 0.0f, // 2
                1.0f, 0.0f, 0.0f // 3
        };

        // Constructing triangle
        short indices[] =
                {
                        0,1,2,
                        1,2,3
                };

        /* left top
         * left bottom
         * right top
         * right bottom
         * where to load texture */
        float texCoord[]=
                {
                        0.0f, 1.0f,
                        0.0f, 0.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                };

        mTriangleVB = ByteBuffer.allocateDirect(tri.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleVB.put(tri).position(0);

        TexCoordVB = ByteBuffer.allocateDirect(texCoord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        TexCoordVB.put(texCoord).position(0);

        IndexVB = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asShortBuffer();
        IndexVB.put(indices).position(0);

        Matrix.setIdentityM(spriteValues, 0);

        spriteBuffer = ByteBuffer.allocateDirect(spriteValues.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        spriteBuffer.put(spriteValues).position(0);

        textureHandle = loadTexture(R.drawable.asteroid1);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void update(double changeTime)
    {

        xPos += (float) Math.cos(Math.toRadians(rotation))*velocityX*changeTime;
        yPos += (float) Math.sin(Math.toRadians(rotation))*velocityY*changeTime;
//        xPos += velocityX*changeTime;
//        yPos += velocityY*changeTime;

        // Wrap around
        if(xPos > 1)
            xPos = -1;
        else if(xPos < -1)
            xPos = 1;

        if(yPos > 1)
            yPos = -1;
        else if(yPos < -1)
            yPos = 1;

//        // Check Collisions
//        for(int i = 0; i < Game.getInstance().sprites.size(); i ++)
//        {
//            Sprite current = Game.getInstance().sprites.get(i);
//            if(current != null)
//            {
//                if(current != this)
//                {
//                    if(     current.xPos < this.xPos + this.scaleX &&
//                            current.xPos + current.scaleX > this.xPos &&
//                            current.yPos < this.yPos + this.scaleY &&
//                            current.yPos + current.scaleY > this.yPos)
//                    {
//                        Game.getInstance().collision(current, this);
//                    }
//                }
//            }
//        }

        changeLocation(xPos, yPos, rotation, scaleX, scaleY);
    }

    @Override
    public void changeLocation(float xPos, float yPos, float rotation, float scaleX, float scaleY)
    {
        Matrix.setIdentityM(spriteValues, 0);
        Matrix.translateM(spriteValues, 0, xPos, yPos, 0);
        Matrix.scaleM(spriteValues, 0, scaleX, scaleY, 0);

        // Move sprite to origin to rotate about its center
        Matrix.translateM(spriteValues, 0, 0.5f, 0.5f, 0);
        Matrix.rotateM(spriteValues, 0, rotation, 0, 0, 1);
        Matrix.translateM(spriteValues, 0, -.5f, -.5f, 0);

        spriteBuffer.put(spriteValues).position(0);
    }

    @Override
    public void draw()
    {
        shader.UseShader();
        int textureLocation = GLES20.glGetUniformLocation(shader.Program, "TextureSample");
        int spriteLocation = GLES20.glGetUniformLocation(shader.Program, "sprite");

        // Bind Texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);

        // Bind texture and sprite locations
        GLES20.glUniform1i(textureLocation, 0);
        GLES20.glUniformMatrix4fv(spriteLocation, 1, false, spriteBuffer);

        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mTriangleVB);

        GLES20.glEnableVertexAttribArray(1);
        GLES20.glVertexAttribPointer(1, 2, GLES20.GL_FLOAT, false, 0, TexCoordVB);

        // Draw sprite
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, 6, GLES20.GL_UNSIGNED_SHORT, IndexVB);

        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
    }


    public static int loadTexture(final int resourceID)
    {
        final Context context = Game.getInstance().context;
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if(textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        }

        if(textureHandle[0] == 0)
            throw new RuntimeException("Error loading texture.");

        return textureHandle[0];
    }

    @Override
    public String getType()
    {
        return type;
    }

    public  float getScaleX() {
        return scaleX;
    }

    public  float getScaleY() {
        return scaleY;
    }

    public  float getxPos() {
        return xPos;
    }

    public  float getyPos() {
        return yPos;
    }
    public float getTime()
    {
        return time;
    }

    public void setScale(float val)
    {
        scaleX = val;
        scaleY = val;
    }

}
