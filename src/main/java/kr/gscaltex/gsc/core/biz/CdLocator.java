package kr.gscaltex.gsc.core.biz;

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

import kr.gscaltex.gsc.core.data.Box;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
//public class CdLocator implements ApplicationListener<ContextRefreshedEvent> {
public class CdLocator {
	private boolean initialized = false;
	private String cdData;
	private static final String CD_GROUP_KEY = "_ROOT_";
	ConcurrentMap<String, CopyOnWriteArrayList<Box>> cdListMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<Box>>();
	ConcurrentMap<String, ConcurrentMap<String, Box>> cdMap = new ConcurrentHashMap<String, ConcurrentMap<String, Box>>();

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate dao;

	public List<Box> getCdList(String grpCd) {
		return cdListMap.get(grpCd);
	}

	public Box getCdBox(String grpCd, String cd) {
		ConcurrentMap<String, Box> cdDetailMap = cdMap.get(grpCd);
		Box cdBox = null;
		if(cd != null && cdDetailMap != null) {
			cdBox = cdDetailMap.get(cd);
		}
		return cdBox;
	}

	public void initialize() {

		List<Box> cdList = dao.selectList("cmn.cdTreeList");
		Box cdBox = null;
		ConcurrentMap<String, Box> cdDetailMap = null;
		CopyOnWriteArrayList<Box> cdDetailList = null;
		for(int i=0, s=cdList.size(); i<s; i++) {
			cdBox = cdList.get(i);
			cdDetailMap = cdMap.get(cdBox.nvl("grpCd"));
			if(cdDetailMap == null) {
				cdDetailMap = new ConcurrentHashMap<String, Box>();
				cdMap.put(cdBox.nvl("grpCd"), cdDetailMap);
			}
			cdDetailMap.put(cdBox.nvl("cd"), cdBox);
			if(CD_GROUP_KEY.equals(cdBox.nvl("grpCd"))) {
				if("Y".equals(cdBox.nvl("useYn"))) {
					cdListMap.put(cdBox.nvl("cd"), new CopyOnWriteArrayList<Box>());
				}
				continue;
			}
			cdDetailList = cdListMap.get(cdBox.nvl("grpCd"));
			if(cdDetailList != null) {
				if("Y".equals(cdBox.nvl("useYn"))) {
					cdDetailList.add(cdBox);
				}
			}
		}
		if (null != cdList && !cdList.isEmpty()) {
			try {
				this.fileCreate(cdList);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public String getData() {
		return cdData;
	}

	public void fileCreate(List<Box> list) throws Exception {

		StringBuffer sb = new StringBuffer();
		StringBuffer rows = new StringBuffer();
		sb.append("window['gCdList'] =\n");
		sb.append("	[\n");
		sb.append("		%rows%");
		sb.append("	]\n");
		String line = "	{'grpCd': '%grpCd%', 'grpCdNm': '%grpCdNm%', 'cd': '%cd%', 'cdNm': '%cdNm%', 'nm': '%cdNm%', 'useYn': '%useYn%', 'useYnNm': '%useYnNm%'"
						+ ", 'cdDesc': '%cdDesc%', 'cdVal1': '%cdVal1%', 'cdVal2': '%cdVal2%', 'cdVal3': '%cdVal3%', 'ord': '%ord%'},\n";
		for (int idx = 0; idx < list.size(); idx++) {
			String str = "";
			Box inBox = (Box)list.get(idx);
			if (null != inBox) {
				str = line.replaceAll("%grpCd%", inBox.nvl("grpCd").trim())
						.replaceAll("%grpCdNm%", inBox.nvl("grpCdNm").trim())
						.replaceAll("%cd%", inBox.nvl("cd").trim())
						.replaceAll("%cdNm%", inBox.nvl("cdNm").trim())
						.replaceAll("%useYn%", inBox.nvl("useYn").trim())
						.replaceAll("%useYnNm%", inBox.nvl("useYnNm").trim())
						.replaceAll("%cdDesc%", inBox.nvl("cdDesc").trim())
						.replaceAll("%cdVal1%", inBox.nvl("cdVal1").trim())
						.replaceAll("%cdVal2%", inBox.nvl("cdVal2").trim())
						.replaceAll("%cdVal3%", inBox.nvl("cdVal3").trim())
						.replaceAll("%ord%", inBox.nvl("ord").trim());
				rows.append(str);
			}
		}
		cdData = sb.toString().replaceAll("%rows%", rows.toString());
	}


	public void afterPropertiesSet() throws Exception {
		initialize();
	}

//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		if (!initialized) {
//			initialize();
//			initialized = true;
//		}
//	}
}
