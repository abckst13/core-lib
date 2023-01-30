package kr.aipeppers.pep.core.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.aipeppers.pep.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Controller
@ApiIgnore
public class CustomErrorController implements ErrorController {

	@RequestMapping(value="/error")
	public String error(HttpServletRequest request) {
		if (SpringUtil.getOriginatingRequestURL().indexOf("favicon.ico") > -1 ) {
			return null;
		}

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		if (throwable instanceof BizException) {
			throw (BizException) throwable;
		} else if (String.valueOf(status).equalsIgnoreCase(HttpStatus.NOT_FOUND.toString())) {
			throw new BizException(throwable);
		}
		log.debug("==== [404 Not Found] Original URL : " + SpringUtil.getOriginatingRequestURL());
		throw new ResourceNotFoundException();
	}

//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}

}
