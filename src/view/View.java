package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import controler.AbstractControler;
import model.Observer;

public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Cryptographie";
	private static int ROWS_TEXTAREA = 30;
	private static int COLUMNS_TEXTAREA = 55;
	private static int DEFAUT_COLUMNS = 8;
	private static final String EMPTY = "";
	private static String CRYPT = new String("Crypt file ...");
	private static String READ = new String("Read file ...");
	private static String CRYPT_ALL = new String("Crypt All");
	private static String LABEL_BLOCK = new String("Crypt block(s) : ");
	private static String LABEL_READ = new String("Decryption key : ");
	private static String LABEL_KEY = new String("Crypt key : ");
	private static String LABEL_WITH_NAME = new String("Output file name : ");
	private static String LABEL_METHODE = new String("Methode : ");
	private static String AES = new String("AES");
	private static String DES = new String("DES");
	private static String MDP_EMPTY = new String("Veuillez renseigner un mot de passe");
	private static String NAME_EMPTY = new String("Veuillez renseigner un nom");
	private static String BLOCK_EMPTY = new String("Veuillez renseigner un block ou cochez crypt all");
	private static String READ_WITH = new String("Lecture avec cle");
	private static String READ_WITHOUT = new String("Lecture sans cle");
	private static String CRYPTED = new String("Crypted");
	private static String ALL_READ_EMPTY = new String("AllReadEmpty");
	private static String ALL_CRYPT_EMPTY = new String("AllCryptEmpty");
	private static String NOTHING_YET = new String("Nothing yet");
	private static String INFORMATIONS = new String("Informations");
	private static String CRYPT_FILE = new String("Crypt file");
	private static String READ_FILE = new String("Read file");
	private static String PREVIEW = new String("Preview");
	private static String ARIAL = new String("Arial");
	
	private static final Font FONT = new Font(ARIAL, Font.PLAIN , 18);
	
	private AbstractControler controler;
	private JPanel cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cryptPan, infoPan, readPan, previewPan;
	private JButton crypt, read;
	private JTextField block, cryptKey, readKey, filePathCrypt, filePathRead, name;
	private JCheckBox cryptAll, aes, des;
	private JLabel from, key, readLabel, withName, withMethode, info;
	private JTextArea lecture;
	private JScrollPane scrollbar;
	
	public View(AbstractControler controler) {
		this.controler = controler;
		this.setTitle(TITLE);
		this.setSize(this.getToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initFrame();
		this.setVisible(true);
	}

	public void initFrame() {
		this.initCryptPan();
		this.initReadPan();
		this.initPreviewPan();
		this.initInfoPan();
		this.initContentPan();
	}
	
	public void initCryptPan() {
		CryptButtonListener cryptButtonListener = new CryptButtonListener();
		AllCheckBoxListener allCheckBoxListener = new AllCheckBoxListener();
		DesCheckBoxListener desCheckBoxListener = new DesCheckBoxListener();
		AesCheckBoxListener aesCheckBoxListener = new AesCheckBoxListener();
		
		GridBagConstraints gbc = new GridBagConstraints();

		this.cryptPan = new JPanel();
		this.cryptPan.setBackground(Color.WHITE);
		this.cryptPan.setPreferredSize(new Dimension(550, 280));
		this.cryptPan.setLayout(new GridBagLayout());
        Border borderLine1 = BorderFactory.createLineBorder(Color.BLACK,5,true);
        Border border1 = BorderFactory.createTitledBorder(borderLine1, CRYPT_FILE, TitledBorder.LEFT,TitledBorder.TOP, new Font(ARIAL, Font.PLAIN , 26), Color.black);
        this.cryptPan.setBorder(border1);
        
        this.cell1 = new JPanel();
        this.cell1.setLayout(new BorderLayout());
        this.cell1.setBackground(Color.WHITE);
        this.cell1.setPreferredSize(new Dimension(240, 50));		
        this.cell2 = new JPanel();
        this.cell2.setLayout(new BorderLayout());
        this.cell2.setBackground(Color.WHITE);
        this.cell2.setPreferredSize(new Dimension(240, 30));
        this.cell3 = new JPanel();
        this.cell3.setLayout(new BorderLayout());
        this.cell3.setBackground(Color.WHITE);
        this.cell3.setPreferredSize(new Dimension(200, 30));
        this.cell4 = new JPanel();
        this.cell4.setLayout(new FlowLayout(FlowLayout.LEFT,0,15));
        this.cell4.setBackground(Color.WHITE);
        this.cell4.setPreferredSize(new Dimension(400, 50));
        this.cell5 = new JPanel();
        this.cell5.setLayout(new BorderLayout());
        this.cell5.setBackground(Color.WHITE);
        this.cell5.setPreferredSize(new Dimension(240, 30));
        this.cell6 = new JPanel();
        this.cell6.setLayout(new BorderLayout());
        this.cell6.setBackground(Color.WHITE);
        this.cell6.setPreferredSize(new Dimension(200, 40));
        
        this.cryptAll = new JCheckBox(CRYPT_ALL);
		this.cryptAll.setFont(FONT);
		this.from = new JLabel(LABEL_BLOCK);
		this.from.setFont(FONT);
		this.block = new JTextField();
		this.key = new JLabel(LABEL_KEY);
		this.key.setFont(FONT);
		this.cryptKey = new JTextField();
		this.withMethode = new JLabel(LABEL_METHODE);
		this.withMethode.setFont(new Font(ARIAL, Font.PLAIN , 18));
		this.aes = new JCheckBox(AES);
		this.aes.setFont(FONT);
		this.aes.setSelected(true);
		this.des = new JCheckBox(DES);
		this.des.setFont(FONT);
		this.withName = new JLabel(LABEL_WITH_NAME);
		this.withName.setFont(FONT);
		this.name = new JTextField();
		this.filePathCrypt = new JTextField();
		this.filePathCrypt.setEditable(false);
		this.filePathCrypt.setColumns(DEFAUT_COLUMNS);
		this.crypt = new JButton(CRYPT);
		this.crypt.setFont(new Font(ARIAL, Font.PLAIN , 18));
		this.cryptAll.addActionListener(allCheckBoxListener);
		this.aes.addActionListener(aesCheckBoxListener);
		this.des.addActionListener(desCheckBoxListener);
		this.crypt.addActionListener(cryptButtonListener);
		
		this.cell1.add(cryptAll, BorderLayout.LINE_START);
		this.cell2.add(this.from, BorderLayout.LINE_START);
		this.cell2.add(this.block);
		this.cell3.add(this.key, BorderLayout.LINE_START);
		this.cell3.add(this.cryptKey);
		this.cell4.add(this.withMethode);
		this.cell4.add(this.aes);
		this.cell4.add(this.des);
		this.cell5.add(this.withName, BorderLayout.LINE_START);
		this.cell5.add(this.name);
		this.cell6.add(this.crypt);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        this.cryptPan.add(this.cell1,gbc);
       
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.cryptPan.add(this.cell2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.cryptPan.add(this.cell3, gbc);		

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.cryptPan.add(this.cell4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        this.cryptPan.add(this.cell5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8,0,0,0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.cryptPan.add(this.cell6, gbc);
	}
	
	public void initReadPan() {
		ReadButtonListener readButtonListener = new ReadButtonListener();
		
		GridBagConstraints gbc = new GridBagConstraints();
        
        this.readPan = new JPanel();
        this.readPan.setBackground(Color.WHITE);
        this.readPan.setPreferredSize(new Dimension(550, 180));
        this.readPan.setLayout(new GridBagLayout());
        Border borderLine2 = BorderFactory.createLineBorder(Color.BLACK,5,true);
        Border border2 = BorderFactory.createTitledBorder(borderLine2, READ_FILE, TitledBorder.LEFT,TitledBorder.TOP, new Font(ARIAL, Font.PLAIN , 26), Color.black);
        this.readPan.setBorder(border2);
        
        this.cell7 = new JPanel();
        this.cell7.setLayout(new BorderLayout());
        this.cell7.setBackground(Color.WHITE);
        this.cell7.setPreferredSize(new Dimension(280, 30)); 
        this.cell8 = new JPanel();
        this.cell8.setLayout(new BorderLayout());
        this.cell8.setBackground(Color.WHITE);
        this.cell8.setPreferredSize(new Dimension(200, 40));
        
        this.readLabel = new JLabel(LABEL_READ);
        this.readLabel.setFont(FONT);
		this.readKey = new JTextField();
        this.readKey.setFont(FONT);
		this.read = new JButton(READ);
		this.read.setFont(new Font(ARIAL, Font.PLAIN , 18));
		this.read.addActionListener(readButtonListener);
		this.filePathRead = new JTextField();
		this.filePathRead.setEditable(false);
		
		this.cell7.add(this.readLabel, BorderLayout.LINE_START);
		this.cell7.add(this.readKey);
		this.cell8.add(this.read);
		
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        this.readPan.add(this.cell7,gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 1;
        gbc.insets = new Insets(8,0,0,0);
        this.readPan.add(this.cell8,gbc);
	}
	
	public void initPreviewPan() {
		GridBagConstraints gbc = new GridBagConstraints();

		this.previewPan = new JPanel();
		this.previewPan.setBackground(Color.WHITE);
		this.previewPan.setPreferredSize(new Dimension(800, 850));
		this.previewPan.setLayout(new GridBagLayout());
        Border borderLine3 = BorderFactory.createLineBorder(Color.BLACK,5,true);
        Border border3 = BorderFactory.createTitledBorder(borderLine3, PREVIEW, TitledBorder.CENTER,TitledBorder.TOP, new Font(ARIAL, Font.PLAIN , 26), Color.black);
        this.previewPan.setBorder(border3);
        
        this.cell9 = new JPanel();
        this.cell9.setLayout(new BorderLayout());
        this.cell9.setBackground(Color.WHITE);
        this.cell9.setPreferredSize(new Dimension(700, 800));
        
		this.lecture = new JTextArea();
		this.lecture.setEditable(false);
		this.lecture.setRows(ROWS_TEXTAREA);
		this.lecture.setColumns(COLUMNS_TEXTAREA);
		this.scrollbar = new JScrollPane(this.lecture);
        this.scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        
        cell9.add(this.scrollbar);
        
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        this.previewPan.add(this.cell9,gbc);
	}
	
	public void initInfoPan() {
        this.infoPan = new JPanel();
        this.infoPan.setBackground(Color.WHITE);
        this.infoPan.setPreferredSize(new Dimension(550, 100));
        this.infoPan.setLayout(new GridBagLayout());
        Border borderLine4 = BorderFactory.createLineBorder(Color.BLACK,5,true);
        Border border4 = BorderFactory.createTitledBorder(borderLine4, INFORMATIONS, TitledBorder.LEFT,TitledBorder.TOP, new Font(ARIAL, Font.PLAIN , 26), Color.black);
        this.infoPan.setBorder(border4);
        
        this.cell10 = new JPanel();
        this.cell10.setBackground(Color.WHITE);
        this.cell10.setPreferredSize(new Dimension(500, 70));
        this.info = new JLabel(NOTHING_YET);
        this.info.setFont(FONT);
        
        this.cell10.add(this.info);
        this.infoPan.add(this.cell10);
    }
	
	public void initContentPan() {
		GridBagConstraints gbc = new GridBagConstraints();  
        
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new GridBagLayout());   
        
        gbc.insets = new Insets(10,10,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        content.add(this.cryptPan, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        content.add(this.readPan, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        content.add(this.infoPan, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        content.add(this.previewPan, gbc);

        this.setContentPane(content);
        this.setVisible(true);
	}
	
	public Frame getFrame() {
		return this;
	}

	public void selectFile(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			controler.setPath(chooser.getSelectedFile().getPath());
			if (e.getSource() == crypt) {
				filePathCrypt.setText(controler.getPath());
			}
			else {
				filePathRead.setText(controler.getPath());
			}
		}
	}
	
	public String getInfo() {
		if (cryptKey.getText().equals(EMPTY)) {
			this.cell10.setBackground(new Color(241,124,124));
			return MDP_EMPTY;
		}
		else if (name.getText().equals(EMPTY)) {
			this.cell10.setBackground(new Color(241,124,124));
			return NAME_EMPTY;
		}
		else if (!controler.getAllSelected() && block.getText().equals(EMPTY)) {
			this.cell10.setBackground(new Color(241,124,124));
			return BLOCK_EMPTY;
		}
		this.cell10.setBackground(Color.WHITE);
		return NOTHING_YET;
	}
	
	public void crypt() {
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
		
		info.setText(getInfo());
		if (info.getText().equals(NOTHING_YET)) {
			String s = new String(EMPTY);
			cell10.setBackground(new Color(152,239,96));

			if (controler.getAllSelected()) {
				try {
					s = controler.readAndCrypt(controler.getPath(), controler.constructKey(cryptKey.getText(), methode), methode);
					info.setText(CRYPTED);
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
					info.setText(CRYPTED);
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

	// Listener pour le bouton permettant de crypter
	class CryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selectFile(e);
			crypt();
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
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controler.setPath(chooser.getSelectedFile().getPath());
				if (e.getSource() == crypt) {
					filePathCrypt.setText(controler.getPath());
				}
				else {
					filePathRead.setText(controler.getPath());
				}
			}
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
				info.setText(READ_WITH);

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException e2) {
				// TODO Auto-generated catch block
				//e2.printStackTrace();

				try {
					toShow = controler.readWithouthKey(controler.getPath());
					info.setText(READ_WITHOUT);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//System.out.println(toShow);
			lecture.setText(toShow);
		}
	}
	
	@Override
	public void update(String whatToUpdate) {
		// TODO Auto-generated method stub
		if (whatToUpdate.equals(ALL_CRYPT_EMPTY)) {
			this.cryptKey.setText(EMPTY);
			this.block.setText(EMPTY);
			this.name.setText(EMPTY);
		}
		else if (whatToUpdate.equals(ALL_READ_EMPTY)) {
			this.readKey.setText(EMPTY);
			this.filePathRead.setText(EMPTY);
		}
		this.block.setEditable(!this.controler.getAllSelected());

	}
}
