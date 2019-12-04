package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Model extends AbstractModel {

	public Model() {
		this.allSelected = false;
		File file = new File("");
		this.path = file.getAbsolutePath();

	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub
		this.path = path;
		this.notifyObserver("");

	}
	
	@Override
	public boolean getAllSelected() {
		// TODO Auto-generated method stub
		return this.allSelected;
	}
	
	@Override
	public void toggleAllSelected() {
		// TODO Auto-generated method stub
		this.allSelected = !this.allSelected;
		this.notifyObserver("");
	}

	@Override
	public String getFolderPath(String filePath) {
		String path[] = filePath.split("/");
		List<String> tmp = Arrays.asList(path);
		ArrayList<String> folders = new ArrayList<String>(tmp);
		String folderPath = new String();
		for (int i = 0; i < folders.size() - 1; i++) {
			folderPath += folders.get(i) + "/";
		}
		return folderPath;
	}
	
	@Override
	public String readAllFile(String file) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(this.getFolderPath(this.getPath()) + file + ".txt"));
		String ligne = new String("");
		StringBuffer fichier = new StringBuffer();
		while ((ligne = reader.readLine()) != null) {
			fichier.append(ligne);
			fichier.append("\n");
		}
		reader.close();
		return fichier.toString().trim();
	}
	
	@Override
	public String readFile(String file, int start, int stop) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(this.getFolderPath(this.getPath()) + file + ".txt"));
		String ligne;
		StringBuffer fichier = new StringBuffer();
		int countLine = 1;
		while ((ligne = reader.readLine()) != null) {
			if (countLine <= stop && countLine >= start) {
				fichier.append(ligne);
				fichier.append("\n");
			}
			countLine++;

		}
		reader.close();
		return fichier.toString().trim();
	}

	@Override
	public void writeFile(String name, String contenu) throws IOException {

		FileWriter writer = new FileWriter(this.getFolderPath(this.getPath()) + name + ".txt");
		writer.write(contenu.trim());
		writer.close();
	}
	
	@Override
	public Key constructKey(String passwoard, String methode) {
		byte[] byteKey = new byte[32];
		for (int i = 0; i < 32; i++) {
			if (i < passwoard.getBytes().length) {
				byteKey[i] = passwoard.getBytes()[i];
			} else {
				byteKey[i] = 0;
			}
		}
		Key key = new SecretKeySpec(byteKey, methode);

		return key;
	}

	@Override
	public String cryptText(String contenu, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenu.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);

		return textEncryptedBase64;
	}

	@Override
	public String decryptText(String contenu, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] text = Base64.getDecoder().decode(contenu.trim());
		byte[] byteDecrypted = cipher.doFinal(text);
		String textDecrypted = new String(byteDecrypted);
		return textDecrypted;
	}

	@Override
	public void cryptFile(String nameFile, String contenu, String cryptKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		// TODO Auto-generated method stub
		Key key = this.constructKey(cryptKey, "AES");
		String textCrypted = this.cryptText(contenu, key);
		this.writeFile(nameFile, textCrypted.trim());
		this.notifyObserver("CryptClick");
	}

	@Override
	public void decryptFile(String nameFile, String contenu, String decryptKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		// TODO Auto-generated method stub
		Key key = this.constructKey(decryptKey, "AES");
		String textDecrypted = this.decryptText(contenu, key);
		this.writeFile(nameFile, textDecrypted.trim());
		this.notifyObserver("CryptClick");
	}

}
