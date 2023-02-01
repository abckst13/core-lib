package kr.aipeppers.pep.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 조건부 PostHandle 인터페이스
 *
 */
public interface ConditionalPostHandlable {

	/**
	 * 조건부 PostHandle
	 *
	 * @param condition
	 * @param requestURI
	 * @param reqDto
	 * @param resDto
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	default Object conditionalPostHandle(Condition condition, String requestURI, Object reqDto, Object resDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return resDto;
	}

	/**
	 * 조건들 정의 - 현재는 미션대상API애 대한 처리만 필요
	 */
	enum Condition {
//		MISSION_API
	}
}
