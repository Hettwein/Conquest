package de.htwg.conquest.model;

import java.util.List;

public interface IPlayer {

	String getName();
	void setName(String name);
	int getCellCount();
	List<ICell> getCells();
	void addCell(ICell cell);
}
