package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "REST API 응답 Dto", description = "REST API 응답 Dto")
@NoArgsConstructor
public class ResResultDto<T> {
	@Schema(description = "결과코드")
	private int code = 200;
	@Schema(description = "결과메시지")
	private String msg = "success";
//	@Schema(description = "결과메시지")
//	private ResCmnMsgDto msg = new ResCmnMsgDto();
	@Schema(description = "결과데이터")
	private T data;
	@Schema(description = "결과페이징정보")
	private ResPageDto paginate;
	@Schema(description = "결과갯수")
	private int count;

	public ResResultDto(T dto) {
//		this.code = 200;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(msg.getMsgC()), ResCmnMsgDto.class);
		this.data = dto;
	}

	public ResResultDto(T dto, ResPageDto paginateDto) {
//		this.code = 200;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(msg.getMsgC()), ResCmnMsgDto.class);
		this.data = dto;
		this.paginate = paginateDto;
		this.count = paginateDto.getTotalRecords();
	}
}
