package kr.aipeppers.pep.core.util.mask;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import kr.aipeppers.pep.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterUtil {

	@Autowired
	SqlSessionTemplate dao;

	/**
	 * @Method Name : usernameFilter
	 * @param box
	 */
	public static boolean usernameFilter(String filterText) {
		String settingText = dao.selectOne("cmn.setFilterView");
		String[] ArraysStr = settingText.split(",");
		for(String text : ArraysStr) {
			if (text.trim().contains(filterText.replaceAll(" ", ""))) { // description 의 내용을 db tb_setting 의 text 내용들과 비교하여 욕설 등 비속어에 속한 단어가 포함되어 있다면
				throw new BizException("E109"); //E109	유효하지 않은 접근입니다.
			}
		}
		return true;
	}
}
