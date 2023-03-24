package kr.aipeppers.pep.core.util.crypto;

import java.util.Base64;

import kr.aipeppers.pep.core.util.EncKeyUtil;

public class AutoCrypto {

	/**
	 * @Method Name : aesDecrypt
	 * @param value
	 * @return
	 */
	public static String aesDecrypt(String value) {
		String AesKey = EncKeyUtil.getEncKey("CRYPT_PASS");
		String AesIV = EncKeyUtil.getEncKey("CRYPT_IV");
		return 	AesBasisUtil.decryptString(value, AesKey, AesIV);
	}

	/**
	 * @Method Name : aesEncrypt
	 * @param value
	 * @return
	 */
	public static String aesEncrypt(String value) {
		String AesKey = EncKeyUtil.getEncKey("CRYPT_PASS");
		String AesIV = EncKeyUtil.getEncKey("CRYPT_IV");
		return 	AesBasisUtil.encryptString(value, AesKey, AesIV);
	}

	/**
	 * @Method Name : seedEncrypt
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String seedEncrypt(String value) throws Exception {
		String[] SeedKey = null;
		String[] SeedIV = null;
		SeedKey = EncKeyUtil.getEncKey("G_BSZUSER_KEY").split(",");
		SeedIV = EncKeyUtil.getEncKey("G_BSZIV").split(",");

		byte[] SeedKeyByte = new byte[SeedKey.length] ;
		byte[] SeedIVByte = new byte[SeedIV.length] ;
		//10진수 변환
		for(int i=0 ; i<16; i++) {
			SeedKeyByte[i] = (byte) Integer.parseInt(SeedKey[i], 16);
			SeedIVByte[i] = (byte) Integer.parseInt(SeedIV[i], 16);
		}

		//byte 배열에 넣어줌
		byte[] text = new byte[value.length()];
		for(int i =0 ;i<value.length();i++ ) {
			char ch = value.charAt(i);
			int num = (int) ch;
			text[i] = (byte) num;
		}
		//seed 인코딩 후
		byte[] seedEncodeData = SeedUtil.encrypt(text , SeedKeyByte ,SeedIVByte , null);

		Base64.Encoder encoder = Base64.getEncoder() ;
		byte[] encArr = encoder.encode(seedEncodeData);
		String encTxt = new String(encArr , "utf-8");
		return encTxt;
	}

	/**
	 * @Method Name : seedDecrypt
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String seedDecrypt(String value) throws Exception {
		String[] SeedKey = null;
		String[] SeedIV = null;
		SeedKey = EncKeyUtil.getEncKey("G_BSZUSER_KEY").split(",");
		SeedIV = EncKeyUtil.getEncKey("G_BSZIV").split(",");

		byte[] SeedKeyByte = new byte[SeedKey.length] ;
		byte[] SeedIVByte = new byte[SeedIV.length] ;
		//10진수 변환
		for(int i=0 ; i<16; i++) {
			SeedKeyByte[i] = (byte) Integer.parseInt(SeedKey[i], 16);
			SeedIVByte[i] = (byte) Integer.parseInt(SeedIV[i], 16);
		}

		//byte 배열에 넣어줌
		byte[] text = new byte[value.length()];
		for(int i =0 ;i<value.length();i++ ) {
			char ch = value.charAt(i);
			int num = (int) ch;
			text[i] = (byte) num;
		}
		//seed 인코딩 후
		byte[] seedEncodeData = SeedUtil.encrypt(text , SeedKeyByte ,SeedIVByte , null);

		Base64.Encoder encoder = Base64.getEncoder() ;
		byte[] encArr = encoder.encode(seedEncodeData);
		String encTxt = new String(encArr , "utf-8");

		return encTxt;
	}
}