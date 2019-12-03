package model;

import java.util.ArrayList;

public abstract class AbstractModel implements Observable{

	
	protected String path;   
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();   
	
	
	public abstract String readFile(String file, int start, int stop);
	public abstract void setPath(String path);
	public abstract String getPath();
	  
	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		this.listObserver.add(obs);
	}
	
	@Override
	public void removeObserver() {
		// TODO Auto-generated method stub
		listObserver = new ArrayList<Observer>();
		
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		for(Observer obs : listObserver) {
			obs.update();
		}
	}
	
}
