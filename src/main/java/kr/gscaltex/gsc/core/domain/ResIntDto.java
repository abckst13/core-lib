package kr.gscaltex.gsc.core.domain;

import kr.gscaltex.gsc.core.util.BeanUtil;
import kr.gscaltex.gsc.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@NoArgsConstructor
public class ResIntDto {
	@Schema(description = "결과갯수")
	private int resultCnt;
	@Schema(description = "성공여부")
	private boolean success = false;
	@Schema(description = "미션데이터")
	private ResMissionResultDto missionResult;
	@Schema(description = "결과메시지")
	private ResCmnMsgDto message = new ResCmnMsgDto();

	public ResIntDto(int resultCnt) {
		this.resultCnt = resultCnt;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
	}

	public ResIntDto(int resultCnt, ResMissionResultDto missionResult) {
		this.resultCnt = resultCnt;
		this.missionResult = missionResult;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
	}

}