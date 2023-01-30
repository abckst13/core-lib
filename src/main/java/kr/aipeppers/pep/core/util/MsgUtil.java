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

	public static Box getMsgBox(String msgC) {
		if(StringUtil.isEmpty(msgC)) {
			return null;
		}
		return msgLocator.getMsgBox(msgC);
	}


	public static String getMsg(String msgC) {
		Box msgBox = getMsgBox(msgC);
		String msgValue = null;
		if(msgBox != null) {
			msgValue = msgBox.nvl("msgKrnCn");
		}
		return msgValue;
	}

	public static String getMsg(String msgC, Object[] messageParams) {
		String msg = getMsg(msgC);
		if(StringUtil.isNotEmpty(msg)) {
			if(messageParams != null && messageParams.length > 0) {
				msg = MessageFormat.format(msg, messageParams);
			}
		}
		return msg;
	}
}
