package de.htwg.conquest.view;

import java.awt.Color;

import com.google.inject.Inject;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.IGameField;
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

	public void processInput(String input) {
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

	public String drawField() {
		StringBuilder s = new StringBuilder();
		s.append("\ncurrent: " + controller.getCurrentPlayer().getName() + "\n");
		for (int i = 0; i < field.getSize(); i++) {
			for (int j = 0; j < field.getSize(); j++) {
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
