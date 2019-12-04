package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.AbstractControler;
import model.Observer;

public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Cryptographie";
	private static final Dimension WINDOW_DIMENSION = new Dimension(950, 500);
	private static final String EMPTY = "";

	private AbstractControler controler;
	private JPanel panelPrincipal = new JPanel();
	private JButton crypt, decrypt, chooseCrypt, chooseDecrypt, chooseRead, read;
	private JTextField start, stop, cryptKey, decryptKey, readKey, filePathCrypt, filePathDecrypt, filePathRead;
	private JCheckBox cryptAll;
	private JLabel from, to, key, or, keyToDecrypt, fileCrypt, fileDecrypt, fileRead, readLabel;

	public View(AbstractControler controler) {
		this.controler = controler;
		this.setTitle(TITLE);
		this.setSize(WINDOW_DIMENSION);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panelPrincipal.setLayout(null);
		//this.panelPrincipal.setBackground(Color.BLUE);
		this.setContentPane(this.panelPrincipal);
		this.initFrame();
		this.setVisible(true);
	}

	public void initFrame() {

		// Déclaration des Listeners
		DecryptButtonListener decryptButtonListener = new DecryptButtonListener();
		CryptButtonListener cryptButtonListener = new CryptButtonListener();
		AllCheckBoxListener allCheckBoxListener = new AllCheckBoxListener();
		ChooseFileListener chooseFileListener = new ChooseFileListener();

		// Initialisation du panel de cryptage
		JPanel cryptPan = new JPanel();
		cryptPan.setBounds(10, 10, 850, 40);
		this.cryptKey = new JTextField();
		this.start = new JTextField();
		this.stop = new JTextField();
		this.cryptAll = new JCheckBox("Crypt All");
		this.chooseCrypt = new JButton("Choose File ...");
		this.from = new JLabel("Crypt from line");
		this.to = new JLabel("to");
		this.key = new JLabel("with key");
		this.or = new JLabel("or");
		this.cryptKey.setColumns(8);
		this.start.setColumns(5);
		this.stop.setColumns(5);
		this.fileCrypt = new JLabel("the file");
		this.filePathCrypt = new JTextField();
		this.filePathCrypt.setEditable(false);
		this.filePathCrypt.setColumns(8);
		this.crypt = new JButton("Crypt");
		this.chooseCrypt.addActionListener(chooseFileListener);
		this.crypt.addActionListener(cryptButtonListener);
		this.cryptAll.addActionListener(allCheckBoxListener);

		cryptPan.add(this.cryptAll);
		cryptPan.add(this.or);
		cryptPan.add(this.from);
		cryptPan.add(this.start);
		cryptPan.add(this.to);
		cryptPan.add(this.stop);
		cryptPan.add(this.key);
		cryptPan.add(this.cryptKey);
		cryptPan.add(this.fileCrypt);
		cryptPan.add(this.filePathCrypt);
		cryptPan.add(this.chooseCrypt);
		cryptPan.add(this.crypt);
		this.panelPrincipal.add(cryptPan);

		// Initialisation du panel de decryptage
		JPanel decryptPan = new JPanel();
		decryptPan.setBounds(10, 80, 630, 40);
		this.keyToDecrypt = new JLabel("Decrypt with key");
		this.chooseDecrypt = new JButton("Choose File ...");
		this.decryptKey = new JTextField();
		this.decryptKey.setColumns(8);
		this.filePathDecrypt = new JTextField();
		this.filePathDecrypt.setEditable(false);
		this.filePathDecrypt.setColumns(8);
		this.crypt = new JButton("Crypt");
		this.decrypt = new JButton("Decrypt");
		this.fileDecrypt = new JLabel("the file");
		this.decrypt.addActionListener(decryptButtonListener);
		this.chooseDecrypt.addActionListener(chooseFileListener);

		decryptPan.add(this.keyToDecrypt);
		decryptPan.add(this.decryptKey);
		decryptPan.add(this.fileDecrypt);
		decryptPan.add(this.filePathDecrypt);
		decryptPan.add(this.chooseDecrypt);
		decryptPan.add(this.decrypt);
		this.panelPrincipal.add(decryptPan);
		
		//Initialisation du panel de lecture
		JPanel readPan = new JPanel();
		readPan.setBounds(10, 160, 600, 40);
		this.readLabel = new JLabel("Read with key");
		this.readKey = new JTextField();
		this.readKey.setColumns(8);
		this.fileRead = new JLabel("the file");
		this.filePathRead = new JTextField();
		this.filePathRead.setEditable(false);
		this.filePathRead.setColumns(8);
		this.chooseRead = new JButton("Choose file ...");
		this.read = new JButton("Read");
		this.chooseRead.addActionListener(chooseFileListener);
		
		
		readPan.add(this.readLabel);
		readPan.add(this.readKey);
		readPan.add(this.fileRead);
		readPan.add(this.filePathRead);
		readPan.add(this.chooseRead);
		readPan.add(this.read);
		this.panelPrincipal.add(readPan);

		
	}

	public Frame getFrame() {
		return this;
	}

	// Listener pour les boutons permettant de choisir un fichier
	class ChooseFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controler.setPath(chooser.getSelectedFile().getPath());
				if (e.getSource() == chooseCrypt) {
					filePathCrypt.setText(controler.getPath());
				} else {
					filePathDecrypt.setText(controler.getPath());
				}
			}
		}
	}

	// Listener pour le bouton permettant de crypter
	class CryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = new String("");
			if (controler.getAllSelected()) {
				try {
					s = controler.readAllFile(controler.getPath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				int startVal = Integer.parseInt(start.getText());
				int stopVal = Integer.parseInt(stop.getText());
				try {
					s = controler.readFile(controler.getPath(), startVal, stopVal);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			try {
				controler.cryptFile(s, cryptKey.getText());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Listener pour le bouton permettant de decrypter
	class DecryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			String s = new String("");
			try {
				s = controler.readAllFile(controler.getPath());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				controler.decryptFile(s, decryptKey.getText());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	// Listener pour la checkbox permettant de savoir si on veut crypter tout le
	// fichier
	class AllCheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			controler.toggleAllSelected();
		}
	}

	// Listener pour le bouton permettant de lire
	class reasButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	@Override
	public void update(String whatToUpdate) {
		// TODO Auto-generated method stub
		if (whatToUpdate.equals("AllEmpty")) {
			this.decryptKey.setText(EMPTY);
			this.cryptKey.setText(EMPTY);
			this.filePathCrypt.setText(EMPTY);
			this.start.setText(EMPTY);
			this.stop.setText(EMPTY);
		}
		this.start.setEditable(!this.controler.getAllSelected());
		this.stop.setEditable(!this.controler.getAllSelected());

	}
}
