package kr.aipeppers.pep.core.util.mask;

public class maskUtil {

    public static String maskPhoneNumber(String phoneNumber) {
        // 전화번호 가운데 4자리 중 마지막 2자리, 끝 4자리 중 마지막 2자리 마스킹 처리
        String[] numberArray = phoneNumber.split("-");
        String middle = numberArray[1];
        String last = numberArray[2];
        String maskedMiddle = middle.substring(0, 2) + "**";
        String maskedLast = "**" + last.substring(2, 4);
        return numberArray[0] + "-" + maskedMiddle + "-" + maskedLast;
    }

    public static String maskEmailAddress(String emailAddress) {
        // 이메일 주소 ID 앞 2자리를 제외한 나머지 모두 마스킹 처리
        String[] addressArray = emailAddress.split("@");
        String id = addressArray[0];
        String maskedId = id.substring(0, 2) + "*******";
        return maskedId + "@" + addressArray[1];
    }
}
