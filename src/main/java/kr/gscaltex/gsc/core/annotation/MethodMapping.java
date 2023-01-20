package kr.gscaltex.gsc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.gscaltex.gsc.core.cont.BizEnum.MethodType;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodMapping {

	 
	/**
	 * 메서드타입.
	 * 조회:SELECT("S") 
	 * 수정:UPDATE("U") 
	 * 삭제:DELETE("D") 
	 * 출력:PRINT("P") 
	 * 입력:INPUT("I") 
	 * 엑셀다운로드:DOWNLOAD("L") 
	 * 검증:VERIFY("V") 
	 * 계산:CALCULATE("C") 
	 * 발송/통지:NOTICE("N") 
	 *
	 * @return MethodType Enum
	 */

	MethodType value();
}
