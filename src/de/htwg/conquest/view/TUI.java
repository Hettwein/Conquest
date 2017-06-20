package de.htwg.conquest.view;

import java.awt.Color;
import java.util.Scanner;

import com.google.inject.Inject;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.IGameField;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.model.impl.Player;
import de.htwg.conquest.util.observer.IObserver;

public class TUI implements IObserver {

	private IController controller;
	private IGameField field;
	private boolean running;

	@Inject
	public TUI(IController controller) {
		this.controller = controller;
		this.controller.addObserver(this);
		this.running = true;
	}

	public void startGame() {
		System.out.println("Welcome to Conquest!");
		System.out.println("Please enter your name:");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		while(true) {
			String input = in.nextLine();
			if(input.equals("start")) {
				break;
			}
			controller.addPlayer(new Player(input));
			System.out.println("Enter another name for an additional player or enter 'start' to begin.");
		}
		System.out.println("Now enter a size for the game field:");
		controller.setSize(in.nextInt());
//		in.close();
		System.out.println("Okay, let's go!\n");
	}
	
	public void processInput(String input) {///////////////////
		switch(input) {
			case "red":
				System.out.println(controller.changeColor(Color.RED) + " new cells");
				break;
			case "blue":
				System.out.println(controller.changeColor(Color.BLUE) + " new cells");
				break;
			case "green":
				System.out.println(controller.changeColor(Color.GREEN) + " new cells");
				break;
			case "yellow":
				System.out.println(controller.changeColor(Color.YELLOW) + " new cells");
				break;
			case "orange":
				System.out.println(controller.changeColor(Color.ORANGE) + " new cells");
				break;
			case "quit":
				running = false;
				break;
			default:
				System.out.println("unknown command");
				break;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public String status() {
		StringBuilder s = new StringBuilder();
		for(IPlayer player : controller.getPlayers()) {
			s.append(player.getName() + ": " + player.getCellCount() + "\n");
		}
		s.append("\nFree cells: " + controller.getFreeCells() + "\n");
		if(controller.getFreeCells() == 0) {//in den controller
			System.out.println("Congratulations " + controller.announceWinner().getName() + ", you have won!\n");
		}
		return s.toString();
	}

	public String drawField() {
		StringBuilder s = new StringBuilder();
		s.append(status());
		s.append("\ncurrent: " + controller.getCurrentPlayer().getName() + "\n");
		for (int i = 0; i < controller.getSize(); i++) {
			for (int j = 0; j < controller.getSize(); j++) {
				if(field.getCell(i, j).isOwned()) {
					s.append(field.getCell(i, j).getColorText().toUpperCase().charAt(0) + " ");
				} else {
					s.append(field.getCell(i, j).getColorText().charAt(0) + " ");
				}
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public void update() {
		field = controller.getField();
		System.out.println(drawField());
	}
}
