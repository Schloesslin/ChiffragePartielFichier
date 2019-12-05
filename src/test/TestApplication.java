package test;

import static org.junit.Assert.assertTrue;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

import controler.AbstractControler;
import controler.Controler;
import model.AbstractModel;
import model.Model;
import view.View;

public class TestApplication {

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
	*/
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
	
	@Test
	public void testGetFolderPath() {
		Model model = new Model();
		Controler controler = new Controler(model);
		String filePathTest = new String("Document/test/doc.txt");
		assertTrue("", controler.getFolderPath(filePathTest).equals("Document/test/"));
	}
	
	@Test
	public void testConstructKey() {
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
	}
	*/
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
}
