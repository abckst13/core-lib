package kr.aipeppers.pep.core.util.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.stream.CryptoInputStream;
import org.apache.commons.crypto.stream.CryptoOutputStream;
import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.util.ConfigUtil;

@Component
public class FileAesUtil {

	private static final String ALG = "AES/CBC/PKCS5Padding";
	public static final int DEFAULT_BUFFER_SIZE = 8192;

	// 참고해서 완성 할것 : https://commons.apache.org/proper/commons-crypto/userguide.html
	/**
	 * AES encrypt
	 *
	 * @param keyStr
	 * @param is
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public static CryptoOutputStream encrypt(String keyStr, InputStream is, OutputStream os) throws IOException {
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes(keyStr), "AES");
		final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes(ConfigUtil.getString("server-file.aes-iv")));
		final Properties properties = new Properties();

		try (CryptoOutputStream cos = new CryptoOutputStream(ALG, properties, os, key, iv)) {
			byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = is.read(buffer)) >= 0) {
				cos.write(buffer, 0, length);
			}

			cos.flush();
		}

		return new CryptoOutputStream(ALG, properties, os, key, iv);
	}

	/**
	 * AES decrypt
	 *
	 * @param keyStr
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public static void decrypt(String keyStr, InputStream is, OutputStream os) throws IOException {
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes(keyStr), "AES");
		final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes(ConfigUtil.getString("server-file.aes-iv")));
		final Properties properties = new Properties();

		try (CryptoInputStream cis = new CryptoInputStream(ALG, properties, is, key, iv)) {
			byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = cis.read(buffer)) >= 0) {
				os.write(buffer, 0, length);
			}

			os.flush();
		}

		return;
	}

	private static byte[] getUTF8Bytes(final String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}
}
