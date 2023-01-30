package kr.aipeppers.pep.core.util.crypto;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kr.aipeppers.pep.core.cont.CmnConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AesUtil {
	private static String ALG = "AES/CBC/PKCS5Padding";
	private static final String KEY = "0123456789ABCDEF0123456789ABCDEF";
//	private static final String KEY = Base64Util.decodeString(CmnConst.AES_ENCRYPT_KEY);
	private static final String CHARSET = "UTF-8";

	public static String encrypt(String plainText, String key, String iv) {
		try {
			Cipher cipher = Cipher.getInstance(ALG);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(CHARSET));
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch(Exception e) {
			log.error("error: {}", e);
		}
		return null;
	}

	public static String encrypt(String plainText, String key) {
		return encrypt(plainText, key, key.substring(0, 16));
	}

	public static String encrypt(String plainText) {
		return encrypt(plainText, KEY, KEY.substring(0, 16));
	}

	public static String decrypt(String encryptedText, String key, String iv) {
		try {
			Cipher cipher = Cipher.getInstance(ALG);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(CHARSET));
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

			byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
			byte[] decrypted = cipher.doFinal(decodedBytes);

			return new String(decrypted, CHARSET);
		} catch(Exception e) {
			log.error("error: {}", e);
		}
		return null;
	}

	public static String decrypt(String encryptedText, String key) {
		return decrypt(encryptedText, key, key.substring(0, 16));
	}

	public static String decrypt(String encryptedText) {
		return decrypt(encryptedText, KEY, KEY.substring(0, 16));
	}

	public static void main(String[] args) throws Exception {
		String plainText = "peperdb123";
		String encryptedText = encrypt(plainText);
		log.debug(plainText);				//
		log.debug(encryptedText);			//
		log.debug(decrypt(encryptedText));		//

		log.debug(KEY);
		log.debug(Base64Util.encodeString(KEY.substring(0, 16)));

		log.debug(encrypt("meplz"));
		log.debug(decrypt("o2KCAqvRtY/IPm0KBArEtQ=="));

	}

}