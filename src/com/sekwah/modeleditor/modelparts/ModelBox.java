package com.sekwah.modeleditor.modelparts;

import com.sekwah.modeleditor.assets.OpenGlAssets;
import com.sekwah.modeleditor.assets.Point;
import com.sekwah.modeleditor.windows.ModelRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ModelBox {
	
	public boolean isMirrored = false;
	
	public ModelBox parent = null;

	public int xWidth = 0;
	public int yWidth = 0;
	public int zWidth = 0;

	public float yOffset = 0F;
	public float xOffset = 0F;
	public float zOffset = 0F;

	public int xTextureOffset = 0;
	public int yTextureOffset = 0;
	
	public float xPos = 0;
	public float yPos = 0;
	public float zPos = 0;
	
	public float xRotation = 0;
	public float yRotation = 0;
	public float zRotation = 0;
	
	protected float sizeScaler = 0f;
	
	protected ArrayList<ModelBox> childBoxes = new ArrayList<ModelBox>();

	public String name = "";

	public float boxAlpha = 1.0f; // 1 is solid 0 is transparent

	public ModelBox(ModelBox parent, String name, int xWidth, int yWidth, int zWidth, float xOffset, float yOffset, float zOffset, int xTextureOffset, int yTextureOffset){
		this.parent = parent;
		
		this.name  = name;

		this.xWidth = xWidth;
		this.yWidth = yWidth;
		this.zWidth = zWidth;

		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;

		this.xTextureOffset = xTextureOffset;
		this.yTextureOffset = yTextureOffset;
	}
	
	public ModelBox(ModelBox parent, String name, int xWidth, int yWidth, int zWidth, float xOffset, float yOffset, float zOffset, int xTextureOffset, int yTextureOffset, float sizeScaler){
		this(parent, name, xWidth, yWidth, zWidth, xOffset, yOffset, zOffset, xTextureOffset, yTextureOffset);
		
		this.sizeScaler  = sizeScaler;
	}
	
	public void render(){

		//GL11.glRotatef(1, 1, 0, 0);
		
		// Tally ho, its sorted for basic rendering :D
		// Now make it make the edged darker trying to simulate minecraft
		//  after you have made proper cubes
		
		// draw quad
		
		// upper points
		
		int textureWidth = OpenGlAssets.currentTextureWidth;
		
		int textureHeight = OpenGlAssets.currentTextureHeight;

        if(ModelRenderer.getSelectedBox() == this){
            boxAlpha = 1F;
        }
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(xPos, yPos, zPos);

		GL11.glRotatef(zRotation, 0, 0, 1);
		GL11.glRotatef(yRotation, 0, 1, 0);
		GL11.glRotatef(xRotation, 1, 0, 0);

        // adds transparancy
        if(boxAlpha != 1f) {
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        }

		//GL11.glRotatef(yRotation, 0, 1, 0);
		//GL11.glRotatef(zRotation, 0, 0, 1);*/
		
		Point point1 = new Point(xOffset - sizeScaler,yOffset - sizeScaler,zOffset - sizeScaler);
		Point point2 = new Point(xOffset + xWidth + sizeScaler,yOffset - sizeScaler,zOffset - sizeScaler);
		Point point3 = new Point(xOffset + xWidth + sizeScaler,yOffset + yWidth + sizeScaler,zOffset - sizeScaler);
		Point point4 = new Point(xOffset - sizeScaler,yOffset + yWidth + sizeScaler,zOffset - sizeScaler);

		Point point5 = new Point(xOffset - sizeScaler,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point6 = new Point(xOffset + xWidth + sizeScaler,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point7 = new Point(xOffset + xWidth + sizeScaler,yOffset + yWidth + sizeScaler,zOffset + zWidth + sizeScaler);
		Point point8 = new Point(xOffset - sizeScaler,yOffset + yWidth + sizeScaler,zOffset + zWidth + sizeScaler);
		
		// GL11.glColor4f(0.95f,0.95f,0.95f, 0.3F); for alpha too
		GL11.glColor4f(0.95f, 0.95f, 0.95f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point1.xPos, point1.yPos, point1.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point2.xPos, point2.yPos, point2.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point3.xPos, point3.yPos, point3.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point4.xPos, point4.yPos, point4.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.9f, 0.9f, 0.9f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point1.xPos, point1.yPos, point1.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point5.xPos, point5.yPos, point5.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point8.xPos, point8.yPos, point8.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point4.xPos, point4.yPos, point4.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.87f, 0.87f, 0.87f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point2.xPos, point2.yPos, point2.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point6.xPos, point6.yPos, point6.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point7.xPos, point7.yPos, point7.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point3.xPos, point3.yPos, point3.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.87f, 0.87f, 0.87f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point1.xPos, point1.yPos, point1.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + 0.01) / textureWidth, (float) (yTextureOffset + 0.01) / textureHeight);
		GL11.glVertex3d(point5.xPos, point5.yPos, point5.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + 0.01) / textureHeight);
		GL11.glVertex3d(point6.xPos, point6.yPos, point6.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth - 0.01) / textureWidth, (float) (yTextureOffset + zWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point2.xPos, point2.yPos, point2.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.87f, 0.87f, 0.87f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point3.xPos, point3.yPos, point3.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth + 0.01) / textureWidth, (float) (yTextureOffset - 0.01) / textureHeight);
		GL11.glVertex3d(point7.xPos, point7.yPos, point7.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth * 2 - 0.01) / textureWidth, (float) (yTextureOffset + 0.01) / textureHeight);
		GL11.glVertex3d(point8.xPos, point8.yPos, point8.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth + xWidth * 2 - 0.01) / textureWidth, (float) (yTextureOffset + zWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point4.xPos, point4.yPos, point4.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.83f, 0.83f, 0.83f, boxAlpha);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point5.xPos, point5.yPos, point5.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth * 2 - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point6.xPos, point6.yPos, point6.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth * 2 - 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point7.xPos, point7.yPos, point7.zPos);
		GL11.glTexCoord2f((float) (xTextureOffset + zWidth * 2 + xWidth + 0.01) / textureWidth, (float) (yTextureOffset + zWidth + yWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point8.xPos, point8.yPos, point8.zPos);
		GL11.glEnd();

        if(boxAlpha != 1f){
            //GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
		
		//GL11.glTranslatef(-xPos, -yPos, -zPos);
		
		//GL11.glRotatef(-xRotation, 1, 0, 0);
		//GL11.glRotatef(yRotation, 0, -1, 0);
		//GL11.glRotatef(zRotation, 0, 0, -1);
		
		GL11.glPushMatrix();
		for(ModelBox box: childBoxes){
			box.boxAlpha = boxAlpha;
			box.render();
		}
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}
	
	public void setPos(float x, float f, float z) {
		this.xPos = x;
		this.yPos = f;
		this.zPos = z;
	}
	
	public void setRotation(float x, float y, float z) {
		this.xRotation = x;
		this.yRotation = y;
		this.zRotation = z;
	}
	
	public void setOffset(float x, float y, float z){
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}
	
	public void setTextureOffset(int x, int y){
		this.xTextureOffset = x;
		this.yTextureOffset = y;
	}

	public void addChild(ModelBox boxModel) {
		childBoxes.add(boxModel);
	}
    public ArrayList<ModelBox> getChildren(){
        return childBoxes;
    }

    public void delete() {
        if(parent != null){
            parent.childBoxes.remove(this);
        }
        else{
            ModelRenderer.boxList.remove(this);
        }
    }
}
