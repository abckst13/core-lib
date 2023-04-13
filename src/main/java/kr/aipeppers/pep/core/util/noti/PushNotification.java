package kr.aipeppers.pep.core.util.noti;

import java.util.List;

import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.domain.ClientApiDto;
import kr.aipeppers.pep.core.exception.BizException;
import kr.aipeppers.pep.core.util.BoxUtil;
import kr.aipeppers.pep.core.util.HttpClient;
import kr.aipeppers.pep.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushNotification {

	public static void pushNotification(List<Box> boxList) {
		Box rqtHeaderBox = new Box();
		rqtHeaderBox.put("Api-key", "156c4675-9608-4591-2022-08161");
		rqtHeaderBox.put("Content-Type", "application/json");
		String json = JsonUtil.toJson(boxList);
		log.debug("json: {}", json);

		ClientApiDto clientApiDto = new ClientApiDto();
//		clientApiDto.setRqtParamBox(box);
		clientApiDto.setRqtContent(json);
		clientApiDto.setRqtHeaderBox(rqtHeaderBox);
		clientApiDto.setScheme("https");
		clientApiDto.setHost("api.aimeplz.com");
		clientApiDto.setPort(50443);
		clientApiDto.setTimeout(30000);
		clientApiDto.setPath("apis/callPushNotification");
		HttpClient.sendPost(clientApiDto);
		if (null == clientApiDto.getException() && null != clientApiDto.getRpyContent()) { //성공시
			Box resBox = BoxUtil.toBox(clientApiDto.getRpyContent());
			if (resBox.eq("code", "200")) {
				log.debug(">>>>>" + resBox);
			}
		} else {
			log.debug("error: {}", clientApiDto.getException().getMessage());
			throw new BizException("F600", new String[]{"push server"}); //{0} 연동 오류
		}
    }
}

