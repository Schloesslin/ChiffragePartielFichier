package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controler.AbstractControler;
import model.Observer;

public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Cryptographie";
	private static final Dimension WINDOW_DIMENSION = new Dimension(1300, 900);
	private static final Rectangle BOUNDS_CRYPTPAN = new Rectangle(10, 10,1220, 40);
	private static final Rectangle BOUNDS_READPAN = new Rectangle(10, 110, 600, 40);
	private static final Rectangle BOUNDS_TEXTAREA = new Rectangle(10, 220, 800, 500);
	private static final Rectangle BOUNDS_INFOCRYPT = new Rectangle(0, 50, 310, 20);
	private static final Rectangle BOUNDS_INFOREAD = new Rectangle(0, 150, 150, 20);
	
	private static int ROWS_TEXTAREA = 30;
	private static int COLUMNS_TEXTAREA = 60;
	private static int DEFAUT_COLUMNS = 8;
	private static int BLOCK_COLUMNS = 5;
	private static final String EMPTY = "";
	private static String CRYPT = new String("Crypt");
	private static String READ = new String("Read");
	private static String CRYPT_ALL = new String("Crypt All");
	private static String CHOOSE = new String("Choose file ...");
	private static String LABEL_BLOCK = new String("Crypt block(s)");
	private static String LABEL_READ = new String("Read with key");
	private static String LABEL_FILE = new String("the file");
	private static String LABEL_OR = new String("or");
	private static String LABEL_KEY = new String("with key");
	private static String LABEL_WITH_NAME = new String("with name");
	private static String LABEL_METHODE = new String("and methode");
	private static String AES = new String("AES");
	private static String DES = new String("DES");
	private static String MDP_EMPTY = new String("Veuillez renseigner un mot de passe");
	private static String FILE_EMPTY = new String("Veuillez renseigner un fichier à crypter");
	private static String NAME_EMPTY = new String("Veuillez renseigner un nom");
	private static String BLOCK_EMPTY = new String("Veuillez renseigner un block ou cochez crypt all");
	private static String READ_WITH = new String("Lecture avec cle");
	private static String READ_WITHOUT = new String("Lecture sans cle");
	private static String CRYPTED = new String("Crypted");
	private static String ALL_READ_EMPTY = new String("AllReadEmpty");
	private static String ALL_CRYPT_EMPTY = new String("AllCryptEmpty");
	
	private AbstractControler controler;
	private JPanel panelPrincipal = new JPanel();
	private JButton crypt, chooseCrypt, chooseRead, read;
	private JTextField block, cryptKey, readKey, filePathCrypt, filePathRead, name;
	private JCheckBox cryptAll, aes, des;
	private JLabel from, key, or, fileCrypt, fileRead, readLabel, withName, withMethode, infoRead, infoCrypt;
	private JTextArea lecture;
	private JScrollPane scrollbar;
	
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
		CryptButtonListener cryptButtonListener = new CryptButtonListener();
		AllCheckBoxListener allCheckBoxListener = new AllCheckBoxListener();
		ChooseFileListener chooseFileListener = new ChooseFileListener();
		ReadButtonListener readButtonListener = new ReadButtonListener();
		DesCheckBoxListener desCheckBoxListener = new DesCheckBoxListener();
		AesCheckBoxListener aesCheckBoxListener = new AesCheckBoxListener();

		// Initialisation du panel de cryptage
		JPanel cryptPan = new JPanel();
		cryptPan.setBounds(BOUNDS_CRYPTPAN);
		this.cryptKey = new JTextField();
		this.block = new JTextField();
		this.cryptAll = new JCheckBox(CRYPT_ALL);
		this.chooseCrypt = new JButton(CHOOSE);
		this.from = new JLabel(LABEL_BLOCK);
		this.key = new JLabel(LABEL_KEY);
		this.or = new JLabel(LABEL_OR);
		this.withName = new JLabel(LABEL_WITH_NAME);
		this.name = new JTextField();
		this.name.setColumns(DEFAUT_COLUMNS);
		this.cryptKey.setColumns(8);
		this.block.setColumns(BLOCK_COLUMNS);
		this.withMethode = new JLabel(LABEL_METHODE);
		this.fileCrypt = new JLabel(LABEL_FILE);
		this.filePathCrypt = new JTextField();
		this.filePathCrypt.setEditable(false);
		this.filePathCrypt.setColumns(DEFAUT_COLUMNS);
		this.crypt = new JButton(CRYPT);
		this.aes = new JCheckBox(AES);
		this.aes.setSelected(true);
		this.des = new JCheckBox(DES);
		this.chooseCrypt.addActionListener(chooseFileListener);
		this.crypt.addActionListener(cryptButtonListener);
		this.cryptAll.addActionListener(allCheckBoxListener);
		this.aes.addActionListener(aesCheckBoxListener);
		this.des.addActionListener(desCheckBoxListener);

		cryptPan.add(this.cryptAll);
		cryptPan.add(this.or);
		cryptPan.add(this.from);
		cryptPan.add(this.block);
		cryptPan.add(this.key);
		cryptPan.add(this.cryptKey);
		cryptPan.add(this.fileCrypt);
		cryptPan.add(this.filePathCrypt);
		cryptPan.add(this.chooseCrypt);
		cryptPan.add(this.withName);
		cryptPan.add(this.name);
		cryptPan.add(this.withMethode);
		cryptPan.add(this.aes);
		cryptPan.add(this.des);
		cryptPan.add(this.crypt);
		this.panelPrincipal.add(cryptPan);

		//Initialisation du panel de lecture
		JPanel readPan = new JPanel();
		readPan.setBounds(BOUNDS_READPAN);
		this.readLabel = new JLabel(LABEL_READ);
		this.readKey = new JTextField();
		this.readKey.setColumns(DEFAUT_COLUMNS);
		this.filePathRead = new JTextField();
		this.fileRead = new JLabel(LABEL_FILE);
		this.filePathRead.setEditable(false);
		this.filePathRead.setColumns(DEFAUT_COLUMNS);
		this.chooseRead = new JButton(CHOOSE);
		this.read = new JButton(READ);
		this.chooseRead.addActionListener(chooseFileListener);
		this.read.addActionListener(readButtonListener);
		
		readPan.add(this.readLabel);
		readPan.add(this.readKey);
		readPan.add(this.fileRead);
		readPan.add(this.filePathRead);
		readPan.add(this.chooseRead);
		readPan.add(this.read);
		this.panelPrincipal.add(readPan);

        JPanel lecturePan = new JPanel();
        lecturePan.setBounds(BOUNDS_TEXTAREA);
		this.lecture = new JTextArea();
		this.lecture.setEditable(false);
		this.lecture.setRows(ROWS_TEXTAREA);
		this.lecture.setColumns(COLUMNS_TEXTAREA);
		this.scrollbar = new JScrollPane(this.lecture);
        this.scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        this.scrollbar.setBounds(BOUNDS_TEXTAREA);
        
        lecturePan.add(this.scrollbar);
        this.panelPrincipal.add(lecturePan);
        
        JPanel infoCryptPan = new JPanel();
        infoCryptPan.setBounds(BOUNDS_INFOCRYPT);
        this.infoCrypt  = new JLabel();
        infoCryptPan.add(this.infoCrypt);
        this.panelPrincipal.add(infoCryptPan);
        
        JPanel infoReadPan = new JPanel();
        infoReadPan.setBounds(BOUNDS_INFOREAD);
        this.infoRead  = new JLabel();
        infoReadPan.add(this.infoRead);
        this.panelPrincipal.add(infoReadPan);
        
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
				}
				else {
					filePathRead.setText(controler.getPath());
				}
			}
		}
	}

	// Listener pour le bouton permettant de crypter
	class CryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String methode = new String(EMPTY);
			ArrayList<Integer> startBlock = new ArrayList<Integer>();
			ArrayList<Integer> stopBlock = new ArrayList<Integer>();
			ArrayList<Integer> blocks = new ArrayList<Integer>();;
			if(aes.isSelected()) {
				methode = AES;
			}
			else {
				methode = DES;
			}
			
			if(filePathCrypt.getText().equals(EMPTY)) {
				infoCrypt.setText(FILE_EMPTY);
			}
			else if (cryptKey.getText().equals(EMPTY)) {
				infoCrypt.setText(MDP_EMPTY);
			}
			else if (name.getText().equals(EMPTY)) {
				infoCrypt.setText(NAME_EMPTY);
			}
			else if (!controler.getAllSelected() && block.getText().equals(EMPTY)) {
				infoCrypt.setText(BLOCK_EMPTY);
			}
			else {
				String s = new String(EMPTY);

				if (controler.getAllSelected()) {
					try {
						s = controler.readAndCrypt(controler.getPath(), controler.constructKey(cryptKey.getText(), methode), methode);
						infoCrypt.setText(CRYPTED);
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					blocks = controler.getBlocks(block.getText());
					
					for (int i=0; i<blocks.size()-1; i+=2) {
						startBlock.add(blocks.get(i));
						stopBlock.add(blocks.get(i+1));
					}
					try {
						
						s = controler.readAndCryptParts(controler.getPath(), startBlock, stopBlock, controler.constructKey(cryptKey.getText(), methode), methode);
						infoCrypt.setText(CRYPTED);
					} catch (InvalidKeyException | NumberFormatException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}   
				}
				try {
					controler.writeFile(name.getText(), s);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startBlock.clear();
				stopBlock.clear();
				blocks.clear();
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

	class AesCheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			des.setSelected(false);
			aes.setSelected(true);
			}
	}
	
	class DesCheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			aes.setSelected(false);
			des.setSelected(true);
		}
	}
	
	// Listener pour le bouton permettant de lire
	class ReadButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String toShow = new String(EMPTY);
			String methode = new String(EMPTY);
			
			try {
				if (controler.readFirstLine(controler.getPath()).contains(AES)) {
					methode=AES;
				}
				else {
					methode = DES;
				}
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			try {
				toShow = controler.readWithKey(controler.getPath(), controler.constructKey(readKey.getText(), methode));
				infoRead.setText(READ_WITH);

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException e2) {
				// TODO Auto-generated catch block
				//e2.printStackTrace();

				try {
					toShow = controler.readWithouthKey(controler.getPath());
					infoRead.setText(READ_WITHOUT);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			lecture.setText(toShow);
		}
	}
	
	@Override
	public void update(String whatToUpdate) {
		// TODO Auto-generated method stub
		if (whatToUpdate.equals(ALL_CRYPT_EMPTY)) {
			this.cryptKey.setText(EMPTY);
			this.filePathCrypt.setText(EMPTY);
			this.block.setText(EMPTY);
			this.filePathRead.setText(EMPTY);
			this.name.setText(EMPTY);
		}
		else if (whatToUpdate.equals(ALL_READ_EMPTY)) {
			this.readKey.setText(EMPTY);
			this.filePathRead.setText(EMPTY);
		}
		this.block.setEditable(!this.controler.getAllSelected());

	}
}
