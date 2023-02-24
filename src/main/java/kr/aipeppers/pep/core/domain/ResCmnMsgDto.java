package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.util.MsgUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "REST API 응답 결과 메시지 Dto", description = "REST API 응답 결과 메시지 Dto")
@NoArgsConstructor
public class ResCmnMsgDto {
	@Schema(description = "메시지ID")
	private String msgId = CmnConst.ResCd.RES_CD_SUCCESS;
	@Schema(description = "메시지내용")
	private String msgNm = MsgUtil.getMsg(msgId);
	@Schema(description = "메시지상세")
	private String msgDesc;
	@Schema(description = "메시지유형")
	private String msgType; //S:성공, I:정보, W:경고, E:업무오류, F:시스템에러

	public ResCmnMsgDto(String msgId, String msgNm) {
		this.msgId = msgId;
		this.msgNm = msgNm;
	}

}