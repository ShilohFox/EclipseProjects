package com.sevendeleven.testproject.item;

import java.awt.image.BufferedImage;

import org.json.JSONObject;

import com.sevendeleven.testproject.entities.Entity;
import com.sevendeleven.testproject.main.ResourceLocator;
import com.sevendeleven.testproject.world.BlockPos;

public class Item {
	
	protected String name;
	protected BufferedImage image;
	protected long uniqueNumber;
	protected int maxStackSize;
	
	public Item(JSONObject obj) {
		if (!obj.has("name")) {
			System.err.println("Item does not have name!");
			System.exit(-1);
		}
		if (!obj.has("uniqueNumber")) {
			System.err.println("Item " + obj.getString("name") + " does not have a unique number!");
			System.exit(-1);
		}
		if (!obj.has("texture")) {
			System.err.println("Item " + obj.getString("name") + " does not have a texture!");
			System.exit(-1);
		}
		if (!obj.has("maxStackSize")) {
			System.err.println("Item " + obj.getString("name") + " does not have a max stack size!");
			System.exit(-1);
		}
		this.name = obj.getString("name");
		this.image = ResourceLocator.getImage(obj.getString("texture"));
		this.uniqueNumber = obj.getNumber("uniqueNumber").longValue();
		this.maxStackSize = obj.getNumber("maxStackSize").intValue();
	}
	
	protected Item(String name, BufferedImage img, long uniqueNumber, int maxStackSize) {
		this.name = name;
		this.image = img;
		this.uniqueNumber = uniqueNumber;
		this.maxStackSize = maxStackSize;
	}
	
	protected Item() {
		
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public void onUse(ItemStack stack, BlockPos blockPos, Entity ent) {
		
	}
	
	public void onClick(ItemStack stack, BlockPos blockPos, Entity ent) {
		
	}
	
	public void onDestroyBlock(ItemStack stack, BlockPos blockPos, Entity ent) {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getUniqueNumber() {
		return this.uniqueNumber;
	}
	
	public int getMaxStackSize() {
		return maxStackSize;
	}
	
}
