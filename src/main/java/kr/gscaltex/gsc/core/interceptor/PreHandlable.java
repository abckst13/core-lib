package kr.gscaltex.gsc.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PreHandlable {
	default Object preHandle(String requestURI, Object reqDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return reqDto;
	}
}
