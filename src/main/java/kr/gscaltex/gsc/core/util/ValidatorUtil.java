package kr.gscaltex.gsc.core.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springmodules.validation.commons.ConfigurableBeanValidator;

import kr.gscaltex.gsc.core.annotation.ReqInfo;
import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidatorUtil {

	@Autowired
	private ConfigurableBeanValidator configurableBeanValidator;

	public void validator(ReqInfo reqInfo, Object data) throws Exception {
		if(reqInfo != null) {
			String validForm = reqInfo.validForm();
			if(StringUtil.isNotEmpty(validForm)) {
				validator(validForm, data);
			}
		}
	}

	public void validator(String validForm, Object data) throws Exception {
		if(StringUtil.isNotEmpty(validForm)) {
			log.debug("Validation Form Name : {}", validForm);
			BindingResult errors = null;
			if(data instanceof Map) {
				errors = new MapBindingResult((Map<?, ?>)data, validForm);
			} else {
				errors = new BeanPropertyBindingResult(data, validForm);
			}
			configurableBeanValidator.setFormName(validForm);
			try {
				configurableBeanValidator.validate(data, errors);
			} catch (Exception e) {
				throw new BizException(CmnConst.ResCd.RES_VALIDATION);
			}
			if(errors.hasErrors()) {
				log.info("Validation Error : {}", errors);
				FieldError fieldError = errors.getFieldError();
				String errorCode = fieldError.getCode();
				if(data instanceof Map) {
					String errorField = fieldError.getField();
					if(StringUtil.isNotEmpty(errorField)) {
						errorField = errorField.substring(errorField.lastIndexOf(".")+1);
					}
					Object[] eArgs = fieldError.getArguments();
					if(eArgs != null && eArgs.length > 0) {
						eArgs[0] = eArgs[0] + " (" + errorField + ")";
					}
					throw new BizException(errorCode, eArgs);
				} else {
					throw new BizException(errorCode, fieldError.getArguments());
				}
			}
		}
	}

}
