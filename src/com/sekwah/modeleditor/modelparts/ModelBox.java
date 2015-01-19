package com.sekwah.modeleditor.modelparts;

import com.sekwah.modeleditor.assets.Assets;
import com.sekwah.modeleditor.assets.Point;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ModelBox {
	
	public boolean isMirrored = false;
	
	public ModelBox parent = null;
	
	private int xWidth = 0;
	private int yWidth = 0;
	private int zWidth = 0;
	
	private float yOffset = 0F;
	private float xOffset = 0F;
	private float zOffset = 0F;
	
	private int xTextureOffset = 0;
	private int yTextureOffset = 0;
	
	public float xPos = 0;
	public float yPos = 0;
	public float zPos = 0;
	
	public float xRotation = 0;
	public float yRotation = 0;
	public float zRotation = 0;
	
	private float sizeScaler = 0;
	
	private ArrayList<ModelBox> childBoxes = new ArrayList<ModelBox>();

	public String name = "";

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
		
		int textureWidth = Assets.currentTextureWidth;
		
		int textureHeight = Assets.currentTextureHeight;
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(xPos, yPos, zPos);
		
		GL11.glRotatef(xRotation, 1, 0, 0);
		GL11.glRotatef(yRotation, 0, 1, 0);
		GL11.glRotatef(zRotation, 0, 0, 1);
		//GL11.glRotatef(yRotation, 0, 1, 0);
		//GL11.glRotatef(zRotation, 0, 0, 1);*/
		
		Point point1 = new Point(xOffset - sizeScaler,yOffset - sizeScaler,zOffset - sizeScaler);
		Point point2 = new Point(xOffset + xWidth + sizeScaler,yOffset - sizeScaler,zOffset - sizeScaler);
		Point point3 = new Point(xOffset + xWidth + sizeScaler,yOffset + yWidth + sizeScaler,zOffset - sizeScaler);
		Point point4 = new Point(xOffset - sizeScaler,yOffset + yWidth + sizeScaler,zOffset - sizeScaler);
		
		// lower points
		Point point5 = new Point(xOffset - sizeScaler,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point6 = new Point(xOffset + xWidth,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point7 = new Point(xOffset + xWidth,yOffset + yWidth + sizeScaler,zOffset + zWidth + sizeScaler);
		Point point8 = new Point(xOffset - sizeScaler,yOffset + yWidth + sizeScaler,zOffset + zWidth);
		
		// GL11.glColor4f(0.95f,0.95f,0.95f, 0.3F); for alpha too
		GL11.glColor3f(0.95f,0.95f,0.95f);
		
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
		
		GL11.glColor3f(0.9f,0.9f,0.9f);
		
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
		
		GL11.glColor3f(0.87f,0.87f,0.87f);
		
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
		
		GL11.glColor3f(0.87f,0.87f,0.87f);
		
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
		
		GL11.glColor3f(0.87f,0.87f,0.87f);
		
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
		
		GL11.glColor3f(0.83f,0.83f,0.83f);
		
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
		
		//GL11.glTranslatef(-xPos, -yPos, -zPos);
		
		//GL11.glRotatef(-xRotation, 1, 0, 0);
		//GL11.glRotatef(yRotation, 0, -1, 0);
		//GL11.glRotatef(zRotation, 0, 0, -1);
		
		GL11.glPushMatrix();
		for(ModelBox box: childBoxes){
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
	
}
