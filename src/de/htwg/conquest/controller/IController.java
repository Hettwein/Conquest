package de.htwg.conquest.controller;

import java.awt.Color;
import java.util.List;

import de.htwg.conquest.model.IPlayer;

public interface IController {

	List<IPlayer> getPlayers();
	void addPlayer(IPlayer player);
	int changeColor(Color color);
	void newGame();
	void newRound();
	IPlayer getCurrentPlayer();
}
