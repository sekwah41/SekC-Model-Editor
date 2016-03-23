package com.sekwah.modeleditor.viewer;

/**
 * Created by sekwah on 23/3/2016.
 */
public class Camera {

    public float posX;

    public float posY;

    public float posZ;

    public float rotX;

    public float rotY;

    public Camera(float posX, float posY, float posZ){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public Camera(float posX, float posY, float posZ, float rotX, float rotY){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.rotX = rotX;
        this.rotY = rotY;
    }

}
