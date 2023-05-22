package kr.aipeppers.pep.core.util.mask;

import kr.aipeppers.pep.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterUtil {

	/**
	 * @Method Name : usernameFilter
	 * @param filterText
	 */
	public static Boolean filterText(String filterText, String filter) {
		String[] ArraysStr = filter.split(",");
		for (String text : ArraysStr) {
			if (text.trim().contains(filterText.replaceAll(" ", ""))) {
				throw new BizException("E109");
			}
		}
		return true;
	}
}
