package kr.gscaltex.gsc.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConfigUtil {

	private static Environment env;

	@Autowired
	public ConfigUtil(Environment env) {
		ConfigUtil.env = env;
	}

	/**
	 * 문자열로 반환
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return env.getProperty(key);
		} catch (Exception e) {
			log.error("error : {}", e);
			throw new BaseException("잘못된 properties key : " + key + ", value : " + env.getProperty(key));
		}
	}

	/**
	 * int형으로 반환
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		try {
			return Integer.valueOf(env.getProperty(key));
		} catch (Exception e) {
			log.error("error : {}", e);
			throw new BaseException("잘못된 properties key : " + key + ", value : " + env.getProperty(key));
		}
	}

	/**
	 * boolean형으로 반환
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		try {
			return env.getProperty(key).equals("true") ? true : false;
		} catch (Exception e) {
			log.error("error : {}", e);
			throw new BaseException("잘못된 properties key : " + key + ", value : " + env.getProperty(key));
		}
	}

	/**
	 * 현재 가동되고 있는 profile명
	 * @return
	 */
	public static String getProfile() {
		return getString(CmnConst.PROFILE_KEY);
	}

	/**
	 * 개발환경으로 수행중일 경우 true
	 * @return
	 */
	public static boolean isDev() {
		return getString(CmnConst.PROFILE_KEY).equals(CmnConst.PROFILE_DEV) ? true : false;
	}

	/**
	 * 테스트환경으로 수행중일 경우 true
	 * @return
	 */
	public static boolean isTest() {
		return getString(CmnConst.PROFILE_KEY).equals(CmnConst.PROFILE_TEST) ? true : false;
	}

	/**
	 * 스테이지환경으로 수행중일 경우 true
	 * @return
	 */
	public static boolean isStag() {
		return getString(CmnConst.PROFILE_KEY).equals(CmnConst.PROFILE_STAG) ? true : false;
	}

	/**
	 * 운영환경으로 수행중일 경우 true
	 * @return
	 */
	public static boolean isProd() {
		return getString(CmnConst.PROFILE_KEY).equals(CmnConst.PROFILE_PROD) ? true : false;
	}

}
