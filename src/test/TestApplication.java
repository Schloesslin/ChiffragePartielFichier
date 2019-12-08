package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

import controler.AbstractControler;
import controler.Controler;
import model.AbstractModel;
import model.Model;
import view.View;

public class TestApplication {

	private static String CRYPT_PART = new String("Crypted Part");
	private static String END_CRYPT_PART = new String("End Crypted Part");
	private static String JUMP_LINE = new String("\n");
	private static String EMPTY = new String("");

	@Test
	public void testAddObserver() {
		AbstractModel model = new Model();
		AbstractControler controler = new Controler(model);
		View view = new View(controler);
		model.addObserver(view);
		assertTrue(model.getObservers().size() == 1);
	}
	
	@Test
	public void testRemoveObserver() {
		AbstractModel model = new Model();
		AbstractControler controler = new Controler(model);
		View view = new View(controler);
		model.addObserver(view);
		model.removeObserver();
		assertTrue(model.getObservers().size() == 0);
	}
	
	@Test
	public void testSetPath() {
		Model model = new Model();
		Controler controler = new Controler(model);
		String pathTest = "Test";
		controler.setPath(pathTest);
		assertTrue("", controler.getPath().equals(pathTest));
	}
	
	@Test
	public void testToggleAllSelected() {
		Model model = new Model();
		Controler controler = new Controler(model);
		boolean initAllSelected = controler.getAllSelected();
		controler.toggleAllSelected();
		assertTrue("", initAllSelected == !controler.getAllSelected());
		controler.toggleAllSelected();
		assertTrue("", controler.getAllSelected() == initAllSelected);
	}
	
	@Test
	public void testGetFolderPath() {
		Model model = new Model();
		Controler controler = new Controler(model);
		String filePathTest = new String("Document/test/doc.txt");
		assertTrue("", controler.getFolderPath(filePathTest).equals("Document/test/"));
	}
	
	@Test
	public void testConstructKeyAES() {
		Model model = new Model();
		Controler controler = new Controler(model);
		byte[] bytePassTest = new byte[32];
		String passwoardTest = new String("Test");
		String methodeTest = new String("AES");
		bytePassTest[0] = passwoardTest.getBytes()[0];
		bytePassTest[1] = passwoardTest.getBytes()[1];
		bytePassTest[2] = passwoardTest.getBytes()[2];
		bytePassTest[3] = passwoardTest.getBytes()[3];
		Key keyTest = new SecretKeySpec(bytePassTest, methodeTest);
		assertTrue("", keyTest.equals(controler.constructKey(passwoardTest, methodeTest)));
	}
	
	@Test
	public void testConstructKeyDES() {
		Model model = new Model();
		Controler controler = new Controler(model);
		byte[] bytePassTest = new byte[DESKeySpec.DES_KEY_LEN];
		String passwoardTest = new String("Test");
		String methodeTest = new String("DES");
		bytePassTest[0] = passwoardTest.getBytes()[0];
		bytePassTest[1] = passwoardTest.getBytes()[1];
		bytePassTest[2] = passwoardTest.getBytes()[2];
		bytePassTest[3] = passwoardTest.getBytes()[3];
		Key keyTest = new SecretKeySpec(bytePassTest, methodeTest);
		assertTrue("", keyTest.equals(controler.constructKey(passwoardTest, methodeTest)));
	}
	
