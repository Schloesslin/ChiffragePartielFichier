package view;

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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.AbstractControler;
import model.Observer;

public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AbstractControler controler;
	private JPanel panelPrincipal = new JPanel();
	private JButton crypt, decrypt;
	private JTextField start, stop, cryptKey, decryptKey;

	public View(AbstractControler controler) {
		this.controler = controler;
		this.setTitle("Crypto");
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initFrame();
		this.setContentPane(this.panelPrincipal);
		this.setVisible(true);
	}

	public void initFrame() {
		
		DecryptButtonListener decryptButtonListener = new DecryptButtonListener();
		CryptButtonListener cryptButtonListener = new CryptButtonListener();
		
		JPanel cryptPan = new JPanel();
		this.cryptKey = new JTextField();
		this.start = new JTextField();
		this.stop = new JTextField();
		this.crypt = new JButton("Crypt File ...");
		this.cryptKey.setColumns(8);
		this.start.setColumns(5);
		this.stop.setColumns(5);
		this.crypt.addActionListener(cryptButtonListener);

		cryptPan.add(this.start);
		cryptPan.add(this.stop);
		cryptPan.add(this.cryptKey);
		cryptPan.add(this.crypt);
		this.panelPrincipal.add(cryptPan);
		
		JPanel decryptPan = new JPanel();
		this.decryptKey = new JTextField();
		this.decryptKey.setColumns(8);
		this.decrypt = new JButton("Decrypt ...");
		this.decrypt.addActionListener(decryptButtonListener);
		
		decryptPan.add(this.decryptKey);
		decryptPan.add(this.decrypt);
		this.panelPrincipal.add(decryptPan);
	}
	
	public Frame getFrame() {
		return this;
	}

	class CryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(getFrame());
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controler.setPath( chooser.getSelectedFile().getPath());
				int startVal = Integer.parseInt(start.getText());
				int stopVal = Integer.parseInt(stop.getText());
				String s = controler.readFile(controler.getPath(), startVal, stopVal);
				try {
					controler.writeCryptedFile("DocToCrypt", s,cryptKey.getText());
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	class DecryptButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				controler.setPath( chooser.getSelectedFile().getPath());
				//int startVal = Integer.parseInt(this.start.getText());
				//int stopVal = Integer.parseInt(this.stop.getText());
				String s = controler.readFile(controler.getPath(), 1, 12);
				try {
					controler.writeDecryptedFile("DocDecrypt", s, decryptKey.getText());
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
