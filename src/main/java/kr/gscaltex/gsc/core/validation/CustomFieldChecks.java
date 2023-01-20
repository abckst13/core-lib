package kr.gscaltex.gsc.core.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;
import org.springmodules.validation.commons.MessageUtils;

import kr.gscaltex.gsc.core.data.Box;
import kr.gscaltex.gsc.core.exception.BaseException;
import kr.gscaltex.gsc.core.util.CdUtil;
import kr.gscaltex.gsc.core.util.StringUtil;

@SuppressWarnings("serial")
public class CustomFieldChecks extends FieldChecks {

	private static final Logger log = LoggerFactory.getLogger(CustomFieldChecks.class);

	public static Date validateDate(Object bean, ValidatorAction va, Field field, Errors errors) {
		Date result = null;
		String value = extractValue(bean, field);
		String datePattern = field.getVarValue("datePattern");
		String datePatternStrict = field.getVarValue("datePatternStrict");
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (datePattern != null && datePattern.length() > 0) {
					result = GenericTypeValidator.formatDate(value, datePattern, false);
				} else if (datePatternStrict != null && datePatternStrict.length() > 0) {
					result = GenericTypeValidator.formatDate(value, datePatternStrict, true);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (result == null) {
				rejectValue(errors, field, va, datePattern);
			}
		}
		return result;
	}