	@Test
	public void testCryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		Key key = controler.constructKey("Test", "AES");
		String contenuTest = new String("Test");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenuTest.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);
		assertTrue("", textEncryptedBase64.equals(controler.cryptText(contenuTest, key, "AES")));
	}
	
	@Test
	public void testDecryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		Key key = controler.constructKey("Test", "AES");
		String contenuTest = new String("Test");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenuTest.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);
		
		assertTrue("", controler.decryptText(textEncryptedBase64, key, "AES").equals("Test"));
	}
	
	@Test
	public void testGetBlock() {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		ArrayList<Integer> testBlock = new ArrayList<Integer>();
		testBlock.add(2);
		testBlock.add(9);
		testBlock.add(12);
		testBlock.add(23);
		assertTrue("", controler.getBlocks("2-9 12-23").equals(testBlock));
		
	}
	
	@Test
	public void testReadFirstLine() throws IOException {
		Model model = new Model();
		Controler controler = new Controler(model);

		String contenu = new String("");

		contenu+="AES encryption\n";
		contenu = contenu.trim();
		
		controler.writeFile("testFile2", contenu);
		assertTrue("", controler.readFirstLine(controler.getFolderPath(controler.getPath())+"testFile2.txt").equals("AES encryption"));		
	}
	
	@Test
	public void testReadAndCrypt() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		controler.writeFile("test", contenu);
		
		StringBuffer out = new StringBuffer("");
		StringBuffer toCrypt = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new FileReader(controler.getFolderPath(controler.getPath())+"test.txt"));
		String line = new String("");
		out.append("AES encryption\n");
		Key key = controler.constructKey("TestPass", "AES");
		while ((line = reader.readLine()) != null) {
			toCrypt.append(line + "\n");
		}
		reader.close();
		out.append("Crypted Part\n");
		out.append(controler.cryptText(toCrypt.toString(), key, "AES").trim());
		out.append("\nEnd Crypted Part\n");
	
		assertTrue("", out.toString().equals(controler.readAndCrypt(controler.getFolderPath(controler.getPath())+"test.txt", key, "AES")));
	}
	
	@Test
	public void testReadAndCryptPart() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		controler.writeFile("test1", contenu);
		
		StringBuffer out = new StringBuffer("");
		StringBuffer toCrypt = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new FileReader(controler.getFolderPath(controler.getPath())+"test.txt"));
		String line = new String("");
		boolean cryptPart = false;
		int countLine = 1;
		ArrayList<Integer> start = new ArrayList<Integer>();
		ArrayList<Integer> stop = new ArrayList<Integer>();
		start.add(4);
		stop.add(10);
		out.append("AES encryption\n");
		Key key = controler.constructKey("TestPass", "AES");
		while ((line = reader.readLine()) != null) {
			if (start.contains(countLine)) {
				out.append(CRYPT_PART + JUMP_LINE);
				toCrypt.append(line + JUMP_LINE);
				cryptPart = true;
			}
			else if (stop.contains(countLine)) {
				toCrypt.append(line + JUMP_LINE);
				out.append(controler.cryptText(toCrypt.toString(), key, "AES") + JUMP_LINE);
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
		
		
	
		assertTrue("", out.toString().equals(controler.readAndCryptParts(controler.getFolderPath(controler.getPath())+"test1.txt",start,stop, key, "AES")));
	}
	
	@Test
	public void testReadWithKeyAES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		controler.writeFile("test3", contenu);
		
		Key key = controler.constructKey("TestPass", "AES");
		String s = controler.readAndCrypt(controler.getFolderPath(controler.getPath())+"test3.txt", key, "AES");
		controler.writeFile("test4", s);
				
		assertTrue("",contenu.equals(controler.readWithKey(controler.getFolderPath(controler.getPath())+"test4.txt", key)));
		
	}
	
	@Test
	public void testReadWithKeyDES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		controler.writeFile("test7", contenu);
		
		Key key = controler.constructKey("TestPass", "DES");
		String s = controler.readAndCrypt(controler.getFolderPath(controler.getPath())+"test7.txt", key, "DES");
		controler.writeFile("test8", s);
				
		assertTrue("",contenu.equals(controler.readWithKey(controler.getFolderPath(controler.getPath())+"test8.txt", key)));
		
	}
	
	@Test
	public void testReadWithKey() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		controler.writeFile("test9", contenu);
		ArrayList<Integer> start = new ArrayList<Integer>();
		ArrayList<Integer> stop = new ArrayList<Integer>();
		start.add(4);
		stop.add(10);
		Key key = controler.constructKey("TestPass", "DES");
		String s = controler.readAndCryptParts(controler.getFolderPath(controler.getPath())+"test9.txt",start, stop, key, "DES");
		controler.writeFile("test10", s);
				
		assertTrue("",contenu.equals(controler.readWithKey(controler.getFolderPath(controler.getPath())+"test10.txt", key)));
		
	}
	
	@Test
	public void testReadWithoutKey() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		
		String contenu = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		String contenuToRead = new String("");
		for (int i=1; i<51; i++) {
			if(i<4 || i>10) {
				contenuToRead+=Integer.toString(i)+"\n";
			}
		}
		contenuToRead = contenuToRead.trim();
		
		controler.writeFile("test5", contenu);
		
		ArrayList<Integer> start = new ArrayList<Integer>();
		ArrayList<Integer> stop = new ArrayList<Integer>();
		start.add(4);
		stop.add(10);
		Key key = controler.constructKey("TestPass", "AES");
		String s = controler.readAndCryptParts(controler.getFolderPath(controler.getPath())+"test5.txt",start,stop, key, "AES");
		controler.writeFile("test6", s);
				
		assertTrue("",contenuToRead.equals(controler.readWithouthKey(controler.getFolderPath(controler.getPath())+"test6.txt")));
		
		
		
	}
	
	/*
	@Test
	public void testReadFile() throws IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		String contenu = new String("");
		String contenuToRead = new String("");
		for (int i=1; i<51; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		
		for (int i=10; i<31; i++) {
			contenuToRead+=Integer.toString(i)+"\n";
		}
		contenuToRead = contenuToRead.trim();
		controler.writeFile("testFile2", contenu);
		assertTrue("", controler.readFile("testFile2", 10, 30).equals(contenuToRead));
	}
	
	@Test
	public void testCryptFile() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		controler.cryptFile("TestName", "TestContenu", "TestPass");
		String testContenuCrypted = model.cryptText("TestContenu", model.constructKey("TestPass", "AES"));
		assertTrue("", controler.readAllFile("TestName").equals(testContenuCrypted));
	}
	
	@Test
	public void testDecryptFile() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		controler.cryptFile("TestName", "TestContenu", "TestPass");
		String testContenuCrypted = controler.readAllFile("TestName");
		controler.decryptFile("TestNameDecrypt", testContenuCrypted, "TestPass");
		assertTrue("",controler.readAllFile("TestNameDecrypt").equals("TestContenu"));
	}
	
	@Test
	public void testCryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		Key key = controler.constructKey("Test", "AES");
		String contenuTest = new String("Test");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenuTest.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);
		assertTrue("", textEncryptedBase64.equals(controler.cryptText(contenuTest, key)));
	}
	
	@Test
	public void testDecryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Controler controler = new Controler(model);
		Key key = controler.constructKey("Test", "AES");
		String contenuTest = new String("Test");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenuTest.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);
		
		assertTrue("", controler.decryptText(textEncryptedBase64, key).equals("Test"));
	}
	*/
	
	/*
	@Test
	public void testReadAllFile() throws IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		String contenu = new String("");
		for (int i=0; i<50; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		controler.writeFile("testFile1", contenu);
		assertTrue("", controler.readAllFile("testFile1").equals(contenu));
	}
	
	
	@Test(expected = IOException.class)
	public void testReadAllFileException() throws IOException {
		Model model = new Model();
		Controler controler = new Controler(model);
		controler.readAllFile("");
	}*/
	
}
