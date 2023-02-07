package kr.aipeppers.pep.core.domain;

import java.util.List;

import kr.aipeppers.pep.core.util.BeanUtil;
import kr.aipeppers.pep.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ResListDto<T> {
	@Schema(description = "결과코드")
	private int code;
	@Schema(description = "결과메시지")
	private String msg = "success";
//	@Schema(description = "결과메시지")
//	private ResCmnMsgDto message = new ResCmnMsgDto();
	@Schema(description = "결과갯수")
	private int count;
	@Schema(description = "결과리스트")
	private List<T> data;
	@Schema(description = "결과페이징정보")
	private ResPageDto paginate;
//	@Schema(description = "결과메시지")
//	private ResCmnMsgDto message = new ResCmnMsgDto();

	public ResListDto(List<T> list) {
		this.count = list.size();
		this.data = list;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
//		this.message.setResultCnt(this.resultCnt);
	}

	public ResListDto(List<T> list, ResPageDto paginateDto) {
//		this.resultCnt = paginateDto.getTotalRecords();
		this.count = paginateDto.getTotalRecords();
		this.data = list;
		this.paginate = paginateDto;
//		this.msg = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
//		this.message.setResultCnt(this.resultCnt);
	}
}