	public static boolean validateMinLength(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int min = Integer.parseInt(field.getVarValue("minlength"));
			try {
				if (!GenericValidator.minLength(value, min)) {
					rejectValue(errors, field, va, min);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, min);
				return false;
			}
		}
		return true;
	}

	public static boolean validateMaxLength(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (value != null) {
			int max = Integer.parseInt(field.getVarValue("maxlength"));
			try {
				if (!GenericValidator.maxLength(value, max)) {
					rejectValue(errors, field, va, max);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, max);
				return false;
			}
		}
		return true;
	}

	public static boolean validateIntRange(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int min = Integer.parseInt(field.getVarValue("min"));
			int max = Integer.parseInt(field.getVarValue("max"));
			try {
				int intValue = Integer.parseInt(value);
				if (!GenericValidator.isInRange(intValue, min, max)) {
					rejectValue(errors, field, va, min, max);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, min, max);
				return false;
			}
		}
		return true;
	}

	public static boolean validateIntMin(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int min = Integer.parseInt(field.getVarValue("min"));
			try {
				int intValue = Integer.parseInt(value);
				if (!GenericValidator.minValue(intValue, min)) {
					rejectValue(errors, field, va, min);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, min);
				return false;
			}
		}
		return true;
	}

	public static boolean validateIntMax(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int max = Integer.parseInt(field.getVarValue("max"));
			try {
				int intValue = Integer.parseInt(value);
				if (!GenericValidator.maxValue(intValue, max)) {
					rejectValue(errors, field, va, max);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, max);
				return false;
			}
		}
		return true;
	}

	public static boolean validateMinByte(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int min = Integer.parseInt(field.getVarValue("minbyte"));
			try {
				if(!StringUtil.isMinByte(value, min)) {
					rejectValue(errors, field, va, min);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, min);
				return false;
			}
		}
		return true;
	}

	public static boolean validateMaxByte(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			int max = Integer.parseInt(field.getVarValue("maxbyte"));
			try {
				if(!StringUtil.isMaxByte(value, max)) {
					rejectValue(errors, field, va, max);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, max);
				return false;
			}
		}
		return true;
	}

	/**
	 * 입력값 목록으로 제한하는 validator
	 *
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @return
	 */
	public static boolean validateInList(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if (!GenericValidator.isBlankOrNull(value)) {
			String listValue = field.getVarValue("inlist");
			String[] arrList = StringUtil.splitStringTrim(listValue, ",");
			try {
				if ( ! ArrayUtils.contains(arrList, value)) {
					rejectValue(errors, field, va, listValue);
					return false;
				}
			} catch (Exception e) {
				rejectValue(errors, field, va, listValue);
				return false;
			}
		}
		return true;
	}

	public static void rejectValue(Errors errors, Field field, ValidatorAction va, Object ... pArg) {
		String fieldCode = field.getKey();
		String errorCode = MessageUtils.getMessageKey(va, field);
		Object[] args = MessageUtils.getArgs(va, field);
		if (log.isDebugEnabled()) {
			log.debug("Rejecting value [field='{}', errorCode='{}']", fieldCode, errorCode);
		}
		List<Object> argList = new ArrayList<Object>();
		argList.addAll(Arrays.asList(args));
		argList.addAll(Arrays.asList(pArg));
		errors.rejectValue(fieldCode, errorCode, argList.toArray(), errorCode);
	}

	/**
	 * Validate required if.
	 *
	 * @param bean the bean
	 * @param va the va
	 * @param field the field
	 * @param errors the errors
	 * @param validator the validator
	 * @return true, if successful
	 */
	public static boolean validateRequiredIf(Object bean, ValidatorAction va, Field field, Errors errors, Validator validator) {
		Object form = validator.getParameterValue(org.apache.commons.validator.Validator.BEAN_PARAM);
		boolean required = false;
		String value = FieldChecks.extractValue(bean, field);
		int i = 0;
		String fieldJoin = "AND";
		if (!GenericValidator.isBlankOrNull(field.getVarValue("fieldJoin"))) {
			fieldJoin = field.getVarValue("fieldJoin");
		}
		if (fieldJoin.equalsIgnoreCase("AND")) {
			required = true;
		}
		while (!GenericValidator.isBlankOrNull(field.getVarValue("field[" + i + "]"))) {
			String dependProp = field.getVarValue("field[" + i + "]");
			String dependTest = field.getVarValue("fieldTest[" + i + "]");
			String dependTestValue = field.getVarValue("fieldValue[" + i + "]");
			String dependIndexed = field.getVarValue("fieldIndexed[" + i + "]");
			if (dependIndexed == null) {
				dependIndexed = "false";
			}
			String dependVal = null;
			boolean thisRequired = false;
			if (field.isIndexed() && dependIndexed.equalsIgnoreCase("true")) {
				String key = field.getKey();
				if ((key.indexOf("[") > -1) && (key.indexOf("]") > -1)) {
					String ind = key.substring(0, key.indexOf(".") + 1);
					dependProp = ind + dependProp;
				}
			}
			dependVal = ValidatorUtils.getValueAsString(form, dependProp);
			if (dependTest.equals(FieldChecks.FIELD_TEST_NULL)) {
				thisRequired = (dependVal == null) || (dependVal.length() <= 0);
			}
			if (dependTest.equals(FieldChecks.FIELD_TEST_NOTNULL)) {
				thisRequired = (dependVal != null) && (dependVal.length() > 0);
			}
			if (dependTest.equals(FieldChecks.FIELD_TEST_EQUAL)) {
				thisRequired = dependTestValue.equalsIgnoreCase(dependVal);
			}
			if (fieldJoin.equalsIgnoreCase("AND")) {
				required = required && thisRequired;
			} else {
				required = required || thisRequired;
			}
			i++;
		}
		if (required) {
			if (GenericValidator.isBlankOrNull(value)) {
				rejectValue(errors, field, va);
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * Validate mask in list.
	 *
	 * @param bean the bean
	 * @param va the va
	 * @param field the field
	 * @param errors the errors
	 * @param validator the validator
	 * @return true, if successful
	 */
	public static boolean validateMaskIfMask(Object bean, ValidatorAction va, Field field, Errors errors, Validator validator) {
		String value = extractValue(bean, field);
		if(!GenericValidator.isBlankOrNull(value)) {
			try {
				String mimField = field.getVarValue("mimField");
				if(!GenericValidator.isBlankOrNull(mimField)) {
					String mimFieldMask = field.getVarValue("mimFieldMask");
					Object form = validator.getParameterValue("java.lang.Object");
					String mimFieldValue = ValidatorUtils.getValueAsString(form, mimField);
					if(!GenericValidator.isBlankOrNull(mimFieldValue) && GenericValidator.matchRegexp(mimFieldValue, mimFieldMask)) {
						String mimMask = field.getVarValue("mimMask");
						if(!GenericValidator.isBlankOrNull(mimMask) && !GenericValidator.matchRegexp(value, mimMask)) {
							throw new BaseException();
						}
					}
				}
			} catch (Exception e) {
				rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate required if mask.
	 *
	 * @param bean the bean
	 * @param va the va
	 * @param field the field
	 * @param errors the errors
	 * @param validator the validator
	 * @return true, if successful
	 */
	public static boolean validateRequiredIfMask(Object bean, ValidatorAction va, Field field, Errors errors, Validator validator) {
		try {
			String rimField = field.getVarValue("rimField");
			if(!GenericValidator.isBlankOrNull(rimField)) {
				String rimFieldMask = field.getVarValue("rimFieldMask");
				Object form = validator.getParameterValue("java.lang.Object");
				String rimFieldValue = ValidatorUtils.getValueAsString(form, rimField);
				if((!(GenericValidator.isBlankOrNull(rimFieldValue))) && (GenericValidator.matchRegexp(rimFieldValue, rimFieldMask))) {
					String value = extractValue(bean, field);
					if(GenericValidator.isBlankOrNull(value)) {
						throw new BaseException();
					}
				}
			}
		} catch (Exception e) {
			rejectValue(errors, field, va);
			return false;
		}
		return true;
	}

	/**
	 * Validate in code.
	 *
	 * @param bean the bean
	 * @param va the va
	 * @param field the field
	 * @param errors the errors
	 * @return true, if successful
	 */
	public static boolean validateInCode(Object bean, ValidatorAction va, Field field, Errors errors) {
		String value = extractValue(bean, field);
		if(!GenericValidator.isBlankOrNull(value)) {
			try {
				boolean isValid = false;
				String grpCd = field.getVarValue("grpCd");
				if(StringUtil.isNotEmpty(grpCd)) {
					List<Box> cdList = CdUtil.getCdList(grpCd);
					for(Box cdBox : cdList) {
						if(value.equals(cdBox.nvl("cd"))) {
							isValid = true;
							break;
						}
					}
				}
				if(!isValid) {
					throw new BaseException();
				}
			} catch (Exception e) {
				rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

//	private static boolean isString(Object obj) {
//		return (obj == null) ? true : String.class.isInstance(obj);
//	}

}
