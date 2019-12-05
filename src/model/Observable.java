package model;

import java.util.ArrayList;

public interface Observable {
	public void addObserver(Observer obs);
	public void removeObserver();
	public ArrayList<Observer> getObservers();
	public void notifyObserver(String whatToNotify);
}
