package de.htwg.conquest;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.htwg.conquest.controller.IController;


public class ConquestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IController.class).to(de.htwg.conquest.controller.impl.Controller.class).in(Singleton.class);
	}
}
