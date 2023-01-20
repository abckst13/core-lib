package kr.gscaltex.gsc.core.domain;

import kr.gscaltex.gsc.core.util.BeanUtil;
import kr.gscaltex.gsc.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "통합플랫폼 REST API 응답 Dto", description = "통합플랫폼 REST API 응답 Dto")
@NoArgsConstructor
public class ResResultDto<T> {
	@Schema(description = "결과갯수")
	private int resultCnt;
	@Schema(description = "성공여부")
	private boolean success = false;
	@Schema(description = "결과데이터")
	private T result;
	@Schema(description = "미션데이터")
	private ResMissionResultDto missionResult;
	@Schema(description = "결과메시지")
	private ResCmnMsgDto message = new ResCmnMsgDto();

	public ResResultDto(T dto) {
		this.result = dto;
		this.resultCnt = dto == null ? 0 : 1;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
	}

	public ResResultDto(T dto, ResMissionResultDto missionResult) {
		this.result = dto;
		this.missionResult = missionResult;
		this.resultCnt = dto == null ? 0 : 1;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
	}
}
