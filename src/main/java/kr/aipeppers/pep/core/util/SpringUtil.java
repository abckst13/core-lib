package kr.aipeppers.pep.core.util;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.util.UrlPathHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringUtil {

	/**
	 * Gets the http servlet request.
	 *
	 * @return the http servlet request
	 */
	public static final HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = null;
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			if(servletRequestAttributes != null) {
				request = servletRequestAttributes.getRequest();
			}
		} catch (Exception e) {
			log.error("error : {}", e.getMessage());
//			throw new BaseException(e);
		}
		return request;
	}

	/**
	 * Gets the http servlet response.
	 *
	 * @return the http servlet response
	 */
	public static HttpServletResponse getHttpResponse() {
		HttpServletResponse response = null;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			response = requestAttributes.getResponse();
		}
		return response;
	}

	/**
	 * Gets the web application context.
	 *
	 * @return the web application context
	 */
	public static final WebApplicationContext getWebApplicationContext() {
		return ContextLoaderListener.getCurrentWebApplicationContext();
	}

	/**
	 * Gets the bean.
	 *
	 * @param beanName the bean name
	 * @return the bean
	 */
	public static final Object getBean(String beanName) {
		Object bean = null;
		WebApplicationContext webApplicationContext = SpringUtil.getWebApplicationContext();
		if(webApplicationContext != null) {
			bean = webApplicationContext.getBean(beanName);
		}
		return bean;
	}
	/**
	 * Gets the bean.
	 *
	 * @param beanName the bean name
	 * @return the bean
	 */
	public static final Object getApplicationConfigBean(String beanName) {
//		Object bean = null;
		ApplicationContext ApplicationContext = ApplicationContextUtil.getApplicationContext();
		return ApplicationContext.getBean(beanName);
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @return the bean
	 */
	public static final <T> T getBean(Class<T> clazz) {
		T bean = null;
		WebApplicationContext webApplicationContext = SpringUtil.getWebApplicationContext();
		if(webApplicationContext != null) {
			bean = webApplicationContext.getBean(clazz);
		}
		return bean;
	}

	/**
	 * Gets the context path.
	 *
	 * @param request the request
	 * @return the context path
	 */
	public static String getContextPath(HttpServletRequest request) {
		return new UrlPathHelper().getContextPath(request);
	}

	/**
	 * Gets the context path.
	 *
	 * @return the context path
	 */
	public static String getContextPath() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return new UrlPathHelper().getContextPath(request);
	}

	public static int getServerPort() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return request.getServerPort();
	}

	/**
	 * Gets the servlet path.
	 *
	 * @param request the request
	 * @return the servlet path
	 */
	public static String getServletPath(HttpServletRequest request) {
		return new UrlPathHelper().getServletPath(request);
	}

	/**
	 * Gets the servlet path.
	 *
	 * @return the servlet path
	 */
	public static String getServletPath() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return SpringUtil.getServletPath(request);
	}

	/**
	 * Gets the originating servlet path.
	 *
	 * @param request the request
	 * @return the originating servlet path
	 */
	public static String getOriginatingServletPath(HttpServletRequest request) {
		return new UrlPathHelper().getOriginatingServletPath(request);
	}

	/**
	 * Gets the originating servlet path.
	 *
	 * @return the originating servlet path
	 */
	public static String getOriginatingServletPath() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return new UrlPathHelper().getOriginatingServletPath(request);
	}

	/**
	 * Gets the request uri.
	 *
	 * @param request the request
	 * @return the request uri
	 */
	public static String getRequestUri(HttpServletRequest request) {
		return new UrlPathHelper().getRequestUri(request);
	}

	/**
	 * Gets the request uri.
	 *
	 * @return the request uri
	 */
	public static String getRequestUri() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return new UrlPathHelper().getRequestUri(request);
	}

	/**
	 * Gets the request menuId
	 *
	 * @return the request uri
	 */
	public static String getMenuCd() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		String url = new UrlPathHelper().getRequestUri(request);
		String rtn = "";
		if (url != null && !url.isEmpty()) {
			rtn = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		}
		return rtn;
	}


	/**
	 * Gets the org request uri.
	 *
	 * @param request the request
	 * @return the org request uri
	 */
	public static String getOriginatingRequestUri(HttpServletRequest request) {
		return new UrlPathHelper().getOriginatingRequestUri(request);
	}

	/**
	 * Gets the originating request uri.
	 *
	 * @return the originating request uri
	 */
	public static String getOriginatingRequestUri() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return new UrlPathHelper().getOriginatingRequestUri(request);
	}

	/**
	 * Gets the org query string.
	 *
	 * @param request the request
	 * @return the org query string
	 */
	public static String getOriginatingQueryString(HttpServletRequest request) {
		return new UrlPathHelper().getOriginatingQueryString(request);
	}

	/**
	 * Gets the originating query string.
	 *
	 * @return the originating query string
	 */
	public static String getOriginatingQueryString() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return new UrlPathHelper().getOriginatingQueryString(request);
	}

	/**
	 * Gets the request url.
	 *
	 * @param request the request
	 * @return the request url
	 */
	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		String reqUrl = HttpUtil.getRequestURL(request);
		int pathIdx = reqUrl.indexOf("/", request.getScheme().length() + 3);
		if(pathIdx > -1) {
			url.append(reqUrl.substring(0, pathIdx));
		} else {
			url.append(reqUrl);
		}
		String uri = SpringUtil.getRequestUri(request);
		if(StringUtil.isNotEmpty(uri)) {
			url.append(uri);
		}
		return url.toString();
	}

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	public static String getRequestURL() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return SpringUtil.getRequestURL(request);
	}

	/**
	 * Gets the org request url.
	 *
	 * @param request the request
	 * @return the org request url
	 */
	public static String getOriginatingRequestURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		String reqUrl = HttpUtil.getRequestURL(request);
		int pathIdx = reqUrl.indexOf("/", request.getScheme().length() + 3);
		if(pathIdx > -1) {
			url.append(reqUrl.substring(0, pathIdx));
		} else {
			url.append(reqUrl);
		}
		String uri = SpringUtil.getOriginatingRequestUri(request);
		if(StringUtil.isNotEmpty(uri)) {
			url.append(uri);
		}
		return url.toString();
	}

	/**
	 * Gets the org request url.
	 *
	 * @return the org request url
	 */
	public static String getOriginatingRequestURL() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return SpringUtil.getOriginatingRequestURL(request);
	}

	/**
	 * Gets the lookup path for request.
	 *
	 * @param request the request
	 * @return the lookup path for request
	 */
	public static String getLookupPathForRequest(HttpServletRequest request) {
		return new UrlPathHelper().getLookupPathForRequest(request);
	}

	/**
	 * Gets the lookup path for request.
	 *
	 * @return the lookup path for request
	 */
	public static String getLookupPathForRequest() {
		HttpServletRequest request = SpringUtil.getHttpServletRequest();
		return SpringUtil.getLookupPathForRequest(request);
	}


	public static List<String> getInferredContentType(MethodParameter returnType) {

		String inferredContentType = MediaType.APPLICATION_JSON_VALUE;
		boolean cannotDeserialize = true;

		Type type = returnType.getNestedGenericParameterType();

		log.debug("Generic Parameter Type: {}", type);
		log.debug("Generic Parameter Type: {}", type.getClass());

		if(type instanceof ParameterizedType) {
			Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();

			//하나라도 Deserialize 할 수 없는게 존재하면, deseriazlie 불가
			cannotDeserialize = List.of(actualTypes).stream().map(actualType -> actualType.getTypeName()).map(typeName -> {
				boolean canDeserialize = false;
				try {

					log.debug("typeName: {}", typeName);
					canDeserialize = canDeserialize(Class.forName(typeName));

				} catch (ClassNotFoundException e) {
					//ignore
					log.warn("Class Not Found! {}", e.getMessage());
				}
				return canDeserialize;
			}).filter(canDeserialize->!canDeserialize).findAny().isPresent();

		} else {
			cannotDeserialize = !canDeserialize(type.getClass());
		}

		if(cannotDeserialize) {
			inferredContentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		log.debug("Inferred Content Type: {}", inferredContentType);

		return List.of(inferredContentType);
	}


	private static boolean canDeserialize(Class<?> clazz) {
		boolean canDeserialize = false;
		try {
			if(clazz != null) {
				log.debug("clazz: {}", clazz);
				RequestMappingHandlerAdapter handlerAdapter = getBean(RequestMappingHandlerAdapter.class);
				List<HttpMessageConverter<?>> httpMessageConverters = handlerAdapter.getMessageConverters();
				for(HttpMessageConverter<?> httpMessageConverter : httpMessageConverters) {
					if(httpMessageConverter.canRead(clazz, MediaType.APPLICATION_JSON)) {
						canDeserialize = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("canDeserialize error: {}", e.getMessage());
		}
		return canDeserialize;
	}

	/**
	 * Handler 파라미터/리턴타입을 Resolve하여 Dto로 가져온다.
	 * @param request
	 * @param methodParameters
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object deserializeDto(MethodParameter type, HttpInputMessage inputMessage) {
		Object dto = null;
		try {
			if(type != null) {
				RequestMappingHandlerAdapter handlerAdapter = getBean(RequestMappingHandlerAdapter.class);
				Type dtoType = type.getNestedGenericParameterType();
				Class<?> dtoClass = type.getParameterType();

				//content-type이 json인지 체크
				MediaType contentType = HttpUtil.getMediaType(inputMessage);
				if (HttpUtil.isApplicationJson(inputMessage)) {
					List<HttpMessageConverter<?>> httpMessageConverters = handlerAdapter.getMessageConverters();
					for(HttpMessageConverter<?> httpMessageConverter : httpMessageConverters) {
						if(httpMessageConverter.canRead(dtoClass, contentType)) {
							if(httpMessageConverter instanceof AbstractJackson2HttpMessageConverter) {
								//Generic에 대한 Nested Component Type 처리를 위한 분기
								dto = ((AbstractJackson2HttpMessageConverter) httpMessageConverter).read(dtoType, dtoClass, inputMessage);
								dto = BeanUtil.convertNullToDefault(dto);

							} else {
								dto = httpMessageConverter.read((Class) dtoClass, inputMessage);
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("deserializeDto error: {}", e.getMessage());
		}
		return dto;
	}
}
