package kr.aipeppers.pep.core.util.mask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.aipeppers.pep.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MaskUtil {

    public static String maskPhoneNumber(String phoneNumber) {
        // 전화번호 가운데 4자리 중 마지막 2자리, 끝 4자리 중 마지막 2자리 마스킹 처리
        String middle = phoneNumber.substring(3,5);
        String last = phoneNumber.substring(6,10);
        String maskedMiddle = middle.substring(0, 2) + "**";
        String maskedLast =  last.substring(0, 2) + "**";
        return phoneNumber.substring(0,2) + "-" + maskedMiddle + "-" + maskedLast;
    }

    public static String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
        	throw new BizException("E107" , new String[] {"이메일이"});  // {0} 존재하지 않습니다.
        }
        int index = email.indexOf("@");
        if (index == -1) {
        	throw new BizException("E112"); // 사용할 수 없는 이메일 입니다.
        }
        String emailId = email.substring(0, index);
        if (emailId.length() <= 2) {
        	throw new BizException("E112"); // 사용할 수 없는 이메일 입니다.
        }
        String maskedEmailId = emailId.substring(0, 2) + "*".repeat(emailId.length() - 2);
        String maskedEmail = maskedEmailId + email.substring(index);
        return maskedEmail;
    }

    public static boolean passwordChk(String password) {
    	String strPattern = "^[a-zA-Z0-9~!@#$%^&*|\\\\\\\\'\\\\\\\";:/?^=+_()<>-]{8,16}$";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(password);
    	return matcher.matches();
    }
}
