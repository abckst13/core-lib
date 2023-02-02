package kr.aipeppers.pep.core.domain;

import kr.aipeppers.pep.core.util.BeanUtil;
import kr.aipeppers.pep.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "통합플랫폼 REST API 응답 Dto", description = "통합플랫폼 REST API 응답 Dto")
@NoArgsConstructor
public class ResResultDto<T> {
	@Schema(description = "결과코드")
	private int code;
	@Schema(description = "결과메시지")
	private String msg = "success";
//	@Schema(description = "결과메시지")
//	private ResCmnMsgDto message = new ResCmnMsgDto();
	@Schema(description = "결과데이터")
	private T data;

	public ResResultDto(T dto) {
		this.data = dto;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(msg.getMsgC()), ResCmnMsgDto.class);
	}

}
