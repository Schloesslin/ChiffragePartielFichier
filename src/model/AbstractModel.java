package model;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class AbstractModel implements Observable {

	protected String path;
	protected boolean allSelected;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();

	public abstract String readAndCryptParts(String file,  ArrayList<Integer> start, ArrayList<Integer> stop, Key key, String methode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	public abstract String readWithKey(String file, Key key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	public abstract String readWithouthKey(String file) throws IOException;
	public abstract ArrayList<Integer> getBlocks(String blocks);
	public abstract String readAndCrypt(String file, Key key, String methode) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
	public abstract String readFirstLine(String file) throws IOException;
	
	public abstract String getPath();
	public abstract void setPath(String path);
	public abstract boolean getAllSelected();
	public abstract void toggleAllSelected();
	public abstract String getFolderPath(String filePath);
	public abstract void writeFile(String name, String contenu) throws IOException;
	public abstract Key constructKey(String passwoard, String methode);
	public abstract String cryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
	public abstract String decryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
	public abstract void cryptFile(String nameFile, String contenu, String cryptKey, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException;
	public abstract void decryptFile(String nameFile, String contenu, String decryptKey, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException;

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
	public ArrayList<Observer> getObservers(){
		return this.listObserver;
	}

	@Override
	public void notifyObserver(String whatToNotify) {
		// TODO Auto-generated method stub
		String whatToUpdate = "";
		if (whatToNotify.equals("CryptClick")) {
			whatToUpdate = "AllEmpty";
		}
		for (Observer obs : listObserver) {
			obs.update(whatToUpdate);
		}
	}

}
