package kr.aipeppers.pep.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvokerUtil {

	/**
	 * 메소드 호출 유틸
	 * ex)  Box newBox = (Box)InvokerUtil.methodCall(msgNewService, "msgView", box);
	 * 		List<Box> newList = (List<Box>)InvokerUtil.methodCall(msgNewService, "msgList", box);
	 * @param classObj
	 * @param methodNm
	 * @param box
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public static Object methodCall(Object classObj, String methodNm, Box box) {

		try {
			Class param[] = new Class[1];
			param[0] = Box.class;
			Method method = classObj.getClass().getDeclaredMethod(methodNm, param);
			return method.invoke(classObj, box);
		} catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();
			throw new BaseException(throwable);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * 메소드 호출 유틸(일반유형)
	 * ex)  InvokerUtil.methodCall("bizService.apiInsert", reqBox, resBox);
	 * @param serviceId
	 * @param reqBox
	 * @param resBox
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object methodCall(String serviceId, Box reqBox, Box resBox) {

		if (null == serviceId || serviceId.isEmpty()) {
			return null;
		}
		try {
			String methodNm = serviceId.split("\\.")[1];
			Object classObj = SpringUtil.getBean(serviceId.split("\\.")[0]);
			Class param[] = new Class[2];
			param[0] = Box.class;
			param[1] = Box.class;
			Method method = classObj.getClass().getDeclaredMethod(methodNm, param);
			return method.invoke(classObj, reqBox, resBox);
		} catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();
			throw new BaseException(throwable);
		} catch (Exception e) {
			throw new BaseException(e);
		}

	}

	/**
	 * 메소드 호출 유틸(일반유형확장)
	 * ex)  InvokerUtil.methodCall("bizService.apiInsert", Object... params);
	 * @param serviceId
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static Object methodCall(String serviceId, Object... params) {
		if (null == serviceId || serviceId.isEmpty()) {
			return null;
		}
		try {
			String methodNm = serviceId.split("\\.")[1];
			Object classObj = SpringUtil.getBean(serviceId.split("\\.")[0]);
			Class<?>[] paramTypes = null;
			if(params != null) {
				paramTypes = new Class<?>[params.length];
				for(int i = 0; i < params.length; i++) {
					paramTypes[i] = params[i].getClass();
				}
			}
			Method method = classObj.getClass().getDeclaredMethod(methodNm, paramTypes);
			return method.invoke(classObj, params);

		} catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();
			throw new BaseException(throwable);

		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
}
