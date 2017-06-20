package de.htwg.conquest.model.impl;

import java.awt.Color;
import java.util.Random;

import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.util.ColorUtil;

public class Cell implements ICell {

	private Color color;
	private IPlayer owner;
	private int x;
	private int y;
	
	public Cell(int num, int x, int y) {
		Random r = new Random();
		this.color = ColorUtil.getColorSet(num).get(r.nextInt(num));
		this.x = x;
		this.y = y;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public IPlayer getOwner() {
		return owner;
	}

	@Override
	public void setOwner(IPlayer owner) {
		this.owner = owner;
	}

	@Override
	public boolean isOwned() {
		return owner != null;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public String getColorText() {
		return ColorUtil.getColorText(color);
	}

}
