package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrincipalFrame implements ActionListener {

	private JFrame frame;
	private JPanel princ;
	private JButton open, decrypt;
	private JTextField start, stop, key, keyDecrypt;
	private String filePath;

	public PrincipalFrame() {
		this.filePath = null;
		this.frame = new JFrame();
		this.princ = new JPanel();
		this.frame.setTitle("Crypto");
		this.frame.setSize(400, 500);
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addLimit();
		this.addKey();
		this.addButtonOpen();
		this.keyDecrypt();
		this.decryptButton();
		this.frame.add(princ);

	}

	public void keyDecrypt() {
		JPanel keyPan = new JPanel();
		this.keyDecrypt = new JTextField();
		this.keyDecrypt.setColumns(8);
		keyPan.add(this.keyDecrypt);
		this.princ.add(keyPan);
	}

	public void decryptButton() {
		JPanel boutonPane = new JPanel();
		this.decrypt = new JButton("Decrypt ...");
		this.decrypt.addActionListener(this);
		boutonPane.add(decrypt);
		this.princ.add(boutonPane);
	}

	public void addKey() {
		JPanel keyPan = new JPanel();
		this.key = new JTextField();
		this.key.setColumns(8);
		keyPan.add(this.key);
		this.princ.add(keyPan);
	}

	public void addLimit() {
		JPanel limitPane = new JPanel();
		this.start = new JTextField();
		this.stop = new JTextField();
		this.start.setColumns(5);
		this.stop.setColumns(5);
		limitPane.add(this.start);
		limitPane.add(this.stop);
		this.princ.add(limitPane);

	}

	public void addButtonOpen() {
		JPanel boutonPane = new JPanel();
		this.open = new JButton("Crypt File ...");
		this.open.addActionListener(this);
		boutonPane.add(open);
		this.princ.add(boutonPane);
	}

	public String readFile(String file, int start, int stop) {
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

			return fichier.toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public String getFolderPath() {
		String path[] = this.filePath.split("/");
		List<String> tmp = Arrays.asList(path);
		ArrayList<String> folders = new ArrayList<String>(tmp);
		String folderPath = new String();
		for (int i = 0; i < folders.size() - 1; i++) {
			folderPath += folders.get(i) + "/";
		}
		return folderPath;
	}

	public void writeCryptedFile(String file, String contenu) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// File fichier = new File(this.getFolderPath() + file);
		byte[] b = new byte[32];
		for(int i=0; i<32;i++) {
			if (this.key.getText().getBytes().length>i) {
				b[i]=this.key.getText().getBytes()[i];
			}
			else {
				b[i]=0;
			}
		}
		Key key = new SecretKeySpec(b, "AES");
		Cipher desCipher;
		desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] textEncrypted = desCipher.doFinal(contenu.getBytes());

		byte[] tt = Base64.getEncoder().encode(textEncrypted);
		//byte[] textEncrypted = desCipher.doFinal(text);
		String s = new String(tt);
		FileWriter fichierToWrite = new FileWriter(this.getFolderPath() + file + "Crypted" + ".txt");
		fichierToWrite.write(s);
		FileWriter fichierToWrite1 = new FileWriter(this.getFolderPath() + file + ".txt");
		fichierToWrite1.write(contenu);
		fichierToWrite.close();
		fichierToWrite1.close();
		


	}

	public void writeDecryptedFile(String file, String contenu) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] b = new byte[32];
		for(int i=0; i<32;i++) {
			if (this.key.getText().getBytes().length>i) {
				b[i]=this.keyDecrypt.getText().getBytes()[i];
			}
			else {
				b[i]=0;
			}
		}
		Key key = new SecretKeySpec(b, "AES");
		Cipher desCipher;
		byte[] text = Base64.getDecoder().decode(contenu.trim());
		desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.DECRYPT_MODE, key);
		
		
		byte[] textDecrypted = desCipher.doFinal(text);
		
		String s = new String(textDecrypted);
		FileWriter fichierToWrite2 = new FileWriter(this.getFolderPath() + file + ".txt");
		fichierToWrite2.write(s);
		fichierToWrite2.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == open) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.filePath = chooser.getSelectedFile().getPath();
				int startVal = Integer.parseInt(this.start.getText());
				int stopVal = Integer.parseInt(this.stop.getText());
				String s = readFile(this.filePath, startVal, stopVal);
				try {
					this.writeCryptedFile("DocToCrypt", s);
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}
		if (e.getSource() == this.decrypt) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.filePath = chooser.getSelectedFile().getPath();
				//int startVal = Integer.parseInt(this.start.getText());
				//int stopVal = Integer.parseInt(this.stop.getText());
				String s = readFile(this.filePath, 1, 12);
				try {
					this.writeDecryptedFile("DocDecrypt", s);
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

}
