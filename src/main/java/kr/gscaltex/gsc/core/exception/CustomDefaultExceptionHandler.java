package kr.gscaltex.gsc.core.exception;

import java.sql.SQLException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.domain.ResErrorDto;
import kr.gscaltex.gsc.core.util.MsgUtil;
import kr.gscaltex.gsc.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomDefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public ResErrorDto handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);

		String msgId = null, msg = null;
		Object[] messageParams = null;
		Throwable nested = e.getCause();
		if(e instanceof BizException) {
			BizException be = (BizException)e;
			msgId = be.getMessageId();
			messageParams = be.getMessageParams();
		} else if (e instanceof DuplicateKeyException) {
			DuplicateKeyException de = (DuplicateKeyException) e;
			msgId = CmnConst.ResCd.RES_CD_DUPLICATE; //중복된 데이터가 존재합니다.
		}


		if (nested != null) {
			if (nested instanceof SQLException) {
				SQLException se = (SQLException) nested;
				log.debug("SQLException errorCode:" + se.getErrorCode());
				log.debug("SQLException sqlState:" + se.getSQLState());
				log.debug("SQLException message:" + se.getMessage());
				if (se.getErrorCode() == 1062) {
					msgId = CmnConst.ResCd.RES_CD_DUPLICATE; //중복된 데이터가 존재합니다.
				}
			}
		}

		if (null == msgId || msgId.isEmpty()) {
			msgId = CmnConst.ResCd.RES_CD_ERROR;
		}

		//TODO : 추후 삭제 - 업무오류를 메타정의전까지 임시로 사용하는 예외처리
		if(msgId.equals(CmnConst.ResCd.BIZ_ERROR)) {
			if(messageParams != null && messageParams.length > 0) {
				msg = ((String[])messageParams)[0];
			}
		}

		if(StringUtil.isEmpty(msg)) {
			msg = MsgUtil.getMsg(msgId, messageParams);
		}
		if(StringUtil.isEmpty(msg)) {
			msg = e.getMessage();
		}

		log.error("handleException : {}", e.getMessage());
		log.error("Exception Class : {}", e.getClass());
		log.error("Exception ID : {}", msgId);
		log.error("Exception Message : {}", msg);
		log.error("Exception Detail : ", e);

		return new ResErrorDto(msgId, msg);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
//	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public ResErrorDto handleNoResourceNotFoundException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);

		String msgId = CmnConst.ResCd.RES_CD_NOT_FOUND;
		String msg = MsgUtil.getMsg(msgId);

		log.error("Exception ID : {}", msgId);
		log.error("Exception Message : {}", msg);

		return new ResErrorDto(msgId, msg);

	}

	/**
	 * 파라미터 오류 발생시((@Valid)
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//TODO: CmnConst.RES_UNPROCESSABLE_ENTITY 메세지 정의 맟 커스텀 메세지 좀더 다듬어야함(사용안할수도 있음)
	@ExceptionHandler(BindException.class)
//	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public ResErrorDto handleBindException(BindException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);

		String msgId = CmnConst.ResCd.RES_VALIDATION;
		String msg = ""; // 파라미터 오류

//		log.debug("e.getFieldError() : {}", e.getFieldError());
//		log.debug("getDefaultMessage() : {}", e.getFieldError().getDefaultMessage());
//		log.debug("getField() : {}", e.getFieldError().getField());

		if (e.getFieldError() != null) {
			msg = e.getFieldError().getField() + " 은/는 " + e.getFieldError().getDefaultMessage();
		} else {
			msg = "알수 없음";
		}

		log.error("Exception ID : {}", msgId);
		log.error("Exception Message : {}", msg);

		return new ResErrorDto(msgId, MsgUtil.getMsg(msgId, new String[]{ msg }));
	}

	private void preHandleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (e != null) {
//			e.printStackTrace();
			log.error("CustomDefaultExceptionHandler, message : {}", e);
		}
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
	}

}
