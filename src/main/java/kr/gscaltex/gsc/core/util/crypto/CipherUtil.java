package kr.gscaltex.gsc.core.util.crypto;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.exception.BaseException;


public class CipherUtil {

	/**
	 * Creates the secret key.
	 *
	 * @param algorithm the algorithm
	 * @param keyByte the key byte
	 * @return the key
	 */
	private static Key generateKey(String algorithm, byte[] keyByte) {
		try {
			if ("DES".equals(algorithm)) {
				KeySpec keySpec = new DESKeySpec(keyByte);
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
				SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
				return secretKey;
			} else if ("DESede".equals(algorithm) || "TripleDES".equals(algorithm) ) {
				KeySpec keySpec = new DESedeKeySpec(keyByte);
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
				SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
				return secretKey;
			} else {
				SecretKeySpec keySpec = new SecretKeySpec(keyByte, algorithm);
				return keySpec;
			}
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * Generate key.
	 *
	 * @param algorithm the algorithm
	 * @return the key
	 */
	private static Key generateKey(String algorithm) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			SecretKey key = keyGenerator.generateKey();
			return key;
		} catch (NoSuchAlgorithmException e) {
			throw new BaseException(e);
		}
	}

	/**
	 * Generate key.
	 *
	 * @param cipherConfig the cipher config
	 * @return the key
	 */
	private static Key generateKey(CipherConfig cipherConfig) {
		byte[] key = cipherConfig.getKey();
		String algorithm = cipherConfig.getAlgorithm();
		if(key == null) {
			return generateKey(algorithm);
		} else {
			return generateKey(algorithm, key);
		}
	}

	/**
	 * Encrypt.
	 *
	 * @param src the src
	 * @param cipherConfig the aes config
	 * @return the byte[]
	 */
	public static byte[] encrypt(byte[] src, CipherConfig cipherConfig) {
		try {
			String transformation = cipherConfig.getTransformation();
			Cipher cipher = Cipher.getInstance(transformation);
			boolean isIV = transformation.matches(".*(CBC|CFB|OFB).*");
			if(isIV) {
				byte[] ivParameter = cipherConfig.getIvParameter();
				if(ivParameter == null) {
					ivParameter = cipherConfig.getKey();
					String iv = new String(ivParameter, CmnConst.CHARSET);
					if (iv.length() != 16) {
						ivParameter = iv.substring(0, 16).getBytes(CmnConst.CHARSET);
					}
				}
				cipher.init(Cipher.ENCRYPT_MODE, generateKey(cipherConfig), new IvParameterSpec(ivParameter));
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, generateKey(cipherConfig));
			}
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new BaseException(e);
		}

	}

	/**
	 * Encrypt.
	 *
	 * @param src the src
	 * @param key the key
	 * @param transformation the transformation
	 * @param algorithm the algorithm
	 * @param ivParameter the iv parameter
	 * @return the byte[]
	 */
	public static byte[] encrypt(byte[] src, byte[] key, String transformation, String algorithm, byte[] ivParameter) {
		CipherConfig cipherConfig = new CipherConfig(key, transformation, algorithm, ivParameter);
		return encrypt(src, cipherConfig);
	}

	/**
	 * Encrypt.
	 *
	 * @param src the src
	 * @param key the key
	 * @param transformation the transformation
	 * @param algorithm the algorithm
	 * @return the byte[]
	 */
	public static byte[] encrypt(byte[] src, byte[] key, String transformation, String algorithm) {
		return encrypt(src, key, transformation, algorithm, null);
	}

	/**
	 * Decrypt.
	 *
	 * @param src the src
	 * @param cipherConfig the aes config
	 * @return the byte[]
	 */
	public static byte[] decrypt(byte[] src, CipherConfig cipherConfig) {
		try {
			String transformation = cipherConfig.getTransformation();
			Cipher cipher = Cipher.getInstance(transformation);
			boolean isIV = transformation.matches(".*(CBC|CFB|OFB).*");
			if(isIV) {
				byte[] ivParameter = cipherConfig.getIvParameter();
				if(ivParameter == null) {
					ivParameter = cipherConfig.getKey();
					String iv = new String(ivParameter, CmnConst.CHARSET);
					if (iv.length() != 16) {
						ivParameter = iv.substring(0, 16).getBytes(CmnConst.CHARSET);
					}
				}
				cipher.init(Cipher.DECRYPT_MODE, generateKey(cipherConfig), new IvParameterSpec(ivParameter));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, generateKey(cipherConfig));
			}
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * Decrypt.
	 *
	 * @param src the src
	 * @param key the key
	 * @param transformation the transformation
	 * @param algorithm the algorithm
	 * @param ivParameter the iv parameter
	 * @return the byte[]
	 */
	public static byte[] decrypt(byte[] src, byte[] key, String transformation, String algorithm, byte[] ivParameter) {
		CipherConfig cipherConfig = new CipherConfig(key, transformation, algorithm, ivParameter);
		return decrypt(src, cipherConfig);
	}

	/**
	 * Decrypt.
	 *
	 * @param src the src
	 * @param key the key
	 * @param transformation the transformation
	 * @param algorithm the algorithm
	 * @return the byte[]
	 */
	public static byte[] decrypt(byte[] src, byte[] key, String transformation, String algorithm) {
		return decrypt(src, key, transformation, algorithm, null);
	}

	/**
	 * @param plainString
	 * @return
	 * @throws Exception
	 */
	public static String getEncryptString(final String plainString, String key,String algorithm,String transformation) throws Exception {
		return new String(getEncrypt(plainString.getBytes(CmnConst.UTF8),key,algorithm,transformation), CmnConst.UTF8);
	}

	/**
	 * @param cipherString
	 * @return
	 * @throws Exception
	 */
	public static String getDecryptString(final String cipherString, String key,String algorithm,String transformation) throws Exception {
		return new String(getDecrypt(cipherString.getBytes(CmnConst.UTF8),key,algorithm,transformation), CmnConst.UTF8);
	}

	/**
	 * @param plainBytes,key,algorithm,transformation
	 * @return
	 * @throws Exception
	 */
	public static byte[] getEncrypt(byte[] plainBytes, String key,String algorithm,String transformation) throws Exception {
		byte[] cipherBytes = null;
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), algorithm);
		Cipher cipher = Cipher.getInstance(transformation);
		//initial vector값은 key의 16자리까지로 사용
		String iv = key.substring(0,16);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
		cipherBytes = Base64.encodeBase64(cipher.doFinal(plainBytes));
		return cipherBytes;
	}

	/**
	 * @param cipherBytes,key,algorithm,transformation
	 * @return plainBytes
	 * @throws Exception
	 */
	public static byte[] getDecrypt(byte[] cipherBytes, String key,String algorithm,String transformation) throws Exception {
		byte[] plainBytes = null;
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), algorithm);
		Cipher cipher = Cipher.getInstance(transformation);
		//initial vector값은 key의 16자리까지로 사용
		String iv = key.substring(0,16);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
		plainBytes = cipher.doFinal(Base64.decodeBase64(cipherBytes));
		return plainBytes;
	}

}
