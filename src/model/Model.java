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
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Model extends AbstractModel {

	private static String CRYPT_PART = new String("Crypted Part");
	private static String END_CRYPT_PART = new String("End Crypted Part");
	private static String JUMP_LINE = new String("\n");
	private static String EMPTY = new String("");
	private static String SPACE = new String(" ");
	private static String EXTEND_TXT = new String(".txt");
	private static String SLASH = new String("/");
	private static String AES = new String("AES");
	private static String DES = new String("DES");
	private static String ENCRYPTION = new String("encryption");
	private static String SEPARATOR = new String("[- ]");
	private static String READ_CLICK = new String("ReadClick");
	private static String CRYPT_CLICK = new String("CryptClick");
	
	public Model() {
		this.allSelected = false;
		File file = new File(EMPTY);
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
		this.notifyObserver(EMPTY);

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
		this.notifyObserver(EMPTY);
	}

	@Override
	public String getFolderPath(String filePath) {
		String path[] = filePath.split(SLASH);
		List<String> tmp = Arrays.asList(path);
		ArrayList<String> folders = new ArrayList<String>(tmp);
		String folderPath = new String();
		for (int i = 0; i < folders.size() - 1; i++) {
			folderPath += folders.get(i) + SLASH;
		}
		this.notifyObserver(EMPTY);

		return folderPath;
	}
	
	
	
	
	
	
	
	

	@Override
	public void writeFile(String name, String contenu) throws IOException {

		FileWriter writer = new FileWriter(this.getFolderPath(this.getPath()) + name + EXTEND_TXT);
		writer.write(contenu.trim());
		writer.close();
		this.notifyObserver(CRYPT_CLICK);

	}
	
	
	
	@Override
	public Key constructKey(String passwoard, String methode) {
		byte[] byteKey;
		if (methode.contains(AES)) {

			byteKey = new byte[32];
			for (int i = 0; i < 32; i++) {
				if (i < passwoard.getBytes().length) {
					byteKey[i] = passwoard.getBytes()[i];
				} else {
					byteKey[i] = 0;
				}
			}
		}
		else {
			byteKey = new byte[DESKeySpec.DES_KEY_LEN];
			for (int i = 0; i < DESKeySpec.DES_KEY_LEN; i++) {
				if (i < passwoard.getBytes().length) {
					byteKey[i] = passwoard.getBytes()[i];
				} else {
					byteKey[i] = 0;
				}
			}
		}
		
		Key key = new SecretKeySpec(byteKey, methode);
		this.notifyObserver(EMPTY);
		return key;
	}

	@Override
	public String cryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(methode);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenu.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);

		this.notifyObserver(EMPTY);

		return textEncryptedBase64;
	}

	@Override
	public String decryptText(String contenu, Key key, String methode) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(methode);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] text = Base64.getDecoder().decode(contenu.trim());
		byte[] byteDecrypted = cipher.doFinal(text);
		String textDecrypted = new String(byteDecrypted);
		this.notifyObserver(EMPTY);

		return textDecrypted;
		

	}
	
	@Override
	public ArrayList<Integer> getBlocks(String blocks){
		String block[] = blocks.split(SEPARATOR);
		List<String> tmp = Arrays.asList(block);
		ArrayList<String> single = new ArrayList<String>(tmp);
		ArrayList<Integer> listBlocks = new ArrayList<Integer>();
		for (int i=0; i<single.size(); i++) {
			listBlocks.add(Integer.parseInt(single.get(i).trim()));
		}
		this.notifyObserver(EMPTY);

		return listBlocks;
	}
	
	@Override
	public String readAndCrypt(String file, Key key, String methode) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		StringBuffer out = new StringBuffer(EMPTY);
		StringBuffer toCrypt = new StringBuffer(EMPTY);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String(EMPTY);
		out.append(methode + SPACE + ENCRYPTION + JUMP_LINE);

		while ((line = reader.readLine()) != null) {
			toCrypt.append(line + JUMP_LINE);
		}
		reader.close();
		out.append(CRYPT_PART + JUMP_LINE);
		out.append(this.cryptText(toCrypt.toString(), key, methode).trim());
		out.append(JUMP_LINE + END_CRYPT_PART + JUMP_LINE);
		this.notifyObserver(EMPTY);

		return out.toString();
	}
	
	@Override
	public String readAndCryptParts(String file, ArrayList<Integer> start, ArrayList<Integer> stop, Key key, String methode) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		StringBuffer out = new StringBuffer(EMPTY);
		StringBuffer toCrypt = new StringBuffer(EMPTY);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String(EMPTY);
		boolean cryptPart = false;
		int countLine = 1;
		out.append(methode + SPACE + ENCRYPTION + JUMP_LINE);
		while ((line = reader.readLine()) != null) {
			if (start.contains(countLine)) {
				out.append(CRYPT_PART + JUMP_LINE);
				toCrypt.append(line + JUMP_LINE);
				cryptPart = true;
			}
			else if (stop.contains(countLine)) {
				toCrypt.append(line + JUMP_LINE);
				out.append(this.cryptText(toCrypt.toString(), key, methode) + JUMP_LINE);
				out.append(END_CRYPT_PART + JUMP_LINE);
				toCrypt = new StringBuffer(EMPTY);
				cryptPart=false;
			}
			else if (cryptPart) {
				toCrypt.append(line + JUMP_LINE);
			}
			else {
				out.append(line + JUMP_LINE);
			}
			countLine++;

		}
		reader.close();
		this.notifyObserver(EMPTY);

		return out.toString();
	}
	
	@Override
	public String readFirstLine(String file) throws IOException {
		StringBuffer out = new StringBuffer(EMPTY);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String(EMPTY);
		if ((line = reader.readLine()) != null && !line.equals("")) {
			out.append(line);
		}
		reader.close();
		return out.toString();
	}
	
	@Override
	public String readWithKey(String file, Key key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		StringBuffer out = new StringBuffer(EMPTY);
		StringBuffer toDecrypt = new StringBuffer(EMPTY);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String(EMPTY);
		Boolean isCryptedPart = false;
		String methode = new String(EMPTY);
		
		while ((line = reader.readLine()) != null) {
			if (line.equals(CRYPT_PART)) {

				isCryptedPart = true;
			}
			else if (line.equals(END_CRYPT_PART)) {
				out.append(this.decryptText(toDecrypt.toString().trim(), key, methode));
				isCryptedPart = false;
				toDecrypt = new StringBuffer(EMPTY);
			}
			else if (line.contains(DES)) {
				methode = DES;
			}
			else if (line.contains(AES)) {
				methode = AES;
			}
			else {
				if (isCryptedPart) {
					toDecrypt.append(line + JUMP_LINE);
				}
				else {
					out.append(line + JUMP_LINE);
				}
			}
		}
		reader.close();
		this.notifyObserver(READ_CLICK);

		return out.toString().trim();
	}
	
	@Override
	public String readWithouthKey(String file) throws IOException {
		StringBuffer out = new StringBuffer(EMPTY);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = new String(EMPTY);
		Boolean isCryptedPart = false;
		while ((line = reader.readLine()) != null) {
			if (line.equals(CRYPT_PART)) {
				isCryptedPart = true;
			}
			else if (line.equals(END_CRYPT_PART)) {
				isCryptedPart = false;
			}
			else if (line.contains(ENCRYPTION)) {
				
			}
			else {
				if (!isCryptedPart) {
					out.append(line + JUMP_LINE);
				}
				
			}	
		}
		reader.close();
		this.notifyObserver(READ_CLICK);

		return out.toString().trim();
	}
	
	
}
