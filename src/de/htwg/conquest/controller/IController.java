package de.htwg.conquest.controller;

import java.awt.Color;
import java.util.List;

import de.htwg.conquest.model.ICell;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.util.observer.IObservable;

public interface IController extends IObservable {

	List<IPlayer> getPlayers();
	void addPlayer(IPlayer player);
	int changeColor(Color color);
	void newGame();
	void newRound();
	IPlayer getCurrentPlayer();
	IGameField getField();
	int getSize();
	void setSize(int size);
	IPlayer announceWinner();
	List<ICell> getNewCells();
	int getFreeCells();
	void loadLevel(String filename);
	List<Color> getColors();
	int getColorNum();
	void setColorNum(int num);
}
