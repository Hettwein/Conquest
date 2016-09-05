package de.htwg.conquest.controller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.model.impl.GameField;
import de.htwg.conquest.model.impl.Player;
import de.htwg.conquest.util.observer.impl.Observerable;

@Singleton
public class Controller extends Observerable implements IController {

	private List<IPlayer> players;
	private IPlayer currentPlayer;
	private IGameField field;
	private List<ICell> newCells;
	private int turn;

	@Inject
	public Controller() {
		players = new ArrayList<>();
		newCells = new ArrayList<>();
		addPlayer(new Player("test"));
		addPlayer(new Player("test2"));
	}

	@Override
	public List<IPlayer> getPlayers() {
		return players;
	}

	@Override
	public void addPlayer(IPlayer player) {
		players.add(player);
	}

	@Override
	public int changeColor(Color color) {
		newCells.clear();
		List<ICell> cells = currentPlayer.getCells();
		for (ICell cell : cells) {
			checkColor(color, field.getCell(cell.getX(), cell.getY()));
			cell.setColor(color);
		}
		for(ICell cell : newCells) {
			currentPlayer.addCell(cell);
		}
		turn++;
		if(turn == players.size()) {
			turn = 0;
		}
		currentPlayer = players.get(turn);
		notifyObservers();
		return newCells.size();
	}
	
	private void checkColor(Color color, ICell cell) {
		if(cell == null) {
			return;
		}
		ICell newCell = field.getCell(cell.getX() + 1, cell.getY());
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX() - 1, cell.getY());
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX(), cell.getY() + 1);
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX(), cell.getY() - 1);
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
	}

	@Override
	public void newGame() {
		turn = 0;
		currentPlayer = players.get(turn);
		field = new GameField(10);
		field.setOwner(0, 0, players.get(0));
		players.get(0).addCell(field.getCell(0, 0));
		field.setOwner(9, 9, players.get(1));
		players.get(1).addCell(field.getCell(9, 9));
		
		notifyObservers();
		
//		System.out.println(players.get(0).getCellCount());
//		System.out.println(players.get(1).getCellCount());
//		
//		System.out.println(changeColor(Color.ORANGE) + " new cells");
//		
//		System.out.println(players.get(0).getCellCount());
//		System.out.println(players.get(1).getCellCount());
//		
//		System.out.println(changeColor(Color.BLUE) + " new cells");
//		
//		System.out.println(players.get(0).getCellCount());
//		System.out.println(players.get(1).getCellCount());
	}

	@Override
	public void newRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public IGameField getField() {
		return field;
	}
}
