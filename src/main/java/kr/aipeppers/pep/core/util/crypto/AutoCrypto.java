package kr.aipeppers.pep.core.util.crypto;

import kr.aipeppers.pep.core.util.EncKeyUtil;
import kr.aipeppers.pep.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		return Base64Util.encodeString(SeedUtil.encrypt(value.getBytes(), StringUtil.bstrToByte(EncKeyUtil.getEncKey("G_BSZUSER_KEY")), StringUtil.bstrToByte(EncKeyUtil.getEncKey("G_BSZIV")), null));
	}

	/**
	 * @Method Name : seedDecrypt
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String seedDecrypt(String value) throws Exception {
		return SeedUtil.decryptString(Base64Util.decode(value), StringUtil.bstrToByte(EncKeyUtil.getEncKey("G_BSZUSER_KEY")), StringUtil.bstrToByte(EncKeyUtil.getEncKey("G_BSZIV")), null);
	}
}