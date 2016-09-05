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

@Singleton
public class Controller implements IController {

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
			System.out.println(cells);
			checkColor(color, field.getCell(cell.getX() + 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX() - 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX(), cell.getY() + 1));
			checkColor(color, field.getCell(cell.getX(), cell.getY() - 1));
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
			checkColor(color, field.getCell(cell.getX() + 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX(), cell.getY() + 1));
			checkColor(color, field.getCell(cell.getX(), cell.getY() - 1));
		}
		newCell = field.getCell(cell.getX() - 1, cell.getY());
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(cell.getX() - 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX(), cell.getY() + 1));
			checkColor(color, field.getCell(cell.getX(), cell.getY() - 1));
		}
		newCell = field.getCell(cell.getX(), cell.getY() + 1);
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(cell.getX() + 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX() - 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX(), cell.getY() + 1));
		}
		newCell = field.getCell(cell.getX(), cell.getY() - 1);
		if(newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			checkColor(color, field.getCell(cell.getX() + 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX() - 1, cell.getY()));
			checkColor(color, field.getCell(cell.getX(), cell.getY() - 1));
		}
	}

	@Override
	public void newGame() {
		turn = 0;
		currentPlayer = players.get(turn);
		field = new GameField(10);
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				Color c = field.getCells()[i][j].getColor();
				String co = "";
				if(c.equals(Color.BLUE)) {
					co = "blue";
				} else if(c.equals(Color.GREEN)) {
					co = "green";
				} else if(c.equals(Color.RED)) {
					co = "red";
				} else if(c.equals(Color.YELLOW)) {
					co = "yellow";
				} else if(c.equals(Color.ORANGE)) {
					co = "orange";
				}
				System.out.println(co);
			}
		}
		field.setOwner(0, 0, players.get(0));
		players.get(0).addCell(field.getCell(0, 0));
		field.setOwner(9, 9, players.get(1));
		players.get(1).addCell(field.getCell(9, 9));
		
		System.out.println(players.get(0).getCellCount());
		System.out.println(players.get(1).getCellCount());
		
		System.out.println(changeColor(Color.ORANGE) + " new cells");
		
		System.out.println(players.get(0).getCellCount());
		System.out.println(players.get(1).getCellCount());
		
		System.out.println(changeColor(Color.BLUE) + " new cells");
		
		System.out.println(players.get(0).getCellCount());
		System.out.println(players.get(1).getCellCount());
	}

	@Override
	public void newRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}
}
