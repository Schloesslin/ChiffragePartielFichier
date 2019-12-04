package controler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import model.AbstractModel;

public abstract class AbstractControler {

	protected AbstractModel model;

	public AbstractControler(AbstractModel model) {
		this.model = model;
	}

	public String readFile(String file, int start, int stop) throws IOException {
		this.control();
		return this.model.readFile(file, start, stop);
	}

	public String readAllFile(String file) throws IOException {
		this.control();
		return this.model.readAllFile(file);
	}
	
	public void setPath(String path) {
		this.model.setPath(path);
		this.control();
	}

	public String getPath() {
		this.control();
		return this.model.getPath();
	}

	public void toggleAllSelected() {
		this.model.toggleAllSelected();
	}

	public boolean getAllSelected() {
		return this.model.getAllSelected();
	}

	public String getFolderPath(String filePath) {
		this.control();
		return this.model.getFolderPath(filePath);
	}

	public void cryptFile(String contenu, String cryptKey) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		this.model.cryptFile("Crypted", contenu, cryptKey);
		this.control();
	}

	public void decryptFile(String contenu, String decryptKey) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		this.model.decryptFile("Decrypted", contenu, decryptKey);
		this.control();
	}

	// Méthode de contrôle
	abstract void control();
}
