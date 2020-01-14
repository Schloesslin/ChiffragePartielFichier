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
import java.security.spec.InvalidKeySpecException;
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
	private static final int ROWS_TEXTAREA = 30;
	private static final int COLUMNS_TEXTAREA = 40;
	private static final int DEFAUT_COLUMNS = 8;
	private static final String EMPTY = "";
	private static final String CRYPT = new String("Crypt file ...");
	private static final String READ = new String("Read file ...");
	private static final String CRYPT_ALL = new String("Crypt All");
	private static final String LABEL_BLOCK = new String("Crypt block(s) : ");
	private static final String LABEL_READ = new String("Decrypt key : ");
	private static final String LABEL_KEY = new String("Crypt key : ");
	private static final String LABEL_WITH_NAME = new String("Output file name : ");
	private static final String LABEL_METHODE = new String("Methode : ");
	private static final String AES = new String("AES");
	private static final String AES_WITH_OPTION = new String("AES/CBC/PKCS5PADDING");
	private static final String DES = new String("DES");
	private static final String DES_WITH_OPTION = new String("DES/CBC/PKCS5PADDING");
	private static final String MDP_EMPTY = new String("Please enter a password");
	private static final String NAME_EMPTY = new String("Please entrer a name");
	private static final String BLOCK_EMPTY = new String("Please enter blocks to crypt or tick \"crypt all\"");
	private static final String READ_WITH = new String("Reading with key");
	private static final String READ_WITHOUT = new String("Reading without key");
	private static final String CRYPTED = new String("Crypted");
	private static final String ALL_READ_EMPTY = new String("AllReadEmpty");
	private static final String ALL_CRYPT_EMPTY = new String("AllCryptEmpty");
	private static final String NOTHING_YET = new String("Nothing yet");
	private static final String INFORMATIONS = new String("Informations");
	private static final String CRYPT_FILE = new String("Crypt file");
	private static final String READ_FILE = new String("Read file");
	private static final String PREVIEW = new String("Preview");
	private static final String ARIAL = new String("Arial");

	private static final Font FONT = new Font(ARIAL, Font.PLAIN, 18);
	private static final Font FONT_TITLE = new Font(ARIAL, Font.PLAIN, 26);

	private static final Insets INSET_NULL = new Insets(0, 0, 0, 0);
	private static final Insets INSET_SMALL = new Insets(8, 0, 0, 0);
	private static final Insets INSET_BIG = new Insets(10, 10, 0, 0);

	private static final Dimension CRYPT_PAN_DIMENSION = new Dimension(550, 280);
	private static final Dimension READ_PAN_DIMENSION = new Dimension(550, 180);
	private static final Dimension PREVIEW_PAN_DIMENSION = new Dimension(600, 650);
	private static final Dimension INFO_PAN_DIMENSION = new Dimension(550, 100);
	private static final Dimension CRYPT_ALL_PAN_DIMENSION = new Dimension(240, 50);
	private static final Dimension BLOCK_PAN_DIMENSION = new Dimension(240, 30);
	private static final Dimension CRYPT_KEY_PAN_DIMENSION = new Dimension(200, 30);
	private static final Dimension METHODE_PAN_DIMENSION = new Dimension(400, 50);
	private static final Dimension OUTPUT_FILE_NAME_PAN_DIMENSION = new Dimension(240, 30);
	private static final Dimension CRYPT_BUTTON_PAN_DIMENSION = new Dimension(200, 40);
	private static final Dimension READ_KEY_PAN_DIMENSION = new Dimension(280, 30);
	private static final Dimension READ_BUTTON_PAN_DIMENSION = new Dimension(200, 40);
	private static final Dimension PREVIEW_AREA_PAN_DIMENSION = new Dimension(500, 600);
	private static final Dimension INFO_LABEL_PAN_DIMENSION = new Dimension(380, 50);

	private static final Color SOFT_RED = new Color(241, 124, 124);
	private static final Color SOFT_GREEN = new Color(152, 239, 96);

	private AbstractControler controler;
	private JPanel cryptAllPan, blockPan, cryptKeyPan, methodePan, outputFileNamePan, cryptButtonPan, readKeyPan,
			readButtonPan, previewAreaPan, infoLabelPan, cryptPan, infoPan, readPan, previewPan;
	private JButton crypt, read;
	private JTextField block, cryptKey, readKey, filePathCrypt, filePathRead, name;
	private JCheckBox cryptAll, aes, des;
	private JLabel blockLabel, keyLabel, readLabel, OutputFileNameLabel, methodeLabel, infoLabel;
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
		this.cryptPan.setPreferredSize(CRYPT_PAN_DIMENSION);
		this.cryptPan.setLayout(new GridBagLayout());
		Border borderLine1 = BorderFactory.createLineBorder(Color.BLACK, 5, true);
		Border border1 = BorderFactory.createTitledBorder(borderLine1, CRYPT_FILE, TitledBorder.LEFT, TitledBorder.TOP,
				FONT_TITLE, Color.black);
		this.cryptPan.setBorder(border1);

		this.cryptAllPan = new JPanel();
		this.cryptAllPan.setLayout(new BorderLayout());
		this.cryptAllPan.setBackground(Color.WHITE);
		this.cryptAllPan.setPreferredSize(CRYPT_ALL_PAN_DIMENSION);
		this.blockPan = new JPanel();
		this.blockPan.setLayout(new BorderLayout());
		this.blockPan.setBackground(Color.WHITE);
		this.blockPan.setPreferredSize(BLOCK_PAN_DIMENSION);
		this.cryptKeyPan = new JPanel();
		this.cryptKeyPan.setLayout(new BorderLayout());
		this.cryptKeyPan.setBackground(Color.WHITE);
		this.cryptKeyPan.setPreferredSize(CRYPT_KEY_PAN_DIMENSION);
		this.methodePan = new JPanel();
		this.methodePan.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 15));
		this.methodePan.setBackground(Color.WHITE);
		this.methodePan.setPreferredSize(METHODE_PAN_DIMENSION);
		this.outputFileNamePan = new JPanel();
		this.outputFileNamePan.setLayout(new BorderLayout());
		this.outputFileNamePan.setBackground(Color.WHITE);
		this.outputFileNamePan.setPreferredSize(OUTPUT_FILE_NAME_PAN_DIMENSION);
		this.cryptButtonPan = new JPanel();
		this.cryptButtonPan.setLayout(new BorderLayout());
		this.cryptButtonPan.setBackground(Color.WHITE);
		this.cryptButtonPan.setPreferredSize(CRYPT_BUTTON_PAN_DIMENSION);

		this.cryptAll = new JCheckBox(CRYPT_ALL);
		this.cryptAll.setFont(FONT);
		this.cryptAll.setBackground(Color.WHITE);
		this.blockLabel = new JLabel(LABEL_BLOCK);
		this.blockLabel.setFont(FONT);
		this.block = new JTextField();
		this.keyLabel = new JLabel(LABEL_KEY);
		this.keyLabel.setFont(FONT);
		this.cryptKey = new JTextField();
		this.methodeLabel = new JLabel(LABEL_METHODE);
		this.methodeLabel.setFont(FONT);
		this.aes = new JCheckBox(AES);
		this.aes.setFont(FONT);
		this.aes.setSelected(true);
		this.aes.setBackground(Color.WHITE);
		this.des = new JCheckBox(DES);
		this.des.setFont(FONT);
		this.des.setBackground(Color.WHITE);
		this.OutputFileNameLabel = new JLabel(LABEL_WITH_NAME);
		this.OutputFileNameLabel.setFont(FONT);
		this.name = new JTextField();
		this.filePathCrypt = new JTextField();
		this.filePathCrypt.setEditable(false);
		this.filePathCrypt.setColumns(DEFAUT_COLUMNS);
		this.crypt = new JButton(CRYPT);
		this.crypt.setFont(FONT);
		this.cryptAll.addActionListener(allCheckBoxListener);
		this.aes.addActionListener(aesCheckBoxListener);
		this.des.addActionListener(desCheckBoxListener);
		this.crypt.addActionListener(cryptButtonListener);

		this.cryptAllPan.add(cryptAll, BorderLayout.LINE_START);
		this.blockPan.add(this.blockLabel, BorderLayout.LINE_START);
		this.blockPan.add(this.block);
		this.cryptKeyPan.add(this.keyLabel, BorderLayout.LINE_START);
		this.cryptKeyPan.add(this.cryptKey);
		this.methodePan.add(this.methodeLabel);
		this.methodePan.add(this.aes);
		this.methodePan.add(this.des);
		this.outputFileNamePan.add(this.OutputFileNameLabel, BorderLayout.LINE_START);
		this.outputFileNamePan.add(this.name);
		this.cryptButtonPan.add(this.crypt);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		this.cryptPan.add(this.cryptAllPan, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		this.cryptPan.add(this.blockPan, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		this.cryptPan.add(this.cryptKeyPan, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.cryptPan.add(this.methodePan, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		this.cryptPan.add(this.outputFileNamePan, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = INSET_SMALL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.cryptPan.add(this.cryptButtonPan, gbc);
	}

	public void initReadPan() {
		ReadButtonListener readButtonListener = new ReadButtonListener();

		GridBagConstraints gbc = new GridBagConstraints();

		this.readPan = new JPanel();
		this.readPan.setBackground(Color.WHITE);
		this.readPan.setPreferredSize(READ_PAN_DIMENSION);
		this.readPan.setLayout(new GridBagLayout());
		Border borderLine2 = BorderFactory.createLineBorder(Color.BLACK, 5, true);
		Border border2 = BorderFactory.createTitledBorder(borderLine2, READ_FILE, TitledBorder.LEFT, TitledBorder.TOP,
				FONT_TITLE, Color.black);
		this.readPan.setBorder(border2);

		this.readKeyPan = new JPanel();
		this.readKeyPan.setLayout(new BorderLayout());
		this.readKeyPan.setBackground(Color.WHITE);
		this.readKeyPan.setPreferredSize(READ_KEY_PAN_DIMENSION);
		this.readButtonPan = new JPanel();
		this.readButtonPan.setLayout(new BorderLayout());
		this.readButtonPan.setBackground(Color.WHITE);
		this.readButtonPan.setPreferredSize(READ_BUTTON_PAN_DIMENSION);

		this.readLabel = new JLabel(LABEL_READ);
		this.readLabel.setFont(FONT);
		this.readKey = new JTextField();
		this.readKey.setFont(FONT);
		this.read = new JButton(READ);
		this.read.setFont(FONT);
		this.read.addActionListener(readButtonListener);
		this.filePathRead = new JTextField();
		this.filePathRead.setEditable(false);

		this.readKeyPan.add(this.readLabel, BorderLayout.LINE_START);
		this.readKeyPan.add(this.readKey);
		this.readButtonPan.add(this.read);

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = INSET_NULL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		this.readPan.add(this.readKeyPan, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy = 1;
		gbc.insets = INSET_SMALL;
		this.readPan.add(this.readButtonPan, gbc);
	}

	public void initPreviewPan() {
		GridBagConstraints gbc = new GridBagConstraints();

		this.previewPan = new JPanel();
		this.previewPan.setBackground(Color.WHITE);
		this.previewPan.setPreferredSize(PREVIEW_PAN_DIMENSION);
		this.previewPan.setLayout(new GridBagLayout());
		Border borderLine3 = BorderFactory.createLineBorder(Color.BLACK, 5, true);
		Border border3 = BorderFactory.createTitledBorder(borderLine3, PREVIEW, TitledBorder.CENTER, TitledBorder.TOP,
				FONT_TITLE, Color.black);
		this.previewPan.setBorder(border3);

		this.previewAreaPan = new JPanel();
		this.previewAreaPan.setLayout(new BorderLayout());
		this.previewAreaPan.setBackground(Color.WHITE);
		this.previewAreaPan.setPreferredSize(PREVIEW_AREA_PAN_DIMENSION);

		this.lecture = new JTextArea();
		this.lecture.setEditable(false);
		this.lecture.setRows(ROWS_TEXTAREA);
		this.lecture.setColumns(COLUMNS_TEXTAREA);
		this.scrollbar = new JScrollPane(this.lecture);
		this.scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		previewAreaPan.add(this.scrollbar);

		gbc.insets = INSET_NULL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		this.previewPan.add(this.previewAreaPan, gbc);
	}

	public void initInfoPan() {
		this.infoPan = new JPanel();
		this.infoPan.setBackground(Color.WHITE);
		this.infoPan.setPreferredSize(INFO_PAN_DIMENSION);
		this.infoPan.setLayout(new GridBagLayout());
		Border borderLine4 = BorderFactory.createLineBorder(Color.BLACK, 5, true);
		Border border4 = BorderFactory.createTitledBorder(borderLine4, INFORMATIONS, TitledBorder.LEFT,
				TitledBorder.TOP, FONT_TITLE, Color.black);
		this.infoPan.setBorder(border4);

		this.infoLabelPan = new JPanel();
		this.infoLabelPan.setBackground(Color.WHITE);
		this.infoLabelPan.setPreferredSize(INFO_LABEL_PAN_DIMENSION);
		this.infoLabel = new JLabel(NOTHING_YET);
		this.infoLabel.setFont(FONT);

		this.infoLabelPan.add(this.infoLabel);
		this.infoPan.add(this.infoLabelPan);
	}

	public void initContentPan() {
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel content = new JPanel();
		content.setBackground(Color.WHITE);
		content.setLayout(new GridBagLayout());

		gbc.insets = INSET_BIG;
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
			} else {
				filePathRead.setText(controler.getPath());
			}
		}
	}

	public String getInfo() {
		if (cryptKey.getText().equals(EMPTY)) {
			this.infoLabelPan.setBackground(SOFT_RED);
			return MDP_EMPTY;
		} else if (name.getText().equals(EMPTY)) {
			this.infoLabelPan.setBackground(SOFT_RED);
			return NAME_EMPTY;
		} else if (!controler.getAllSelected() && block.getText().equals(EMPTY)) {
			this.infoLabelPan.setBackground(SOFT_RED);
			return BLOCK_EMPTY;
		}
		this.infoLabelPan.setBackground(Color.WHITE);
		return NOTHING_YET;
	}

	public void crypt() {
		String methodeOption = new String(EMPTY);
		String methode = new String(EMPTY);
		ArrayList<Integer> startBlock = new ArrayList<Integer>();
		ArrayList<Integer> stopBlock = new ArrayList<Integer>();
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		;
		if (aes.isSelected()) {
			methodeOption = AES_WITH_OPTION;
			methode = AES;
		} else {
			methode = DES;
			methodeOption = DES_WITH_OPTION;
		}

		infoLabel.setText(getInfo());
		if (infoLabel.getText().equals(NOTHING_YET)) {
			String s = new String(EMPTY);
			infoLabelPan.setBackground(SOFT_GREEN);

			if (controler.getAllSelected()) {
				try {
					s = controler.readAndCrypt(controler.getPath(), controler.constructKey(cryptKey.getText(), methode),
							methodeOption);
					infoLabel.setText(CRYPTED);
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				blocks = controler.getBlocks(block.getText());

				for (int i = 0; i < blocks.size() - 1; i += 2) {
					startBlock.add(blocks.get(i));
					stopBlock.add(blocks.get(i + 1));
				}
				try {

					s = controler.readAndCryptParts(controler.getPath(), startBlock, stopBlock,
							controler.constructKey(cryptKey.getText(), methode), methodeOption);
					infoLabel.setText(CRYPTED);
				} catch (InvalidKeyException | NumberFormatException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException e1) {
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
	class CryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selectFile(e);
			crypt();
		}
	}

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
				} else {
					filePathRead.setText(controler.getPath());
				}
			}
			String toShow = new String(EMPTY);
			String methode = new String(EMPTY);

			try {
				if (controler.readFirstLine(controler.getPath()).contains(AES)) {
					methode = AES;
				} else {
					methode = DES;
				}
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			try {
				toShow = controler.readWithKey(controler.getPath(), controler.constructKey(readKey.getText(), methode));
				infoLabel.setText(READ_WITH);

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | IOException | InvalidKeySpecException e2) {
				// TODO Auto-generated catch block
				// e2.printStackTrace();

				try {
					toShow = controler.readWithouthKey(controler.getPath());
					infoLabel.setText(READ_WITHOUT);


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
			this.block.setText(EMPTY);
			this.name.setText(EMPTY);
		} else if (whatToUpdate.equals(ALL_READ_EMPTY)) {
			this.readKey.setText(EMPTY);
			this.filePathRead.setText(EMPTY);
		}
		this.block.setEditable(!this.controler.getAllSelected());

	}
}
