package kr.aipeppers.pep.core.domain;

import java.util.List;

import kr.aipeppers.pep.core.util.BeanUtil;
import kr.aipeppers.pep.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "REST API 응답 List Dto", description = "REST API 응답 List Dto")
@NoArgsConstructor
public class ResListDto<T> {
//	@Schema(description = "결과코드")
//	private String msgCd;
	@Schema(description = "결과코드")
	private String code = "200";
	@Schema(description = "결과메시지")
	private String msg = "성공";
	@Schema(description = "결과갯수")
	private int count;
	@Schema(description = "결과리스트")
	private List<T> data;
	@Schema(description = "결과페이징정보")
	private ResPageDto paginate;

	public ResListDto(List<T> list) {
		this.data = list;
		this.count = list.size();
	}

	public ResListDto(List<T> list, String msgCd) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd);
		}
		this.data = list;
		this.count = list.size();
	}

	public ResListDto(List<T> list, String msgCd, Object[] messageParams) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd, messageParams);
		}
		this.data = list;
		this.count = list.size();
	}

	public ResListDto(List<T> list, ResPageDto paginateDto) {
		this.data = list;
		this.paginate = paginateDto;
		this.count = paginateDto.getTotalRecords();
	}

	public ResListDto(List<T> list, ResPageDto paginateDto, String msgCd) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd);
		}
		this.data = list;
		this.paginate = paginateDto;
		this.count = paginateDto.getTotalRecords();
	}

	public ResListDto(List<T> list, ResPageDto paginateDto, String msgCd, Object[] messageParams) {
		if (msgCd.startsWith("I")) {
			this.msg =  MsgUtil.getMsg(msgCd);
		}
		this.data = list;
		this.paginate = paginateDto;
		this.count = paginateDto.getTotalRecords();
	}
}
