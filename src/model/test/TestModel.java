package model.test;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import model.Model;

public class TestModel {

	
	@Test
	public void testCryptFile() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		model.cryptFile("TestName", "TestContenu", "TestPass");
		String testContenuCrypted = model.cryptText("TestContenu", model.constructKey("TestPass", "AES"));
		assertTrue("", model.readAllFile("TestName").equals(testContenuCrypted));
	}
	
	@Test
	public void testDecryptFile() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Model model = new Model();
		model.cryptFile("TestName", "TestContenu", "TestPass");
		String testContenuCrypted = model.readAllFile("TestName");
		model.decryptFile("TestNameDecrypt", testContenuCrypted, "TestPass");
		assertTrue("",model.readAllFile("TestNameDecrypt").equals("TestContenu"));
	}
	
	@Test
	public void testCryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Model model = new Model();
		Key key = model.constructKey("Test", "AES");
		String contenuTest = new String("Test");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(contenuTest.getBytes());
		byte[] byteEncryptedBase64 = Base64.getEncoder().encode(byteEncrypted);
		String textEncryptedBase64 = new String(byteEncryptedBase64);
		assertTrue("", textEncryptedBase64.equals(model.cryptText(contenuTest, key)));
	}
	
	@Test
	public void testConstructKey() {
		Model model = new Model();
		byte[] bytePassTest = new byte[32];
		String passwoardTest = new String("Test");
		String methodeTest = new String("AES");
		bytePassTest[0] = passwoardTest.getBytes()[0];
		bytePassTest[1] = passwoardTest.getBytes()[1];
		bytePassTest[2] = passwoardTest.getBytes()[2];
		bytePassTest[3] = passwoardTest.getBytes()[3];
		Key keyTest = new SecretKeySpec(bytePassTest, methodeTest);
		assertTrue("", keyTest.equals(model.constructKey(passwoardTest, methodeTest)));
	}
	
	@Test
	public void testReadFile() throws IOException {
		Model model = new Model();
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
		model.writeFile("testFile2", contenu);
		assertTrue("", model.readFile("testFile2", 10, 30).equals(contenuToRead));
	}
	
	@Test
	public void testReadAllFile() throws IOException {
		Model model = new Model();
		String contenu = new String("");
		for (int i=0; i<50; i++) {
			contenu+=Integer.toString(i)+"\n";
		}
		contenu = contenu.trim();
		model.writeFile("testFile1", contenu);
		assertTrue("", model.readAllFile("testFile1").equals(contenu));
	}
	
	@Test(expected = IOException.class)
	public void testReadAllFileException() throws IOException {
		Model model = new Model();
		model.readAllFile("");
	}
	
	@Test
	public void testSetPath() {
		Model model = new Model();
		String pathTest = "Test";
		model.setPath(pathTest);
		assertTrue("", model.getPath().equals(pathTest));
	}
	
	@Test
	public void testToggleAllSelected() {
		Model model = new Model();
		boolean initAllSelected = model.getAllSelected();
		model.toggleAllSelected();
		assertTrue("", initAllSelected == !model.getAllSelected());
		model.toggleAllSelected();
		assertTrue("", model.getAllSelected() == initAllSelected);
	}
	

}
