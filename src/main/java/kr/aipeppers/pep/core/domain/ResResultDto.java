package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.aipeppers.pep.core.util.MsgUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Data
@ApiModel(value = "REST API 응답 Dto", description = "REST API 응답 Dto")
@NoArgsConstructor
public class ResResultDto<T> {
//	@Schema(description = "결과코드")
//	private String msgCd;
	@Schema(description = "성공여부")
	private String code = "200";
	@Schema(description = "결과메시지")
	private String msg = "성공";
	@Schema(description = "결과데이터")
	private T data;
//	@Schema(description = "결과페이징정보")
//	private ResPageDto paginate;
//	@Schema(description = "결과갯수")
//	private int count;

	public ResResultDto(T dto) {
		this.data = dto;
	}

	public ResResultDto(T dto, String msgCd) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd);
		}
		this.data = dto;
	}

	public ResResultDto(T dto, String msgCd, Object[] messageParams) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd, messageParams);
		}
		this.data = dto;
	}

//	public ResResultDto(T dto, ResPageDto paginateDto) {
//		this.data = dto;
//		this.paginate = paginateDto;
//		this.count = paginateDto.getTotalRecords();
//	}
//	public ResResultDto(T dto, ResPageDto paginateDto, String msgCd) {
//		if (msgCd.startsWith("I")) {
//			this.msg =  MsgUtil.getMsg(msgCd);
//		}
//		this.data = dto;
//		this.paginate = paginateDto;
//		this.count = paginateDto.getTotalRecords();
//	}
//	public ResResultDto(T dto, ResPageDto paginateDto, String msgCd, Object[] messageParams) {
//		if (msgCd.startsWith("I")) {
//			this.msg =  MsgUtil.getMsg(msgCd, messageParams);
//		}
//		this.data = dto;
//		this.paginate = paginateDto;
//		this.count = paginateDto.getTotalRecords();
//	}
}
