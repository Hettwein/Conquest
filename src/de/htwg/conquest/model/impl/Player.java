package de.htwg.conquest.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IPlayer;

public class Player implements IPlayer {

	private String name;
	private List<ICell> cells;

	public Player(String name) {
		this.name = name;
		this.cells = new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getCellCount() {
		return cells.size();
	}

	@Override
	public void addCell(ICell cell) {
		this.cells.add(cell);
	}

	@Override
	public List<ICell> getCells() {
		return cells;
	}

}
