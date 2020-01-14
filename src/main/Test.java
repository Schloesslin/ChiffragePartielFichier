package main;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String message = "Mon message a traiter";

	    KeyGenerator keyGen;
	    try {
	      keyGen = KeyGenerator.getInstance("AES");
	      keyGen.init(256);
	      SecretKey cle = keyGen.generateKey();
	      System.out.println("cle : " + new String(cle.getEncoded()));

	      byte[] enc = encrypter(message, cle);
	      System.out.println("texte encrypte : " + new String(enc));

	      String dec = decrypter(enc, cle);
	      System.out.println("texte decrypte : " + dec);

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
	}
	
	public static byte[] encrypter(final String message, SecretKey cle)
		      throws NoSuchAlgorithmException, NoSuchPaddingException,
		      InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.ENCRYPT_MODE, cle);
		    byte[] donnees = message.getBytes();

		    return cipher.doFinal(donnees);
		  }

		  public static String decrypter(final byte[] donnees, SecretKey cle)
		      throws NoSuchAlgorithmException, NoSuchPaddingException,
		      InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.DECRYPT_MODE, cle);

		    return new String(cipher.doFinal(donnees));
		  }

}
