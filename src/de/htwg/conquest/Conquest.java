package de.htwg.conquest;

import java.util.Observable;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.view.GUI;
import de.htwg.conquest.view.TUI;

public class Conquest extends Observable{

	private static Conquest instance = null;

	private TUI tui;
	private GUI gui;
	private Injector injector;
	private IController controller;

	public static Conquest getInstance() {
	if (instance == null) {
		instance = new Conquest();
	}
		return instance;
	}

	private Conquest() {
		Injector injector = Guice.createInjector(new ConquestModule());
		controller = injector.getInstance(IController.class);
		gui = injector.getInstance(GUI.class);
		tui = injector.getInstance(TUI.class);
	}

	public static void main(String[] args) {
		Conquest game = Conquest.getInstance();
//		game.getTui().startGame();
		game.getGui().startGame();
		game.controller.newGame();
//		Scanner in = new Scanner(System.in);
//		while(game.getTui().isRunning()) {
//			game.getTui().processInput(in.nextLine());
//		}
//		in.close();
	}

    public Injector getInjector() {
        return injector;
    }

	public TUI getTui() {
		return tui;
	}

	public GUI getGui() {
		return gui;
	}
	
	public IController getController() {
		return controller;
	}
}

