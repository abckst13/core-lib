package kr.aipeppers.pep.core.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import kr.aipeppers.pep.core.annotation.ReqInfo;
import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.exception.BizException;
import kr.aipeppers.pep.core.spring.RequestWrapper;
import kr.aipeppers.pep.core.util.ApiUtil;
import kr.aipeppers.pep.core.util.HttpUtil;
import kr.aipeppers.pep.core.util.JsonUtil;
import kr.aipeppers.pep.core.util.ServerUtil;
import kr.aipeppers.pep.core.util.SessionUtil;
import kr.aipeppers.pep.core.util.SpringUtil;
import kr.aipeppers.pep.core.util.StringUtil;
import kr.aipeppers.pep.core.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UiInterceptor implements HandlerInterceptor {

//	@Autowired
//	protected SqlSessionTemplate dao;

	@Autowired
	private ValidatorUtil validatorUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		log.debug("---------------------------preHandle-------------------------------");

		/*********************************************
		 * [START] 요청 타입이 JSON과 상관없는 공통 로직 처리
		 *********************************************/
////		log.debug("userBox: {}", userBox);
//
//		if (userBox == null ) {
//			throw new BizException("E101"); //로그인 정보가 존재하지 않습니다.
//		}
		Box userBox = SessionUtil.getUserData();
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
		if (apiOperation != null) {
			if (!SpringUtil.getRequestUri().equals("/") &&
				!SpringUtil.getRequestUri().startsWith("/anony") &&
				!SpringUtil.getRequestUri().startsWith("/cmn") &&
				!SpringUtil.getRequestUri().startsWith("/restapi/main/info")&&
				!SpringUtil.getRequestUri().startsWith("/restapi/category/aipeppers")&&
				!SpringUtil.getRequestUri().startsWith("/restapi/showpepper/info")&&
				!SpringUtil.getRequestUri().startsWith("/restapi/category/info")

				// 아래부터 임시로 추가한 부분 주석 풀고 사용 로그인 부분 작업 완료 후 삭제
//				&& !SpringUtil.getRequestUri().startsWith("/category")
//				&& !SpringUtil.getRequestUri().startsWith("/mypage")
//				&& !SpringUtil.getRequestUri().startsWith("/customer")
				//여기까지
				) {
				if (userBox == null ) {
				throw new BizException("E101"); //로그인 정보가 존재하지 않습니다.
			}
			}
		}

		//WAS LOG 기능 강화 (접근 IP, 시스템, 사번을 로그에 출력)
		Box threadBox = new Box();
		threadBox.put("clientIp", HttpUtil.getRemoteIpAddr());
		threadBox.put("clientSys", ServerUtil.getWorkerName());
		threadBox.put("clientCallUrl", HttpUtil.getRequestURL(request));
		threadBox.put("clientMethod", StringUtil.nvl(request.getMethod()));
		threadBox.put("clientRefererUri", HttpUtil.getRefererUri(request));
		if (userBox != null && !userBox.isEmpty()) {
			threadBox.put("userId", userBox.nvl("mnmCstMngtNo")); //TODO : userId로 변경
		}
		request.setAttribute(CmnConst.PARAM_PROP_THREAD_BOX, threadBox);
		ApiUtil.setThreadNm(threadBox);
		/*********************************************
		 * [E N D] 요청 타입이 JSON과 상관없는 공통 로직 처리
		 *********************************************/

		/*********************************************
		 * [START] 요청 타입이 JSON이어야 하는 로직
		 *********************************************/
		//content-type이 json인지 체크
		HttpInputMessage requestMessage = new ServletServerHttpRequest(request);
		if (HttpUtil.isApplicationJson(requestMessage) && handler instanceof HandlerMethod) {
			handlerMethod = (HandlerMethod)handler;

			// @RequestBody 어노테이션이 붙은 파라미터를 찾음.
			MethodParameter reqDtoParam = Arrays.stream(handlerMethod.getMethodParameters())
					.filter(parameter -> parameter.getParameterAnnotation(RequestBody.class) != null)
					.findFirst()
					.orElse(null);

			// JSON -> ReqDto Deserializing
			Object reqDto = SpringUtil.deserializeDto(reqDtoParam, requestMessage);
			if(reqDto != null) {
				((RequestWrapper)request).setRawData(mapper.writeValueAsBytes(reqDto));
			}

			//PreHandlable 인터페이스를 구현한 핸들러 클래스(컨트롤러)인 경우, preHandle을 호출해 준다.
			//이미 위의 Validator쪽에서 타입캐스팅 됐으니, 굳이 체크하는 로직은 제거
			if(handlerMethod.getBean() instanceof PreHandlable) {
				//preHandle의 결과로 리턴되는 Dto로 요청 데이터를 변경해 준다.
				//업무단위 영역에서 컨틀롤러단위의 공통 전처리에 사용가능
				reqDto = ((PreHandlable)handlerMethod.getBean()).preHandle(HttpUtil.getRequestUri(request), reqDto, request, response);
				if(reqDto != null) {
					((RequestWrapper)request).setRawData(mapper.writeValueAsBytes(reqDto));
				}
			}

			//request값 공통 세팅
			String jsonBody = IOUtils.toString(request.getReader());
			Box paramBox = (jsonBody == null || jsonBody.isEmpty()) ? new Box() : JsonUtil.toObject(jsonBody, Box.class);
			request.setAttribute(CmnConst.PARAM_BOX_ORG, jsonBody);
			request.setAttribute(CmnConst.PARAM_BOX, paramBox);
			request.setAttribute(CmnConst.REQ_DTO, reqDto);

			//Validator
			ReqInfo reqInfo = handlerMethod.getMethodAnnotation(ReqInfo.class);
			if (reqInfo != null) {
				log.info("[Validation]");
//					log.debug("paramBox:" + paramBox.toString());
				validatorUtil.validator(reqInfo, paramBox);
			}
		}
		/*********************************************
		 * [E N D] 요청 타입이 JSON이어야 하는 로직
		 *********************************************/

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		log.debug("---------------------------postHandle------------------------------");

		/*******************************************************************
		 * 메소드의 리턴타입을 가지고 응답 ContentType을 반 정도 스마트하게 추론하는 부분 ㅎㅎ
		 *   - 이 시점에선 실제로 response의 content-type 헤더 값이 설정되기 전이라서..
		 *   - produces가 명시적으로 있다면 그 annotation을 봐도 되지만..
		 *******************************************************************/
		ContentCachingResponseWrapper wRes = ((ContentCachingResponseWrapper)response);
		Object resDto = null;

		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			MethodParameter returnType = handlerMethod.getReturnType();
			HttpInputMessage responseMessage = new HttpInputMessage() {
				List<String> contentType = SpringUtil.getInferredContentType(returnType);
				@Override
				public HttpHeaders getHeaders() {
					HttpHeaders headers = new HttpHeaders();
					headers.put("Content-Type", contentType);
					return headers;
				}
				@Override
				public InputStream getBody() throws IOException {
					return new ByteArrayInputStream(wRes.getContentAsByteArray());
				}
			};


			String requestUri = HttpUtil.getRequestUri(request);
//			Object reqDto = request.getAttribute(CmnConst.REQ_DTO);

			//content-type이 json인지 체크
			if (HttpUtil.isApplicationJson(responseMessage)) {
				// JSON -> ResDto Deserializing
				resDto = SpringUtil.deserializeDto(returnType, responseMessage);

				//TODO : ConcurrentMap에 저장한(예: apiInfo) 서버API요청응답명세 테이블의 기준정보와 HttpUtil.getRequestUri(request)을 비교하여 해당하는 url만 비즈니스 로직을 수행한다.
//				log.debug("url : {}", requestUri);
//				Box reqBox = (Box)request.getAttribute(CmnConst.PARAM_BOX);
//				String jsonBody = (String)request.getAttribute(CmnConst.PARAM_BOX_ORG);
//				log.debug("request: {}", reqBox);
//				log.debug("jsonBody: {}", jsonBody);
			}

			//PostHandlable 인터페이스를 구현한 핸들러 클래스(컨트롤러)인 경우, postHandle을 호출해 준다.
			if(handler instanceof HandlerMethod && ((HandlerMethod)handler).getBean() instanceof PostHandlable) {
				//postHandle의 결과로 리턴되는 Dto로 응답 데이터를 변경해 준다.
				//업무단위 영역에서 컨틀롤러단위의 공통 후처리에 사용가능
				resDto = ((PostHandlable)handlerMethod.getBean()).postHandle(requestUri, resDto, request, response);
				setResponse(response, resDto);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//AfterCompletionable 인터페이스를 구현한 핸들러 클래스(컨트롤러)인 경우, afterCompletion을 호출해 준다.
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			if(handlerMethod.getBean() instanceof AfterCompletionable) {
				((AfterCompletionable)handlerMethod.getBean()).afterCompletion(HttpUtil.getRequestUri(request), request, response);
			}
		}
	}

	/**
	 * @param response
	 * @param resDto
	 * @throws Exception
	 */
	private void setResponse(HttpServletResponse response, Object resDto) throws Exception {
		if(resDto != null) {
			Map<String,String> cloneHeaders = new HashMap<>();
			String orgCharacterEncoding = response.getCharacterEncoding();
			String orgContentType = response.getContentType();

			//backup(cloning) original headers
			response.getHeaderNames().forEach(headerName -> {
				String headerValue = response.getHeaders(headerName).stream().collect(Collectors.joining("; "));
				cloneHeaders.put(headerName, headerValue);
			});

			//reset response
			response.reset();

			//restore original response headers
			response.setCharacterEncoding(orgCharacterEncoding);
			response.setContentType(orgContentType);
			cloneHeaders.forEach(response::setHeader);

			//rewrite response body
			String resStr = mapper.writeValueAsString(resDto);

			response.getWriter().write(resStr);
		}
	}

}

