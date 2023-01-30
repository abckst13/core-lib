package kr.aipeppers.pep.core.util.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FileEncUtil {
	private static Algorithms algorithm = Algorithms.AES;

	//사용할 암호화 알고리즘
	//private static final String hashAlgorithm = "sha-256";
	private static BlockMode blockmode = BlockMode.ECB;
	private static Padding padding = Padding.PKCS5Padding;
	private static Hash hashalgorithm = Hash.SHA_256;

	//사용할 해시 알고리즘
	//private static final String transformation = algorithm + "/CBC/PKCS5Padding";
	//사용할 블럭 운영 방식
	private static int keysize = 16; // 키의 크기
	private static int ivsize = 16; // 키의 크기
	private static final int BUFFSIZE = 4096; // 버퍼의 크기
	private static final String SUCCESS = "Success";
	private static final String FAIL = "Fail";

	/**
	 * 사용할수 있는 암호화 알고리즘들
	 * @author Malloc
	 *
	 */
	public enum Algorithms {
		AES, DES, DESede
	}

	/**
	 * 사용할수 있는 블럭 운영모드
	 * @author Malloc
	 *
	 */
	public enum BlockMode {
		ECB, CBC, CFB, OFB, CTR
	}

	/**
	 * 사용할수 있는 패딩옵션
	 * @author Malloc
	 *
	 */
	public enum Padding {
		PKCS5Padding, ISO10126Padding// ,NoPadding, PKCS1Padding,
	}

	/**
	 * 사용할수 있는 해시 알고리즘
	 * @author Malloc
	 */
	public enum Hash {
		SHA_1("SHA-1"), SHA_256("SHA-256"), SHA_512("SHA-512"), MD5("MD5"), MD2("MD2");
		String algorithm;
		private Hash(String alg) {
			algorithm = alg;
		}

		@Override
		public String toString() {
			return algorithm;
		}
	}

	/**
	 * 문자열을 이용해서 키를 생성한다.<br>
	 * SHA-256으로 해시 한다음 키의 크기만큼 잘라서 쓴다.
	 * @param key key를 생성할 문자열
	 * @return {@link Key}
	 */
	private static Key getKey(String key) {
		MessageDigest hash;
		try {
			hash = MessageDigest.getInstance(Hash.SHA_256.toString());
		} catch (NoSuchAlgorithmException e) {
			log.error("error : {}", e);
			return null;
		}

		switch (algorithm) {
		case AES:
			break;
		case DES:
			break;
		case DESede:
			break;
		}

		//password 스트링을 해쉬 알고리즘으로 해쉬 한뒤 키에 필요한 만큼 잘라서 사용
		return new SecretKeySpec(hash.digest(key.getBytes()), 0, keysize, algorithm.toString());
	}

	/**
	 * @param iv iv를 생성할 문자열
	 * @return {@link IvParameterSpec}
	 */
	private static IvParameterSpec getIV(String iv) {
		MessageDigest hash;
		try {
			hash = MessageDigest.getInstance(Hash.SHA_256.toString());
		} catch (NoSuchAlgorithmException e) {
			log.error("error : {}", e);
			return null;
		}
		//Initial Vector 또한 String 으로부터 해쉬한뒤 필요한 만큼 잘라서 사용
		return new IvParameterSpec(hash.digest(iv.getBytes()), 0, ivsize);
	}

	/**
	 * 변환 형식을 지정한다.<br>
	 * Cipher.getInstance(getTransFormation()) 형식으로 이용
	 * @return
	 *         현재 설정되어있는 algorithm과 blockmode, padding 을 이용해서 반환한다.
	 */
	private static String getTransFormation() {
		return algorithm + "/" + blockmode + "/" + padding;
	}

	/**
	 * 기본값 Algorithms.AES
	 * @param algorithm {@link Algorithms} 을 참조해서 암호화 알고리즘을 설정한다.
	 */
	public static void setAlgorithms(FileEncUtil.Algorithms algorithm) {
		FileEncUtil.algorithm = algorithm;
		switch (algorithm) {
		case AES:
			keysize = 16;
			ivsize = 16;
			break;
		case DES:
			keysize = 8;
			ivsize = 8;
			break;
		case DESede:
			keysize = 24;
			ivsize = 8;
		default:
			break;
		}
	}

	/**
	 * 기본값은 BlockMode.ECB
	 * @param blockmode {@link BlockMode} 를 참조해서 블럭모드를 설정한다.
	 */
	public static void setBlockMode(FileEncUtil.BlockMode blockmode) {
		FileEncUtil.blockmode = blockmode;
	}

	/**
	 * 기본값은 Padding.PKCS5Padding
	 * @param padding {@link Padding}을 참조해서 패딩옵션을 설정한다.
	 */
	public static void setPadding(FileEncUtil.Padding padding) {
		FileEncUtil.padding = padding;
	}

	/**
	 * 기본값은 Hash.SHA_256
	 * 이것과 무관하게 키를 생성할때는 SHA-256을 사용한다.
	 * @param hash {@link Hash}를 참조해서 해쉬 알고리즘을 설정한다.
	 */
	public static void setHashAlgorithm(FileEncUtil.Hash hash) {
		FileEncUtil.hashalgorithm = hash;
	}

	/**
	 * 설정된 모드를 통해서 암호화 한다.<br>
	 * 전달받은 File src를 읽어서 파일명-enc 의 파일로 출력한다.
	 *
	 * @param src      encode 할 파일
	 * @param password key
	 * @param iv       initial vector
	 * @return 현재 설정된 해시를 이용한 hashcode
	 */
	@SuppressWarnings({ "unused" })
	public static String encode(File src, String password, String iv) {
		MessageDigest hash = null;
		try {
			String path = src.getParent();
			String name = src.getName();
			int extIndex = name.lastIndexOf('.');
			String ext;
			if (extIndex > 0) {
				ext = name.substring(extIndex);
				name = name.substring(0, extIndex);
			} else {
				ext = "";
			}
			File dst = new File(path, name + "-enc" + ext);
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
			//파일로부터 읽어오는 input stream
			BASE64EncoderStream out = new BASE64EncoderStream(new BufferedOutputStream(new FileOutputStream(dst)));
			//byte배열을 Base64로 인코딩 한뒤에 출력해주는 output stream
			Cipher cipher = Cipher.getInstance(getTransFormation());
			//cipher에 AES/CTR/PKCS5Padding 로 설정한다.
			//AES 알고리즘을 사용하고 CTR 블럭 운영을 하며 마지막에 블럭이 남는주분은 PKCS5Padding 방식으로 패딩한다.
			Key key = getKey(password);
			if (blockmode == BlockMode.ECB) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} else {
				IvParameterSpec ivSpec = getIV(iv);
				cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			} // 암호화 모드로 설정하고, key 와 initial vector을 설정한다.

			int length;
			byte result[];
			byte buffer[] = new byte[BUFFSIZE];
			hash = MessageDigest.getInstance(hashalgorithm.toString()); // 해시 알고리즘 설정.
			while ((length = in.read(buffer)) >= 0) { // 파일을 끝까지 읽는다.
				result = cipher.update(buffer, 0, length); // 읽은 내용을 암호화 해서 저장한다.
				hash.update(result); // 암호문을 해쉬한다.
				out.write(result); // 암호문을 base64로 저장한다.
			}

			result = cipher.doFinal();
			hash.update(result); // 암호문을 해쉬한다.
			out.write(result); // 암호문을 base64로 저장한다.
			out.flush();
			out.close();
			in.close();

		} catch (Exception e) {
			log.error("error : {}", e);
			return FAIL;
		}

		if (hash != null)
			return Base64.encodeBase64String(hash.digest());
		else
			return FAIL;
	}

	/**
	 * 설정된 모드를 통해서 복호화 한다.<br>
	 * 전달받은 File src를 읽어서 파일명에 -enc가 있다면 -dec로 바꾸고
	 * 없다면 파일명-dec 로 출력한다.
	 *
	 * @param src      decode 할 파일
	 * @param password key
	 * @param iv       initial vector
	 * @param hashcode sha-256 hash code
	 * @return 디코딩이 성공하면 "Success" 실패하면 "Fail"이 출력된다.
	 *
	 */

	public static String decode(File src, String password, String iv, String hashcode) {
		MessageDigest hash = null;
		File dst = null;
		try {
			int length = 0;
			String path = src.getParent();
			String name = src.getName();
			int extIndex = name.lastIndexOf('.');
			String ext;
			if (extIndex > 0) {
				ext = name.substring(extIndex);
				name = name.substring(0, extIndex);
			} else {
				ext = "";
			}
			name = name.replace("-enc", "");
			dst = new File(path, name + "-dec" + ext);
			BASE64DecoderStream in = new BASE64DecoderStream(new BufferedInputStream(new FileInputStream(src)));
			//Base64로 인코딩 된 파일을 읽어서 byte 배열로 변환해주는 input stream
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst));
			//byte 배열을 출력하는 output stream
			Cipher cipher = Cipher.getInstance(getTransFormation());
			//cipher에 AES/CTR/PKCS5Padding 의 형식으로 설정한다.
			Key key = getKey(password);
			if (blockmode == BlockMode.ECB) {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} else {
				IvParameterSpec ivSpec = getIV(iv);
				cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			}

			//복호화 모드로 설정하고, key 와 initial vector을 설정한다.
			hash = MessageDigest.getInstance(hashalgorithm.toString());// 해시 알고리즘 설정.
			byte result[];
			byte buffer[] = new byte[BUFFSIZE];
			while ((length = in.read(buffer)) >= 0) { // 파일을 끝까지 읽는다.
				hash.update(buffer, 0, length); // 암호문을 해쉬한다.
				result = cipher.update(buffer, 0, length); // 복호화한다.
				out.write(result); // 평문을 저장한다.
			}

			//해쉬는 while 문에서 암호문을 전부 읽었기 때문에 끝났음.
			result = cipher.doFinal(); // 남아있는 암호문을 복호화한다.
			out.write(result); // 평문을 base64로 저장한다.
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			log.error("error : {}", e);
			return FAIL;
		}

		if (hash != null && hashcode != null) {
			if (hashcode.equals(Base64.encodeBase64String(hash.digest())))
				return SUCCESS;
			if (dst != null)
				dst.delete();
		}
		return FAIL;
	}

//	public static void main(String[] args) {
//		File src = new File("c:/dev/keycloak-tomcat-adapter-dist-10.0.2.zip");
//		File dst = new File("c:/dev/keycloak-tomcat-adapter-dist-10.0.2-enc.zip");
//		String key = "eb6c6f3ec827422e";
//		String iv = "2fd16e0a9453468d";
//		String hash = encode(src, key, iv);
//		System.out.println(decode(dst, key, iv, hash));
//	}

}
