package kr.aipeppers.pep.core.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
	ConcurrentMap<String, ConcurrentMap<String, Box>> encMap = new ConcurrentHashMap<String, ConcurrentMap<String, Box>>();

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate dao;

	public Box getEncKeyBox(String title) {
		ConcurrentMap<String, Box> encKeyMap = encMap.get(title);
		Box keyBox = null;
		if(title != null && encKeyMap != null) {
			keyBox = encKeyMap.get(title);
		}
		return keyBox;
	}

	/**
	 * @Method Name : initialize
	 */
	public void initialize() {
		Box encKeyBox = new Box();
		//디비에 저장된 api 셋팅값 가져옴
		String[] title = {"CRYPT_PASS" , "CRYPT_IV" , "G_BSZUSER_KEY" , "G_BSZIV"};
		encKeyBox.put("title", title);
//		List<Box> encKeyList = dao.selectList("cmn.apiSettingList", encKeyBox);
//		Box innerBox = null;
//		ConcurrentMap<String, Box> encKeyDetailMap = null;
//		for(int i=0, s=encKeyList.size(); i<s; i++) {
//			innerBox = encKeyList.get(i);
//			encKeyDetailMap = encMap.get(innerBox.nvl("title"));
//			if(encKeyDetailMap == null) {
//				encKeyDetailMap = new ConcurrentHashMap<String, Box>();
//				encMap.put(innerBox.nvl("title"), encKeyDetailMap);
//			}
//			encKeyDetailMap.put(innerBox.nvl("title"), innerBox);
//		}

	}

	/**
	 * @Method Name : afterPropertiesSet
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		initialize();
	}

	/**
	 *@Method Name : onApplicationEvent
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!initialized) {
			initialize();
			initialized = true;
		}
	}
}
