package com.sekwah.modeleditor.modelparts;

import com.sekwah.modeleditor.assets.Assets;
import com.sekwah.modeleditor.assets.Point;
import com.sekwah.modeleditor.windows.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelRetexturedBox extends ModelBox {

	private int xTop = 0;
	private int yTop = 0;

	private int xBot = 0;
	private int yBot = 0;

	public ModelRetexturedBox(ModelBox parent, String name, int xWidth, int yWidth, int zWidth, float xOffset, float yOffset, float zOffset, int xTextureOffset, int yTextureOffset, int xTop, int yTop, int xBot, int yBot){
		super(parent, name, xWidth, yWidth, zWidth, xOffset, yOffset, zOffset, xTextureOffset, yTextureOffset);
		this.xTop = xTop;
		this.yTop = yTop;

		this.xBot = xBot;
		this.yBot = yBot;
	}

	public ModelRetexturedBox(ModelRetexturedBox parent, String name, int xWidth, int yWidth, int zWidth, float xOffset, float yOffset, float zOffset, int xTextureOffset, int yTextureOffset, float sizeScaler, int xTop, int yTop, int xBot, int yBot){
		this(parent, name, xWidth, yWidth, zWidth, xOffset, yOffset, zOffset, xTextureOffset, yTextureOffset, xTop, yTop, xBot, yBot);
		
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

        if(ModelRenderer.getSelectedBox() == this){
            boxAlpha = 1F;
        }
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(xPos, yPos, zPos);

		GL11.glRotatef(yRotation, 0, 1, 0);
		GL11.glRotatef(zRotation, 0, 0, 1);
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
		
		// lower points
		Point point5 = new Point(xOffset - sizeScaler,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point6 = new Point(xOffset + xWidth,yOffset - sizeScaler,zOffset + zWidth + sizeScaler);
		Point point7 = new Point(xOffset + xWidth,yOffset + yWidth + sizeScaler,zOffset + zWidth + sizeScaler);
		Point point8 = new Point(xOffset - sizeScaler,yOffset + yWidth + sizeScaler,zOffset + zWidth);
		
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
		GL11.glTexCoord2f((float) (xTop + 0.01) / textureWidth, (float) (yTop + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point1.xPos, point1.yPos, point1.zPos);
		GL11.glTexCoord2f((float) (xTop + 0.01) / textureWidth, (float) (yTop + 0.01) / textureHeight);
		GL11.glVertex3d(point5.xPos, point5.yPos, point5.zPos);
		GL11.glTexCoord2f((float) (xTop + xWidth - 0.01) / textureWidth, (float) (yTop + 0.01) / textureHeight);
		GL11.glVertex3d(point6.xPos, point6.yPos, point6.zPos);
		GL11.glTexCoord2f((float) (xTop + xWidth - 0.01) / textureWidth, (float) (yTop + zWidth - 0.01) / textureHeight);
		GL11.glVertex3d(point2.xPos, point2.yPos, point2.zPos);
		GL11.glEnd();
		
		GL11.glColor4f(0.87f, 0.87f, 0.87f, boxAlpha);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((float) (xBot + 0.01) / textureWidth, (float) (yBot + zWidth + 0.01) / textureHeight);
		GL11.glVertex3d(point3.xPos, point3.yPos, point3.zPos);
		GL11.glTexCoord2f((float) (xBot + 0.01) / textureWidth, (float) (yBot + 0.01) / textureHeight);
		GL11.glVertex3d(point7.xPos, point7.yPos, point7.zPos);
		GL11.glTexCoord2f((float) (xBot + xWidth - 0.01) / textureWidth, (float) (yBot + 0.01) / textureHeight);
		GL11.glVertex3d(point8.xPos, point8.yPos, point8.zPos);
		GL11.glTexCoord2f((float) (xBot + xWidth - 0.01) / textureWidth, (float) (yBot + zWidth - 0.01) / textureHeight);
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
}
