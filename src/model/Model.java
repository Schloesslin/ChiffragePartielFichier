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
	
	public ArrayList<Integer> getBlocks(String blocks){
		String block[] = blocks.split("[- ]");
		List<String> tmp = Arrays.asList(block);
		ArrayList<String> single = new ArrayList<String>(tmp);
		ArrayList<Integer> listBlocks = new ArrayList<Integer>();
		for (int i=0; i<single.size(); i++) {
			listBlocks.add(Integer.parseInt(single.get(i).trim()));
		}
		
		return listBlocks;
	}
	
	
	
	public String readWithKey(String file, Key key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		StringBuffer out = new StringBuffer("");
		StringBuffer toDecrypt = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String("");
		Boolean isCryptedPart = false;
		while ((line = reader.readLine()) != null) {
			if (line.equals("Crypted Part")) {

				isCryptedPart = true;
			}
			else if (line.equals("End Crypted Part")) {
				out.append(this.decryptText(toDecrypt.toString().trim(), key));
				isCryptedPart = false;
				toDecrypt = new StringBuffer("");
			}
			else {
				if (isCryptedPart) {
					toDecrypt.append(line+"\n");
				}
				else {
					out.append(line+"\n");
				}
			}
			
			
			
			
		}
		reader.close();
		return out.toString();
	}
	
	
	
	public String readWithouthKey(String file) throws IOException {
		StringBuffer out = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String("");
		Boolean isCryptedPart = false;
		while ((line = reader.readLine()) != null) {
			if (line.equals("Crypted Part")) {
				isCryptedPart = true;
			}
			else if (line.equals("End Crypted Part")) {
				isCryptedPart = false;
			}
			else {
				if (!isCryptedPart) {
					out.append(line+"\n");
				}
				
			}
			
			
			
			
		}
		reader.close();
		return out.toString();
	}
	
	public String writeTemp(String file, ArrayList<Integer> start, ArrayList<Integer> stop, Key key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		StringBuffer out = new StringBuffer("");
		StringBuffer toCrypt = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String("");
		boolean cryptPart = false;
		int countLine = 1;
		while ((line = reader.readLine()) != null) {
			if (start.contains(countLine)) {
				out.append("Crypted Part\n");
				toCrypt.append(line+"\n");
				cryptPart = true;
			}
			else if (stop.contains(countLine)) {
				toCrypt.append(line+"\n");
				out.append(this.cryptText(toCrypt.toString(), key)+"\n");
				out.append("End Crypted Part\n");
				toCrypt = new StringBuffer("");
				cryptPart=false;
			}
			else if (cryptPart) {
				toCrypt.append(line+"\n");
			}
			else {
				out.append(line+"\n");
			}
			countLine++;

		}
		reader.close();
		
		return out.toString();
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
