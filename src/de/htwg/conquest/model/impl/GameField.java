package de.htwg.conquest.model.impl;

import java.awt.Color;

import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;

public class GameField implements IGameField {

	private ICell[][] cells;
	private int size;
	private int colorNumber;
	
	public GameField(int size) {
		this.size = size;
		cells = new Cell[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				cells[i][j] = new Cell(5, i, j);
			}
		}
	}

	@Override
	public ICell[][] getCells() {
		return cells;
	}

	@Override
	public ICell getCell(int x, int y) {
		if(x < size && y < size && x > -1 && y > -1) {
			return cells[x][y];
		}
		return null;
	}

	@Override
	public void setOwner(int x, int y, IPlayer owner) {
		cells[x][y].setOwner(owner);
	}

	@Override
	public void setCell(int x, int y, Color color) {
		cells[x][y].setColor(color);
	}

	@Override///////////////////
	public int getSize() {
		return size;
	}

	@Override//////////////////
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int getColorNumber() {
		return colorNumber;
	}

	@Override
	public void setColorNumber(int num) {
		colorNumber = num;
	}

}
