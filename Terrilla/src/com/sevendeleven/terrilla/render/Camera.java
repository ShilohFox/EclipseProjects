package com.sevendeleven.terrilla.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.sevendeleven.terrilla.main.Main;

public class Camera {
	
	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	void setX(float x) {
		this.x = x;
	}
	
	void setY(float y) {
		this.y = y;
	}
	
	float getX() {
		return this.x;
	}
	
	float getY() {
		return this.y;
	}
	
	public FloatBuffer toMatrix() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		new Matrix4f().setOrtho2D(0+x, Main.getScreenWidth()+x, 0+y, Main.getScreenHeight()+y).get(buffer);
		return buffer;
	}
	
}
