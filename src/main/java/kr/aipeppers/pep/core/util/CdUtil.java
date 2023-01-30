package kr.aipeppers.pep.core.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.biz.CdLocator;
import kr.aipeppers.pep.core.data.Box;

@Component
public class CdUtil {

	private static CdLocator cdLocator;

	@Autowired(required=true)
	private CdUtil(CdLocator cdLocator) {
		CdUtil.cdLocator = cdLocator;
	}

	public static List<Box> getCdList(String grpCd) {
		if(StringUtil.isEmpty(grpCd)) {
			return null;
		}
		return cdLocator.getCdList(grpCd);
	}

	public static Box getCd(String grpCd, String cd) {
		if(StringUtil.isEmpty(grpCd)) {
			return null;
		}
		if(StringUtil.isEmpty(cd)) {
			return null;
		}
		return cdLocator.getCdBox(grpCd, cd);
	}

	/**
	 * 통합코드한글명
	 * @param grpCd
	 * @return
	 */
	public static String getGrpNm(String grpCd) {
		List<Box> cdList = getCdList(grpCd);
		String cdValue = null;
		if(cdList != null && cdList.size() > 0) {
			cdValue = cdList.get(0).nvl("itcKnm");
		}
		return cdValue;
	}

	/**
	 * 통합코드 인스턴스명
	 * @param grpCd
	 * @param cd
	 * @return
	 */
	public static String getCdNm(String grpCd, String cd) {
		Box cdBox = getCd(grpCd, cd);
		String cdValue = null;
		if(cdBox != null && !cdBox.isEmpty()) {
			cdValue = cdBox.nvl("itcInsNm");
		}
		return cdValue;
	}

//	public static String getItcKnm(String grpCd) {
//		return getGrpNm(grpCd);
//	}
//
//	public static String getItcInsNm(String grpCd, String cd) {
//		return getCdNm(grpCd,cd);
//	}

//	public static String getCdVal1(String grpCd, String cd) {
//		Box cdBox = getCd(grpCd, cd);
//		String cdValue = null;
//		if(cdBox != null) {
//			cdValue = cdBox.nvl("cdVal1");
//		}
//		return cdValue;
//	}

//	public static String[] getCdValAry(String grpCd, String cd) {
//		Box cdBox = getCd(grpCd, cd);
//		String[] cdValue = new String[5];
//		if(cdBox != null) {
//			cdValue[0] = cdBox.nvl("cdVal1");
//			cdValue[1] = cdBox.nvl("cdVal2");
//			cdValue[2] = cdBox.nvl("cdVal3");
//			cdValue[3] = cdBox.nvl("cdVal4");
//			cdValue[4] = cdBox.nvl("cdVal5");
//		}
//		return cdValue;
//	}
}
