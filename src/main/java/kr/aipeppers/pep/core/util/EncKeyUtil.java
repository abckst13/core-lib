package kr.aipeppers.pep.core.util;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.biz.EncKeyLocator;
import kr.aipeppers.pep.core.data.Box;


@Component
public class EncKeyUtil {

	private static EncKeyLocator encKeyLocator;

	@Autowired(required=true)
	private EncKeyUtil(EncKeyLocator encKeyLocator) {
		EncKeyUtil.encKeyLocator = encKeyLocator;
	}

	public static List<Box> getEncKeyBox() {
//		if(box.isEmpty()) {
//			return null;
//		}
		return encKeyLocator.getEncKeyBox();
	}

}
