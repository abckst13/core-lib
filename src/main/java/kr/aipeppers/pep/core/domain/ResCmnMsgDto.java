package kr.aipeppers.pep.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel(value = "통합플랫폼 REST API 응답 결과 메시지 Dto", description = "통합플랫폼 REST API 응답 결과 메시지 Dto")
@NoArgsConstructor
public class ResCmnMsgDto {
	@ApiModelProperty("메시지코드")
	private String msgC = CmnConst.ResCd.RES_CD_SUCCESS;
	@ApiModelProperty("메시지내용")
	private String msgKrnCn = MsgUtil.getMsg(msgC);
//	private String msgC; //메시지코드
//	private String msgTpc; //S:system, D:database, C:customor, B:business
//	private String msgKndC; //W:warning, I:info, E:error, N:normal
//	private String appC;
//	private String msgChnlDvC;
//	private String metaTrsDt;
//	private String msgKrnCn; //메시지내용
//	private String msgEnglCn; //메시지내용(영문:미사용)
//	private int resultCnt = 1;

//	public ResCmnMsgDto(int resultCnt) {
//		this.resultCnt = resultCnt;
//	}
	
	public ResCmnMsgDto(String msgC, String msgKrnCn) {
		this.msgC = msgC;
		this.msgKrnCn = msgKrnCn;
//		this.msgC = MsgUtil.getMsgBox(resCd).nvl("msgC");
//		this.msgTpc =  MsgUtil.getMsgBox(resCd).nvl("msgTpc");
//		this.msgKndC = MsgUtil.getMsgBox(resCd).nvl("msgKndC");
//		this.appC =  MsgUtil.getMsgBox(resCd).nvl("appC");
//		this.msgChnlDvC = MsgUtil.getMsgBox(resCd).nvl("msgChnlDvC");
//		this.metaTrsDt = MsgUtil.getMsgBox(resCd).nvl("metaTrsDt");
//		this.msgKrnCn = MsgUtil.getMsgBox(resCd).nvl("msgKrnCn");
//		this.msgEnglCn = MsgUtil.getMsgBox(resCd).nvl("msgEnglCn");
	}

//	public ResCmnMsgDto(String resCd, String resMsg, int resultCnt) {
//		this.resCd = resCd;
//		this.resMsg = resMsg;
//		this.resultCnt = resultCnt;
//	}

}