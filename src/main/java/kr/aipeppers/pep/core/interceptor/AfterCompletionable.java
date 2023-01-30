package kr.aipeppers.pep.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AfterCompletionable {
	default void afterCompletion(String requestURI, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}
}
