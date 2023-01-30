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
	
	public ResErrorDto(String msgC, String msgKrnCn) {
		this.message.setMsgC(msgC);
		this.message.setMsgKrnCn(msgKrnCn);
	}
	
}
