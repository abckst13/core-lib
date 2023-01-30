package kr.aipeppers.pep.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PostHandlable {
	default Object postHandle(String requestURI, Object resDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return resDto;
	}
}
