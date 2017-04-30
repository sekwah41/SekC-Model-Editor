package com.sekwah.modeleditor.assets;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Matrix4f;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by sekwah on 11/10/2015.
 */
public class OpenGlAssets {


    private static Matrix4f matrix = new Matrix4f();

    private static FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);

    public static BufferedImage[] Images = new BufferedImage[3];

    public static int currentTextureHeight = 0;

    public static int currentTextureWidth = 0;

    private static final int BYTES_PER_PIXEL = 4;
    public static int loadTextureAndGetID(BufferedImage image){
        glEnable(GL_TEXTURE_2D);

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

        // TODO add code to have the set texture size so if HD textures are used they work.
        currentTextureWidth = image.getWidth();

        currentTextureHeight = image.getHeight();

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:
        int textureID = glGenTextures(); //Generate texture ID
        Images[textureID - 1] = image;
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        //Return the texture ID so we can bind it later again
        return textureID;
    }

    public static void rebindTexture(int textureID) {
        BufferedImage image = Images[textureID - 1];

        // TODO add code to have the set texture size so if HD textures are used they work.
        currentTextureWidth = image.getWidth();

        currentTextureHeight = image.getHeight();

        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
    }

    public static Matrix4f getMatrix(int MatrixId){

        GL11.glGetFloat(MatrixId, matrixData);

        matrix.load(matrixData);

        matrixData.flip();
        return matrix;
    }
}
