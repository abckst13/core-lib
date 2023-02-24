package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ResErrorDto {
	private boolean success = false;
	private ResCmnMsgDto message = new ResCmnMsgDto();

	public ResErrorDto(String msgId, String msgNm) {
		this.message.setMsgId(msgId);
		this.message.setMsgNm(msgNm);
	}

}
