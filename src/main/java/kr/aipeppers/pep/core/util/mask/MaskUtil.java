package kr.aipeppers.pep.core.util.mask;

public class MaskUtil {

    public static String maskPhoneNumber(String phoneNumber) {
        // 전화번호 가운데 4자리 중 마지막 2자리, 끝 4자리 중 마지막 2자리 마스킹 처리
        String[] numberArray = phoneNumber.split("-");
        String middle = numberArray[1];
        String last = numberArray[2];
        String maskedMiddle = middle.substring(0, 2) + "**";
        String maskedLast = "**" + last.substring(2, 4);
        return numberArray[0] + "-" + maskedMiddle + "-" + maskedLast;
    }

    public static String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return email;
        }

        int index = email.indexOf("@");
        if (index == -1) {
            return email;
        }

        String emailId = email.substring(0, index);
        if (emailId.length() <= 2) {
            return email;
        }

        String maskedEmailId = emailId.substring(0, 2) + "*".repeat(emailId.length() - 2);
        String maskedEmail = maskedEmailId + email.substring(index);
        return maskedEmail;
    }
}
