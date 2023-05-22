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
				throw new BizException("E129"); // 부적절한 단어의 포함으로 사용이 불가능합니다. (금칙어 사용)
			}
		}
		return true;
	}

	/**
	 * @Method Name : lenChk
	 * @param text
	 * @return
	 */
	public static boolean lenChk(String text) {
		Boolean result = false;
		if (text.length() > 2 && text.length() < 9) {
			result = true;
		} else {
			throw new BizException("E131"); // 2자 이상 8자 이하의 닉네임을 사용해 주세요.
		}
		return result;

	}

	/**
	 * @Method Name : koreanInitial
	 * @param text
	 * @return
	 */
	public static boolean koreanInitial(String text ) {
		Boolean result = true;
		 char[] koreanInitials = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

	        for (char initial : koreanInitials) {
	            if (text.indexOf(initial) != -1) {
	                throw new BizException("E132", new String[] {"닉네임"}); //올바르지 않은 형식의 {0}입니다.
	            }
	        }
		return result ;
	}

    /**
     * @Method Name : checkEmailFormat
     * @param email
     * @return
     */
	public static boolean checkEmailFormat(String email) {
    	String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Boolean val = email.matches(regex);
        if (val == false) {
        	throw new BizException("E132", new String[] {"이메일"}); //올바르지 않은 형식의 {0}입니다.
        }
        return true;
    }

    /**
     * @Method Name : checkUpperCaseAfterAt
     * @param email
     * @return
     */
	public static boolean checkUpperCaseAfterAt(String email) {
        int atIndex = email.indexOf("");
        Boolean result = false;
        if (atIndex != -1 && atIndex + 1 < email.length()) {
            String afterAt = email.substring(atIndex + 1);
            log.debug("afterAt: {}", afterAt);
            if ( afterAt.matches(".*[A-Z].*") == true ) {
            	throw new BizException("E132", new String[] {"이메일"}); //올바르지 않은 형식의 {0}입니다.
            }
        }
        return false;
    }
}
