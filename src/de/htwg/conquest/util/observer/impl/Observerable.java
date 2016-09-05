package de.htwg.conquest.util.observer.impl;

import java.util.ArrayList;
import java.util.List;

import de.htwg.conquest.util.observer.IObservable;
import de.htwg.conquest.util.observer.IObserver;

public class Observerable implements IObservable {
	
	private List<IObserver> subscriber = new ArrayList<IObserver>(2); 

	public void addObserver(IObserver s) {
		subscriber.add(s);
	}
	
	public void removeObserver(IObserver s){
		subscriber.remove(s);
	}

	public void removeAllObservers(){
		subscriber.clear();
	}

	public void notifyObservers() {		
		for (IObserver myob : subscriber) {
			IObserver observer = myob;
			observer.update();
		}
	}
}
