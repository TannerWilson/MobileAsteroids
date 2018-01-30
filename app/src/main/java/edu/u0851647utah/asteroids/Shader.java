package edu.u0851647utah.asteroids;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Tanner on 11/30/2016.
 */
public class Shader {

int Program;

        public Shader()
        {
            String vert =
                    "// Vertex Shader\n" +
                            "\n" +
                            "attribute vec3 position;\n" +
                            "attribute vec2 texCoord;\n" +
                            "varying vec2 TexCoordinate;\n" +
                            "uniform mat4 sprite;\n" +
                            "void main(void)\n" +
                            "{\n" +
                            "   TexCoordinate = texCoord;\n" +
                            "   gl_Position = sprite*vec4(position, 1.0);\n" +
                            "}";
            String Frag =
                    "// Fragment Shader\n" +
                            "uniform sampler2D TextureSample;\n" +
                            "varying vec2 TexCoordinate;\n" +
                            "void main(void)\n" +
                            "{\n" +
                            "   gl_FragColor = texture2D(TextureSample, TexCoordinate);\n" +
                            "}";
            createProgram(vert, Frag);
        }

    public void createProgram(String vertex, String fragment) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertex);
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragment);

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, pixelShader);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e("ERROR", "Could not link program: ");
                Log.e("ERROR", GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        Program = program;
    }

    public int loadShader(int shaderType, String source)
    {
        int shader = GLES20.glCreateShader(shaderType);
        if(shader != 0)
        {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if(compiled[0] == 0)
            {
                Log.e("SHADER ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("SHADER ERROR", GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public void UseShader(){GLES20.glUseProgram(Program);}

}
