package kr.aipeppers.pep.core.util;

import kr.aipeppers.pep.core.data.Box;

public class RrnoUtil {
	
	/**
	 * 주민번호 정보 조회
	 * box 형태로 리턴
	 * 1. sexC(성별) : 남자 - 1 / 여자 - 2
	 * 2. bird(생년월일) : YYYYMMDD 형태
	 * 3. frnrDv(내외국인구분) : 내국인 - 1 / 외국인 - 2
	 * @name_ko          주민번호 정보 조회
	 * @param   Rrno(주민번호)
	 *                       Description
	 * @return           주민번호 정보 조회
	 */
	public static Box getRrnoChk(String Rrno) {
		Box dataBox = new Box();
		String rrno = Rrno.replace("-","");
		int rrnoChk = Integer.parseInt(rrno.substring(6,7));
		
		// 성별 구하기 ( sexC : 남자 - 1 / 여자 - 2 )
		if(rrnoChk % 2> 0) {
			dataBox.put("sexC", "1");
		}else if(rrnoChk % 2 == 0) {
			dataBox.put("sexC", "2");
		}else {
			dataBox.put("sexC", "");
		}
		
		// 생년월일 ( YYYYMMDD 형태 )
		// 내외국인구분 ( frnrDv : 내국인 - 1 / 외국인 - 2 )
		switch(rrnoChk) {
			case 1:
			case 2:
				// 1900년대 출생한 남/여 내국인
				dataBox.put("bird", "19"+rrno.substring(0,6));
				dataBox.put("frnrDv", "1");
				break;
			case 3:
			case 4:
				// 2000년대 출생한 남/여 내국인
				dataBox.put("bird", "20"+rrno.substring(0,6));
				dataBox.put("frnrDv", "1");
				break;
			case 5:
			case 6:
				// 1900년대 출생한 남/여 외국인
				dataBox.put("bird", "19"+rrno.substring(0,6));
				dataBox.put("frnrDv", "2");
				break;
			case 7:
			case 8:
				// 2000년대 출생한 남/여 외국인
				dataBox.put("bird", "20"+rrno.substring(0,6));
				dataBox.put("frnrDv", "2");
				break;
			case 9:
			case 0:
				// 1800년대 출생한 남/여 내국인
				dataBox.put("bird", "18"+rrno.substring(0,6));
				dataBox.put("frnrDv", "1");
				break;
			default:
				// 그외
				dataBox.put("bird", "");
				dataBox.put("frnrDv", "");
				break;
		}

		return dataBox;
	}
}
