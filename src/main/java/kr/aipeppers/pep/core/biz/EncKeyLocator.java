package kr.aipeppers.pep.core.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.data.Box;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EncKeyLocator implements ApplicationListener<ContextRefreshedEvent> {
	private boolean initialized = false;
	List<Box> encKeyMap = new ArrayList<>();

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate dao;

	public List<Box> getEncKeyBox(Box box) {
		return encKeyMap;
	}

	public void initialize() {
		Box encKeyBox = new Box();
		//디비에 저장된 api 셋팅값 가져옴
		String[] title = {"CRYPT_PASS" , "CRYPT_IV" , "G_BSZUSER_KEY" , "G_BSZIV"};
		encKeyBox.put("title", title);
		List<Box> encKeyList = dao.selectList("cmn.apiSettingList", encKeyBox);
		for(Box innrBox : encKeyList) {
			if(encKeyList != null && !encKeyList.isEmpty()) {
				encKeyMap.add(innrBox);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		initialize();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!initialized) {
			initialize();
			initialized = true;
		}
	}
}
