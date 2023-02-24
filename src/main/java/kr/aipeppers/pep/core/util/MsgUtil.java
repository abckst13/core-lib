package kr.aipeppers.pep.core.util;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.aipeppers.pep.core.biz.MsgLocator;
import kr.aipeppers.pep.core.data.Box;

@Component
public class MsgUtil {

	private static MsgLocator msgLocator;

	@Autowired(required=true)
	private MsgUtil(MsgLocator msgLocator) {
		MsgUtil.msgLocator = msgLocator;
	}

	public static Box getMsgBox(String msgId) {
		if(StringUtil.isEmpty(msgId)) {
			return null;
		}
		return msgLocator.getMsgBox(msgId);
	}


	public static String getMsg(String msgId) {
		Box msgBox = getMsgBox(msgId);
		String msgNm = null;
		if(msgBox != null) {
			msgNm = msgBox.nvl("msgNm");
		}
		return msgNm;
	}

	public static String getMsg(String msgId, Object[] messageParams) {
		String msgNm = getMsg(msgId);
		if(StringUtil.isNotEmpty(msgNm)) {
			if(messageParams != null && messageParams.length > 0) {
				msgNm = MessageFormat.format(msgNm, messageParams);
			}
		}
		return msgNm;
	}
}
