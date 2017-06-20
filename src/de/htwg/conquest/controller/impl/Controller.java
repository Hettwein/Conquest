package de.htwg.conquest.controller.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.model.impl.GameField;
import de.htwg.conquest.model.impl.Player;
import de.htwg.conquest.util.ColorUtil;
import de.htwg.conquest.util.observer.impl.Observerable;

/*ideen:
 * -"landschaft", hindernisse (leere felder) #
 * -gui gestaltung
 * -bessere start verteilung (+bug? #)
 * -evtl feste startpositionen?
 * -counter für single player
 * -erkennen wenn jmd nicht mehr kann #
 * -andere spielmodi ?
 * -items ?
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
	private int numberOfColors = 5;

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
		changePlayer();
		while(checkStalemate(currentPlayer) && freeCells > 0) {
			changePlayer();
		}
		notifyObservers();
		return newCells.size();
	}

	private void changePlayer() {
		turn++;
		if (turn == players.size()) {
			turn = 0;
		}
		currentPlayer = players.get(turn);
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

	private boolean checkStalemate(IPlayer player) {
		for(ICell cell : player.getCells()) {
			int x = cell.getX();
			int y = cell.getY();
			ICell c = field.getCell(x + 1, y);
			if(c != null && !c.isOwned()) {
				return false;
			}
			c = field.getCell(x - 1, y);
			if(c != null && !c.isOwned()) {
				return false;
			}
			c = field.getCell(x, y - 1);
			if(c != null && !c.isOwned()) {
				return false;
			}
			c = field.getCell(x, y + 1);
			if(c != null && !c.isOwned()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void newGame() {
		turn = 0;
		currentPlayer = players.get(turn);

		for (IPlayer player : players) {
			Random r = new Random();
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			while(field.getCell(x, y).isOwned()) {
				x = r.nextInt(size);
				y = r.nextInt(size);
			}
			conquest(player, field.getCell(x, y));
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
		setSize(size);
		for (IPlayer p : players) {
			p.getCells().clear();
		}
		newGame();
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
		if(size == 25) {
			freeCells = size * size;
			loadLevel("level1.png");
		} else if(size == 30) {
				freeCells = size * size;
				loadLevel("level2.png");
		} else if(size == 40) {
				freeCells = size * size;
				loadLevel("level3.png");
		} else {
			this.size = size;
			field = new GameField(size, numberOfColors);
			freeCells = size * size;
		}
	}

	@Override
	public IPlayer announceWinner() {
		int max = 0;
		IPlayer winner = null;
		for (IPlayer player : players) {
			if (player.getCellCount() > max) {
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

	@Override
	public void loadLevel(String filename) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File("./resources/" + filename));
			size = image.getWidth();
			field = new GameField(size, numberOfColors);

			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					int color = image.getRGB(x, y);
					if (color == Color.BLACK.getRGB()) {
						field.getCell(x, y).setOwner(new Player(" "));
						field.getCell(x, y).setColor(Color.BLACK);
						freeCells--;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error while reading file " + filename);
			e.printStackTrace();
		}
	}

	@Override
	public List<Color> getColors() {
		return ColorUtil.getColorSet(numberOfColors);
	}

	@Override
	public int getColorNum() {
		return numberOfColors;
	}

	@Override
	public void setColorNum(int num) {
		this.numberOfColors = num;
		field = new GameField(size, numberOfColors);
	}
}
