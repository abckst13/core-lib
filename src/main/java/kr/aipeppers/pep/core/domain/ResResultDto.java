package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.util.BeanUtil;
import kr.aipeppers.pep.core.util.MsgUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "REST API 응답 Dto", description = "REST API 응답 Dto")
@NoArgsConstructor
public class ResResultDto<T> {
	@Schema(description = "결과코드")
	private String code = "200";
//	@Schema(description = "결과메시지")
//	private String msg = "success";
	@Schema(description = "결과메시지")
	private String msg;
	@Schema(description = "결과데이터")
	private T data;
	@Schema(description = "결과페이징정보")
	private ResPageDto paginate;
	@Schema(description = "결과갯수")
	private int count;
	@Schema(description = "메세지 Dto")
	private ResCmnMsgDto cmmMsg =  new ResCmnMsgDto();
	public ResResultDto(T dto) {
		ResCmnMsgDto msgText = BeanUtil.convert(MsgUtil.getMsgBox(cmmMsg.getMsgId()), ResCmnMsgDto.class);
//		this.code = 200;
		this.msg = msgText.getMsgNm();
		this.data = dto;
	}

	public ResResultDto(T dto, ResPageDto paginateDto) {
//		this.code = 200;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(msg.getMsgC()), ResCmnMsgDto.class);
		ResCmnMsgDto msgText = BeanUtil.convert(MsgUtil.getMsgBox(cmmMsg.getMsgId()), ResCmnMsgDto.class);
		this.msg = msgText.getMsgNm();
		this.data = dto;
		this.paginate = paginateDto;
		this.count = paginateDto.getTotalRecords();
	}
}
