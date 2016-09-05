package de.htwg.conquest.model;

import java.awt.Color;

public interface ICell {

	Color getColor();
	void setColor(Color color);
	IPlayer getOwner();
	void setOwner(IPlayer owner);
	boolean isOwned();
	int getX();
	int getY();
}
