package kr.gscaltex.gsc.core.util;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.gscaltex.gsc.core.exception.BaseException;

public class JsonUtil {

	/**
	 * Checks if is json accept.
	 *
	 * @param request the request
	 * @return true, if is json accept
	 */
	public static final boolean isJsonAccept(HttpServletRequest request) {
		String accept = request.getHeader("Accept");

		if(accept != null && accept.toLowerCase().startsWith("application/json")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is json body.
	 *
	 * @param request the request
	 * @return true, if is json body
	 */
	public static final boolean isJsonBody(HttpServletRequest request) {
		String contentType = request.getContentType();
		if(contentType != null && contentType.toLowerCase().startsWith("application/json")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is json extension.
	 *
	 * @param request the request
	 * @return true, if is json extension
	 */
	public static final boolean isJsonExtension(HttpServletRequest request) {
		String uri = SpringUtil.getOriginatingServletPath(request);
		if(uri.endsWith(".json")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is json error accept.
	 *
	 * @param request the request
	 * @return true, if is json error accept
	 */
	public static final boolean isJsonErrorAccept(HttpServletRequest request) {
		if("application/json".equals(request.getHeader("X-Error-Accept"))) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is json.
	 *
	 * @param request the request
	 * @return true, if is json
	 */
	public static final boolean isJson(HttpServletRequest request) {
//		if(isJsonExtension(request) || isJsonAccept(request) || isJsonBody(request) || isJsonErrorAccept(request)) {
		if(isJsonExtension(request) || isJsonAccept(request) || isJsonBody(request)) {
			return true;
		}
		return false;
	}

	/**
	 * To json.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static final String toJson(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}
	}

	/**
	 * To format json.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static final String toFormatJson(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}
	}

	/**
	 * json string To object.
	 *
	 * @param <T> the generic type
	 * @param json the json
	 * @param clazz the clazz
	 * @return the t
	 */
	public static final <T> T toObject(String json, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

//	public static final <T> T toObject(String json) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			return objectMapper.readTree(json);
//		} catch (Exception e) {
//			throw new BaseException(e);
//		}
//	}

	/**
	 * json string To object.
	 *
	 * @param <T> the generic type
	 * @param json the json
	 * @param typRef the TypeReference
	 * @return the t
	 */
	public static final <T> T toObject(String json, TypeReference<T> typRef) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, typRef);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * Convert value.
	 *
	 * @param <T> the generic type
	 * @param fromValue the from value
	 * @param clazz the to value type
	 * @return the t
	 */
	public static final <T> T convertValue(Object fromValue, Class<T> clazz) {
//		ObjectMapper objectMapper = new ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.convertValue(fromValue, clazz);
	}

	/**
	 * Convert value.
	 *
	 * @param <T> the generic type
	 * @param fromValue the from value
	 * @param typRef the TypeReference
	 * @return the t
	 */
	public static final <T> T convertValue(Object fromValue, TypeReference<T> typRef) {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.convertValue(fromValue, typRef);
	}

}
