package kr.aipeppers.pep.core.util.crypto;

import java.util.Base64;

import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.util.EncKeyUtil;

public class AutoCrypto {
	/**
	 * @Method Name : encoding
	 * @param box
	 * @return
	 * @throws Exception
	 */
	public static Box encrypt(Box box) throws Exception {
		Box resBox = new Box();
		String[] SeedKey = null;
		String[] SeedIV = null;
		//api 데이터 나눔
		String AesKey = EncKeyUtil.getEncKey("CRYPT_PASS");
		String AesIV = EncKeyUtil.getEncKey("CRYPT_IV");
		SeedKey = EncKeyUtil.getEncKey("G_BSZUSER_KEY").split(",");
		SeedIV = EncKeyUtil.getEncKey("G_BSZIV").split(",");

		byte[] SeedKeyByte = new byte[SeedKey.length] ;
		byte[] SeedIVByte = new byte[SeedIV.length] ;
		//10진수 변환
		for(int i=0 ; i<16; i++) {
			SeedKeyByte[i] = (byte) Integer.parseInt(SeedKey[i], 16);
			SeedIVByte[i] = (byte) Integer.parseInt(SeedIV[i], 16);
		}

		/* 테스트를 위한 주석
		   //AesUtil 인코딩 ( 테스트를 위해서 - 프론트에서 인코딩해주는 데이터 )
		 * String email = AesUtil.encrypt(box.get("email").toString(), AesKey ,AesIV );
		 * String phone = AesUtil.encrypt(box.get("phone").toString(), AesKey ,AesIV);
		 * String username = AesUtil.encrypt(box.get("username").toString(),AesKey ,AesIV );
		 * byte[] password = DigestUtil.sha256(box.nvl("password").getBytes()); // //AesUtil 디코딩 후 //
		 * String emailD = AesUtil.decrypt(email, AesKey ,AesIV );
		 * String phoneD = AesUtil.decrypt(phone, AesKey ,AesIV );
		 * String passwordD = password.toString();
		 */

		//AesUtil 디코딩 후
//		String emailD = AesUtil.decrypt(box.get("email").toString(), AesKey ,AesIV );
//		String phoneD = AesUtil.decrypt(box.get("phone").toString(), AesKey ,AesIV );
//		byte[] password = DigestUtil.sha256(box.nvl("password").getBytes());// password encrypt

		String emailD = AesBasisUtil.decryptString(box.nvl("email"), AesKey, AesIV);
		String phoneD = AesBasisUtil.decryptString(box.nvl("phone"), AesKey, AesIV);
		String passwordD = DigestUtil.sha256ToStr(box.nvl("password"));// password encrypt

		Box aesDeBox = new Box();
		aesDeBox.put("email", emailD);
		aesDeBox.put("phone", phoneD);
		aesDeBox.put("password", passwordD);

		if(aesDeBox.containsKey("email")){
			//byte 배열에 넣어줌
			byte[] text = new byte[aesDeBox.nvl("email").length()];
			for(int i =0 ;i<aesDeBox.nvl("email").length();i++ ) {
				char ch = aesDeBox.nvl("email").charAt(i);
				int num = (int) ch;
				text[i] = (byte) num;
			}
			//seed 인코딩 후
			byte[] seedEncodeData = SeedUtil.encrypt(text , SeedKeyByte ,SeedIVByte , null);

			Base64.Encoder encoder = Base64.getEncoder() ;
			byte[] encArr = encoder.encode(seedEncodeData);
			String encTxt = new String(encArr , "utf-8");

			resBox.put("email", encTxt);
		}
		if(aesDeBox.containsKey("phone")){
			//byte 배열에 넣어줌
			byte[] text = new byte[emailD.length()];
			for(int i =0 ;i<aesDeBox.nvl("phone").length();i++ ) {
				char ch = aesDeBox.nvl("phone").charAt(i);
				int num = (int) ch;
				text[i] = (byte) num;
			}
			//seed 인코딩 후
			byte[] seedEncodeData = SeedUtil.encrypt(text , SeedKeyByte ,SeedIVByte , null);

			Base64.Encoder encoder = Base64.getEncoder() ;
			byte[] encArr = encoder.encode(seedEncodeData);
			String encTxt = new String(encArr , "utf-8");

			resBox.put("phone", encTxt);
		}
		if(aesDeBox.containsKey("password")){
			//byte 배열에 넣어줌
			byte[] text = new byte[aesDeBox.nvl("password").length()];
			for(int i =0 ;i<aesDeBox.nvl("password").length();i++ ) {
				char ch = aesDeBox.nvl("password").charAt(i);
				int num = (int) ch;
				text[i] = (byte) num;
			}
			//seed 인코딩 후
			byte[] seedEncodeData = SeedUtil.encrypt(text , SeedKeyByte ,SeedIVByte , null);
			Base64.Encoder encoder = Base64.getEncoder() ;
			byte[] encArr = encoder.encode(seedEncodeData);
			String encTxt = new String(encArr , "utf-8");

			resBox.put("password", encTxt);
		}
		return resBox ;
	}
}