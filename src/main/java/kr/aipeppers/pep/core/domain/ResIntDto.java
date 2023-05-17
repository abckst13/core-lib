package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.aipeppers.pep.core.util.MsgUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@NoArgsConstructor
public class ResIntDto {
	@Schema(description = "결과코드")
	private String code = "200";
	@Schema(description = "결과메시지")
	private String msg = "성공";

	public ResIntDto(int resultCnt) {
		this.code = resultCnt > 0 ? "200" : "500";
		this.msg = resultCnt > 0 ? "success" : "failed";
	}

	public ResIntDto(int resultCnt, String msgCd) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd);
		}
	}

	public ResIntDto(int resultCnt, String msgCd, Object[] messageParams) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd, messageParams);
		}
	}

}