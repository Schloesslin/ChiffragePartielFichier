package main;


import controler.AbstractControler;
import controler.Controler;
import model.AbstractModel;
import model.Model;
import view.View;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractModel model = new Model();
		AbstractControler controler = new Controler(model);
		View view = new View(controler);
		model.addObserver(view);
	}
	

}
