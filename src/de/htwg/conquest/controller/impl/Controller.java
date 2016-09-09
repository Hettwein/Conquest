package de.htwg.conquest.controller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.model.impl.GameField;
import de.htwg.conquest.util.observer.impl.Observerable;

/*ideen:
 * -"landschaft", hindernisse (leere felder)
 * -gui gestaltung
 * 
 * 
*/
@Singleton
public class Controller extends Observerable implements IController {

	private List<IPlayer> players;
	private IPlayer currentPlayer;
	private IGameField field;
	private List<ICell> newCells;
	private int freeCells;
	private int turn;
	private int size;

	@Inject
	public Controller() {
		players = new ArrayList<>();
		newCells = new ArrayList<>();
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
		for (ICell cell : newCells) {
			currentPlayer.addCell(cell);
		}
		turn++;
		if (turn == players.size()) {
			turn = 0;
		}
		currentPlayer = players.get(turn);
		notifyObservers();
		return newCells.size();
	}

	private void checkColor(Color color, ICell cell) {
		if (cell == null) {
			return;
		}
		ICell newCell = field.getCell(cell.getX() + 1, cell.getY());
		if (newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			freeCells--;
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX() - 1, cell.getY());
		if (newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			freeCells--;
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX(), cell.getY() + 1);
		if (newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			freeCells--;
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
		newCell = field.getCell(cell.getX(), cell.getY() - 1);
		if (newCell != null && !newCell.isOwned() && newCell.getColor().equals(color)) {
			newCell.setOwner(currentPlayer);
			newCells.add(newCell);
			freeCells--;
			checkColor(color, field.getCell(newCell.getX(), newCell.getY()));
		}
	}

	@Override
	public void newGame() {
		turn = 0;
		currentPlayer = players.get(turn);

		for (IPlayer player : players) {
			Random r = new Random();
			conquest(player, field.getCell(r.nextInt(size), r.nextInt(size)));
		}
		notifyObservers();
	}

	private void conquest(IPlayer player, ICell cell) {
		field.setOwner(cell.getX(), cell.getY(), player);
		player.addCell(cell);
		freeCells--;
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

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
		field = new GameField(size);
		freeCells = size * size;
	}

	@Override
	public IPlayer announceWinner() {
		int max = 0;
		IPlayer winner = null;
		for (IPlayer player : players) {
			if(player.getCellCount() > max) {
				max = player.getCellCount();
				winner = player;
			}
		}
		return winner;
	}

	@Override
	public List<ICell> getNewCells() {
		return newCells;
	}

	@Override
	public int getFreeCells() {
		return freeCells;
	}
}
