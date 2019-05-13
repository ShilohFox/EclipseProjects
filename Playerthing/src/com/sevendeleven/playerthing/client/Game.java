package com.sevendeleven.playerthing.client;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game extends Canvas {
	
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	List<UpdateInformation> buffer = new ArrayList<>();
	
	public void run() {
		
	}
	
	public void update() {
		
		while (buffer.size() > 0) {
			if (players.containsKey(buffer.get(0).getId())) {
				Player p = players.get(buffer.get(0).getId());
				int x = buffer.get(0).getX();
				int y = buffer.get(0).getY();
				p.update(x, y);
			}
		}
		
	}
	
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//RENDER START
		
		for (Player p : players.values()) {
			p.draw(g);
		}
		
		//RENDER END
		g.dispose();
		bs.show();
	}
	
}
