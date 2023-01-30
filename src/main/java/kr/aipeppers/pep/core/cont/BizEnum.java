package kr.aipeppers.pep.core.cont;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BizEnum {
	public enum DataState {
		DECODED, UNCONFIRMED_HR, UNCONFIRMED_LR, FETCHED, IN_REVIEW, CONFIRMED, REJECTED
	}

	@Getter
	@AllArgsConstructor
	@Deprecated
	public enum ErrorCode {
		// Success
		S200(200, "S200", "성공")

		// 4XX Client errors
		,F400(400, "F400", "Bad Request")
		,F401(401, "F401", "UNAUTHORIZED")
		,F403(403, "F403", "FORBIDDEN")
		,F404(404, "F404", "NOT FOUND")

		// 5XX Server errors
		,F500(500, "F500", "시스템오류")
		,F501(501, "F501", "SQL 실행오류 ({0})")
		,F502(502, "F502", "세션 만료")
		,F503(503, "F503", "중복 오류")
		,F504(504, "F504", "Gateway Timeout")

		// 600
		,F600(600, "F600", "{0} 연동 오류")

		//END
		;

		private int httpStatusCode;
		private String code;
		private String msg;

	}
	
	/**
	 * 암복호화 타입 Enum
	 * @author Y5004160
	 */
	@Getter
	@AllArgsConstructor
	public enum CryptoType {
		// 암호화
		ENC("ENC")

		// 복호화
		,DEC("DEC")
		
		;

		private String cryptoType;

	}
	
	
	/**
	 * MethodType 어노테이션 파라미터 Enum 
	 * @author Y5004160
	 *
	 */
	@Getter
	@AllArgsConstructor
	public enum MethodType {
		//: 조회
		 SELECT("S") 
		//: 수정
		,UPDATE("U") 
		//: 삭제
		,DELETE("D") 
		//: 출력
		,PRINT("P") 
		//: 입력
		,INPUT("I") 
		//: 엑셀다운로드
		,DOWNLOAD("L") 
		//: 검증 (Verify)
		,VERIFY("V") 
		//: 계산 (Calculation)
		,CALCULATE("C") 
		//: 발송/통지(Notice)
		,NOTICE("N") 
		
		;
		
		private String code;
		


	}


}
