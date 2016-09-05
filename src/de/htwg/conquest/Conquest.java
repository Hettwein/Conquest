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
		injector.getInstance(GUI.class);
		tui = injector.getInstance(TUI.class);
	}

	public static void main(String[] args) {
		Conquest.getInstance().controller.newGame();
		
	}

    public Injector getIn() {
        return injector;
    }

	public TUI getTui() {
		return tui;
	}
	
	public IController getController() {
		return controller;
	}
}
