package controler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import model.AbstractModel;

public abstract class AbstractControler {

	protected AbstractModel model;
	
	public AbstractControler(AbstractModel model) {
		this.model = model;
	}

	public String getPath() {
		this.control();
		return this.model.getPath();
	}
	
	public void setPath(String path) {
		this.model.setPath(path);
		this.control();
	}

	public boolean getAllSelected() {
		return this.model.getAllSelected();
	}
	
	public void toggleAllSelected() {
		this.model.toggleAllSelected();
	}

	public String getFolderPath(String filePath) {
		this.control();
		return this.model.getFolderPath(filePath);
	}
	
	public void writeFile(String name, String contenu) throws IOException{
		this.control();
		this.model.writeFile(name, contenu);
	}

	public Key constructKey(String passwoard, String methode) {
		this.control();
		return this.model.constructKey(passwoard, methode);
	}
	
	public String cryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		this.control();
		return this.model.cryptText(contenu, key, methode);
	}

	public String decryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		this.control();
		return this.model.decryptText(contenu, key, methode);
	}
	
	public ArrayList<Integer> getBlocks(String blocks){
		this.control();
		return this.model.getBlocks(blocks);
	}
	
	public String readAndCrypt(String file, Key key, String methode) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		this.control();
		return this.model.readAndCrypt(file, key, methode);
	}
	
	public String readAndCryptParts(String file, ArrayList<Integer> start, ArrayList<Integer> stop, Key key, String methode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		this.control();
		return this.model.readAndCryptParts(file, start, stop, key, methode);
	}
	
	public String readFirstLine(String file) throws IOException {
		this.control();
		return this.model.readFirstLine(file);
	}
	
	public String readWithKey(String file, Key key ) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		this.control();
		return this.model.readWithKey(file, key);
	}

	public String readWithouthKey(String file) throws IOException {
		this.control();
		return this.model.readWithouthKey(file);
	}

	// Méthode de contrôle
	abstract void control();
}
