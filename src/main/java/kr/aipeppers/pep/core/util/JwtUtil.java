package kr.aipeppers.pep.core.util;

import java.util.Date;

import org.joda.time.LocalDateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

	/**
	 * 토큰 생성
	 * @param memBox
	 * @return
	 */
	public static String createToken(Box memBox) {
		Box headerBox = new Box();
		headerBox.put("typ", CmnConst.JWT_TYP);
		headerBox.put("alg", CmnConst.JWT_ALG);
		Box claimBox = new Box();
		LocalDateTime ldt = LocalDateTime.now();
		long nowDt = ldt.toDate().getTime();
		long expDt = ldt.plusSeconds(ConfigUtil.getInt("jwt.login.exp-sec")).toDate().getTime();
		claimBox.putAll(memBox);
		claimBox.put("nowDt", nowDt);
		claimBox.put("expDt", expDt);
		claimBox.put("expSec", ConfigUtil.getInt("jwt.login.exp-sec"));

		String jwt = Jwts.builder()
				.setHeader(headerBox)
				.setClaims(claimBox)
				.setExpiration(new Date(expDt))
				.signWith(SignatureAlgorithm.HS256, ConfigUtil.getString("jwt.login.key").getBytes())
				.compact();
//		log.debug("jwtToken:"+jwt);
		return jwt;
	}

	/**
	 * 파일토큰 생성
	 * @param memEtt
	 * @return
	 */
	public static String createFileToken(String fileId) {
		Box headerBox = new Box();
		headerBox.put("typ", CmnConst.JWT_TYP);
		headerBox.put("alg", CmnConst.JWT_ALG);
		Box claimBox = new Box();
		LocalDateTime ldt = LocalDateTime.now();
		long nowDt = ldt.toDate().getTime();
		long expDt = ldt.plusSeconds(ConfigUtil.getInt("jwt.file.exp-sec")).toDate().getTime();
		claimBox.put("fileId", fileId);
		claimBox.put("nowDt", nowDt);
		claimBox.put("expDt", expDt);
//		claimBox.put("expSec", ConfigUtil.getInt("jwt.file.exp-sec"));

		String jwt = Jwts.builder()
				.setHeader(headerBox)
				.setClaims(claimBox)
				.setExpiration(new Date(expDt))
				.signWith(SignatureAlgorithm.HS256, ConfigUtil.getString("jwt.file.key").getBytes())
				.compact();
//		log.debug("jwtToken:"+jwt);
		return jwt;
	}

	/**
	 * 토큰 유효성 체크
	 * @param jwtToken
	 * @return
	 */
	public static boolean isValidToken(String jwtToken) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(ConfigUtil.getString("jwt.login.key").getBytes())
					.parseClaimsJws(jwtToken)
					.getBody();
			log.debug("nowDt:" + claims.get("nowDt", Date.class));
			log.debug("expDt:" + claims.get("expDt", Date.class));
			log.debug("memNm:" + claims.get("memNm"));

			if (null != claims.get("memId")) { //사용자ID 미존재시 오류
				return true;
			}
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		return false;
	}


	/**
	 * 파일토큰 유효성 체크
	 * @param fileId
	 * @param jwtToken
	 * @return
	 */
	public static boolean isValidFileToken(String fileId, String jwtToken) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(ConfigUtil.getString("jwt.file.key").getBytes())
					.parseClaimsJws(jwtToken)
					.getBody();
			log.debug("nowDt:" + claims.get("nowDt", Date.class));
			log.debug("expDt:" + claims.get("expDt", Date.class));
			log.debug("req fileId:" + fileId);
			log.debug("token fileId:" + claims.get("fileId"));

			if (null != claims.get("fileId") && StringUtil.nvl(claims.get("fileId")).equals(fileId)) { //fileId가 존재여부및 입력된 fileId와 일치여부 체크
				return true;
			}
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		return false;
	}

	/**
	 * 토큰 복호화
	 * @param jwtToken
	 * @return Box
	 */
	public static Box decodeTokenBox(String jwtToken) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(ConfigUtil.getString("jwt.login.key").getBytes())
					.parseClaimsJws(jwtToken)
					.getBody();
//			log.debug("nowDt:" + claims.get("nowDt", Date.class));
//			log.debug("expDt:" + claims.get("expDt", Date.class));
//			log.debug("memNm:" + claims.get("memNm"));

			if (null == claims.get("memId")) { //사용자ID 미존재시 오류
				throw new BizException("F111"); //유효성 검증 오류
			}
		} catch (Exception e) {
			log.error("error: {}", e);
			throw new BizException("F111"); //유효성 검증 오류
		}
		return BoxUtil.toBox(claims);
	}


	/**
	 * 토큰 유효성 검증
	 * @param jwtToken
	 * @return String
	 */
	public static String decodeTokenStr(String jwtToken) {
		Box box = decodeTokenBox(jwtToken);
		return box.nvl("memId");
	}

}
