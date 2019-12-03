package controler;

import java.io.BufferedReader;
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

import model.AbstractModel;

public abstract class AbstractControler {

	protected AbstractModel model;

	public AbstractControler(AbstractModel model) {
		this.model = model;
	}

	public String readFile(String file, int start, int stop) {
		// TODO Auto-generated method stub
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
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
			this.control();
			return fichier.toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	public void setPath(String path) {
		this.model.setPath(path);
		this.control();
	}

	public String getPath() {
		this.control();
		return this.model.getPath();
	}
	
	public String getFolderPath(String filePath) {
		String path[] = filePath.split("/");
		List<String> tmp = Arrays.asList(path);
		ArrayList<String> folders = new ArrayList<String>(tmp);
		String folderPath = new String();
		for (int i = 0; i < folders.size() - 1; i++) {
			folderPath += folders.get(i) + "/";
		}
		this.control();
		return folderPath;
	}

	public void writeCryptedFile(String file, String contenu, String cryptKey) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		System.out.println(file);
		byte[] b = new byte[32];
		for (int i = 0; i < 32; i++) {
			if (cryptKey.getBytes().length > i) {
				b[i] = cryptKey.getBytes()[i];
			} else {
				b[i] = 0;
			}
		}
		Key key = new SecretKeySpec(b, "AES");
		Cipher desCipher;
		desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] textEncrypted = desCipher.doFinal(contenu.getBytes());

		byte[] tt = Base64.getEncoder().encode(textEncrypted);
		String s = new String(tt);
		FileWriter fichierToWrite = new FileWriter(model.getPath() + file + "Crypted" + ".txt");
		fichierToWrite.write(s);
		FileWriter fichierToWrite1 = new FileWriter(model.getPath() + file + ".txt");
		fichierToWrite1.write(contenu);
		fichierToWrite.close();
		fichierToWrite1.close();
		this.control();
	}

	public void writeDecryptedFile(String file, String contenu, String decryptKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] b = new byte[32];
		for (int i = 0; i < 32; i++) {
			if (decryptKey.getBytes().length > i) {
				b[i] = decryptKey.getBytes()[i];
			} else {
				b[i] = 0;
			}
		}
		Key key = new SecretKeySpec(b, "AES");
		Cipher desCipher;
		byte[] text = Base64.getDecoder().decode(contenu.trim());
		desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.DECRYPT_MODE, key);

		byte[] textDecrypted = desCipher.doFinal(text);

		String s = new String(textDecrypted);
		FileWriter fichierToWrite2 = new FileWriter(model.getPath() + file + ".txt");
		fichierToWrite2.write(s);
		fichierToWrite2.close();
		this.control();
	}

	// Méthode de contrôle
	abstract void control();
}
