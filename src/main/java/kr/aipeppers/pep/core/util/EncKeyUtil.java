package kr.aipeppers.pep.core.util;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.biz.EncKeyLocator;
import kr.aipeppers.pep.core.data.Box;


@Component
public class EncKeyUtil {

	private static EncKeyLocator encKeyLocator;

	/**
	 * @param encKeyLocator
	 */
	private EncKeyUtil(EncKeyLocator encKeyLocator) {
		EncKeyUtil.encKeyLocator = encKeyLocator;
	}

	/**
	 * @Method Name : getEncKeyBox
	 * @return
	 */
	public static List<Box> getEncKeyBox() {
//		if(box.isEmpty()) {
//			return null;
//		}
		return encKeyLocator.getEncKeyBox();
	}

}
