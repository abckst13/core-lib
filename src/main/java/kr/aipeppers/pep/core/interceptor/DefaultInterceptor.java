package kr.aipeppers.pep.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.exception.BizException;
import kr.aipeppers.pep.core.util.ApiUtil;
import kr.aipeppers.pep.core.util.HttpUtil;
import kr.aipeppers.pep.core.util.ServerUtil;
import kr.aipeppers.pep.core.util.SessionUtil;
import kr.aipeppers.pep.core.util.SpringUtil;
import kr.aipeppers.pep.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefaultInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Box userBox = SessionUtil.getUserData();
		if (null != userBox) {
			Box threadBox = new Box();
			threadBox.put("clientIp", HttpUtil.getRemoteIpAddr());
			threadBox.put("clientSys", ServerUtil.getWorkerName());
			threadBox.put("clientCallUrl", HttpUtil.getRequestURL(request));
			threadBox.put("clientMethod", StringUtil.nvl(request.getMethod()));
			threadBox.put("clientRefererUri", HttpUtil.getRefererUri(request));
			if (userBox != null && !userBox.isEmpty()) {
//				if (userBox.ne("mnmCstMngtNo")) {
//					threadBox.put("userId", userBox.nvl("mnmCstMngtNo"));
//				} else {
					threadBox.put("userId", userBox.nvl("empno"));
//				}
			}
			ApiUtil.setThreadNm(threadBox);
		}
		log.debug(SpringUtil.getRequestUri());
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
			if (apiOperation != null) {
				if (!SpringUtil.getRequestUri().equals("/") &&
					!SpringUtil.getRequestUri().startsWith("/anony") &&
					!SpringUtil.getRequestUri().startsWith("/cmn") &&
					!SpringUtil.getRequestUri().startsWith("/callbackapi") &&
					!SpringUtil.getRequestUri().startsWith("/sample") &&
					!SpringUtil.getRequestUri().startsWith("/test") &&
					!SpringUtil.getRequestUri().startsWith("/mypage") &&
					!SpringUtil.getRequestUri().startsWith("/restapi")

					// 아래부터 임시로 추가한 부분 주석 풀고 사용 로그인 부분 작업 완료 후 삭제
//					&& !SpringUtil.getRequestUri().startsWith("/category")
//					&& !SpringUtil.getRequestUri().startsWith("/mypage")
//					&& !SpringUtil.getRequestUri().startsWith("/customer")
					//여기까지

					) {
					log.debug("[URL INVALID] 허용된 url이 아닙니다. restapi를 확인해주세요. - {} : {}", apiOperation.value(), SpringUtil.getRequestUri());
					throw new BizException(CmnConst.ResCd.RES_CD_400); //Bad Request
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}

