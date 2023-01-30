package kr.aipeppers.pep.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.data.Box;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class SessionUtil {

	/**
	 * 세션 가져오기
	 * @return
	 */
	public static final HttpSession getSession() {
		HttpSession session = null;
		try {
			session = SpringUtil.getHttpServletRequest().getSession();
		} catch (Exception e) {
//			throw new BaseException(e);
			log.error("error : {}", e.getMessage());
		}
		return session;
	}

	public static Box getUserData(HttpServletRequest request) {
		Box resBox = null;
		try {
			resBox = (Box)request.getSession().getAttribute(CmnConst.SES_USER_DATA);
		} catch (Exception e) {
			log.error("error : {}", e.getMessage());
		}
		return resBox;
	}

	public static Box getUserData() {
		return getUserData(SpringUtil.getHttpServletRequest());
	}

	public static String getUserId() {
		String resUserId = null;
		Box sBox = getUserData();
		if (sBox != null) {
			resUserId = sBox.nvl("userId");
		}
		return resUserId;
	}

	public static String getUserId(HttpServletRequest request) {
		String resUserId = null;
		Box sBox = getUserData(request);
		if (sBox != null) {
			resUserId = sBox.nvl("userId");
		}
		return resUserId;
	}


	/**
	 * 세션Id 반환
	 * @return
	 */
	public static String getSid() {
//		return SpringUtil.getHttpServletRequest().getRequestedSessionId();
		return SpringUtil.getHttpServletRequest().getSession().getId();
	}

	/**
	 * 세션Id 반환
	 * @return
	 */
	public static String getSid(HttpServletRequest request) {
		return request.getSession().getId();
	}
}