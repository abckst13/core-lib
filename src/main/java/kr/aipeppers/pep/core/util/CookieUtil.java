package kr.aipeppers.pep.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.aipeppers.pep.core.exception.BaseException;
import kr.aipeppers.pep.core.util.crypto.Base64Util;

public class CookieUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CookieUtil.class);
	private static final int DEFAULT_EXPIRY = -1;
	
	
	public static final void setCookie(String key, String value, String path, int expiry, String domain, boolean httpOnly, boolean isEncode) {
		try {
			Cookie cookie = null;
			if (StringUtil.isEmpty(value)) {
				cookie = new Cookie(key, value);
			} else {
				if (isEncode) {
					cookie = new Cookie(key, Base64Util.encodeString(value));
				} else {
					cookie = new Cookie(key, value);
				}
			}
			String newPath = path;
			if(StringUtil.isEmpty(newPath)) {
				newPath = "/";
			}
			cookie.setPath(newPath);
			if(expiry != DEFAULT_EXPIRY) {
				cookie.setMaxAge(expiry);
			}
			if(domain != null) {
				cookie.setDomain(domain);
			}
			cookie.setHttpOnly(httpOnly);
			HttpServletResponse response = SpringUtil.getHttpResponse();
			response.addCookie(cookie);
		} catch (Exception e) {
			log.error("cookie setter error : {} - {}", key, value);
			throw new BaseException(e);
		}
	}
	
	
	public static final void setCookie(String key, String value, String path) {
		setCookie(key, value, path, DEFAULT_EXPIRY, null, true, true);
	}
	
	
	public static final void setCookie(String key, String value) {
		setCookie(key, value, null, DEFAULT_EXPIRY, null, true, true);
	}
	
	
	public static final void setCookie(String key, String value, String path, int expiry) {
		setCookie(key, value, path, expiry, null, true, true);
	}
	
	
	public static final void setCookie(String key, String value, int expiry) {
		setCookie(key, value, null, expiry, null, true, true);
	}
	
	public static final void setCookie(String key, String value, int expiry, boolean httpOnly) {
		setCookie(key, value, null, expiry, null, httpOnly, true);
	}
	
	public static final void setCookie(String key, String value, boolean httpOnly) {
		setCookie(key, value, DEFAULT_EXPIRY, httpOnly);
	}
	
	
	public static final String getCookie(String key) {
		String value = null;
		try {
		    HttpServletRequest request = SpringUtil.getHttpServletRequest();
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				return value;
			}
			for(Cookie cookie : cookies) {
				if(key.equals(cookie.getName())) {
					value = cookie.getValue();
					if (StringUtil.isNotEmpty(value)) {
						value = Base64Util.decodeString(value);
					}
				}
			}
		} catch (Exception e) {
			log.error("cookie getter error : {} - {}", key);
			throw new BaseException(e);
		}
		return value;
	}
	
	
	public static final void removeCookie(String key, String path) {
		try {
		    HttpServletRequest request = SpringUtil.getHttpServletRequest();
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				return;
			}
			for(Cookie cookie : cookies) {
				if(key.equals(cookie.getName())) {
					CookieUtil.setCookie(key, null, path, 0, null, true, true);
				}
			}
		} catch (Exception e) {
			log.error("cookie remove error : {}", key);
			throw new BaseException(e);
		}
	}
	
	
	public static final void removeCookie(String key) {
		CookieUtil.removeCookie(key, null);
	}
	
	
	public static final void printCookies() {
		try {
		    HttpServletRequest request = SpringUtil.getHttpServletRequest();
			Cookie[] cookies = request.getCookies();
			int cookieLen = cookies == null ? 0 : cookies.length;
			log.debug("Cookie Length : {}", cookieLen);
			Cookie cookie = null;
			for(int i=0; i<cookieLen; i++) {
				cookie = cookies[i];
				log.debug("Key : {}, Value : {}", cookie.getName(), cookie.getValue());
			}
		} catch (Exception e) {
			log.error("error : {}", e);
		}
	}
	
	
}
