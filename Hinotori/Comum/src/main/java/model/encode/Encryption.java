package model.encode;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encryption {
	
	public static String encrypt(final String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, Exception {
		if (text.equals("")) {
			return null;
		}

		SecretKey skey;
		KeySpec ks;
		PBEParameterSpec ps;
		String algorithm;
		Cipher cipher;

		SecretKeyFactory skf;

		algorithm = "PBEWithMD5AndDES";
		skf = SecretKeyFactory.getInstance(algorithm);
		cipher = Cipher.getInstance(algorithm);

		ps = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

		ks = new PBEKeySpec("XTAGeEen3/m8/YkO".toCharArray());
		skey = skf.generateSecret(ks);

		cipher.init(Cipher.ENCRYPT_MODE, skey, ps);

		String retorno = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
		
		return retorno;
	}

	public static String decrypt(final String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		SecretKey skey;
		KeySpec ks;
		PBEParameterSpec ps;
		String algorithm;
		Cipher cipher;
		SecretKeyFactory skf;
		String retorno = "";

		algorithm = "PBEWithMD5AndDES";
		skf = SecretKeyFactory.getInstance(algorithm);

		
		byte[] asBytes = Base64.getDecoder().decode(text);
		
		cipher = Cipher.getInstance(algorithm);
		ks = new PBEKeySpec("XTAGeEen3/m8/YkO".toCharArray());
		skey = skf.generateSecret(ks);

		ps = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

		cipher.init(Cipher.DECRYPT_MODE, skey, ps);
		try {
			retorno = new String(cipher.doFinal(asBytes));
		} catch (Exception e) {
			System.err.println(e);
		}

		return retorno;
	}
	
	public static String decodifica(String texto) {
		try {
			return texto = decrypt(texto);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
		return "";
	}
	
	public static String codifica(String texto) {
		try {
			return texto = encrypt(texto);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
