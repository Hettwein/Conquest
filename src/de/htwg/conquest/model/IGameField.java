package de.htwg.conquest.model;

import java.awt.Color;

public interface IGameField {

	ICell[][] getCells();
	ICell getCell(int x, int y);
	void setOwner(int x, int y, IPlayer owner);
	void setCell(int x, int y, Color color);
	int getSize();
	void setSize(int size);
}
