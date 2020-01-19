package model;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public abstract class AbstractModel implements Observable {

	private static String READ_CLICK = new String("ReadClick");
	private static String CRYPT_CLICK = new String("CryptClick");
	private static String ALL_READ_EMPTY = new String("AllReadEmpty");
	private static String ALL_CRYPT_EMPTY = new String("AllCryptEmpty");
	
	protected String path;
	protected boolean allSelected;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();

	
	public abstract String getPath();
	public abstract void setPath(String path);
	public abstract boolean getAllSelected();
	public abstract void toggleAllSelected();
	public abstract String getFolderPath(String filePath);
	public abstract void writeFile(String name, String contenu) throws IOException;
	public abstract SecretKeySpec constructKey(String passwoard, String methode) throws NoSuchAlgorithmException, InvalidKeySpecException;
	public abstract String cryptText(String contenu, SecretKeySpec key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
	public abstract String decryptText(String contenu, SecretKeySpec key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
	public abstract ArrayList<Integer> getBlocks(String blocks);
	public abstract String readAndCrypt(String file, SecretKeySpec key, String methode) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
	public abstract String readAndCryptParts(String file,  ArrayList<Integer> start, ArrayList<Integer> stop, SecretKeySpec key, String methode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	public abstract String readFirstLine(String file) throws IOException;
	public abstract String readWithKey(String file, SecretKeySpec key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
	public abstract String readWithouthKey(String file) throws IOException;
	public abstract int countLine(String file) throws IOException;

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
		if (whatToNotify.equals(CRYPT_CLICK)) {
			whatToUpdate = ALL_CRYPT_EMPTY;
		}
		else if (whatToNotify.equals(READ_CLICK)) {
			whatToUpdate = ALL_READ_EMPTY;
		}
		for (Observer obs : listObserver) {
			obs.update(whatToUpdate);
		}
	}

}
