package kr.gscaltex.gsc.core.domain;

import java.util.List;

import kr.gscaltex.gsc.core.util.BeanUtil;
import kr.gscaltex.gsc.core.util.MsgUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ResListDto<T> {
	@Schema(description = "결과갯수")
	private int resultCnt;
	@Schema(description = "성공여부")
	private boolean success = false;
	@Schema(description = "결과리스트")
	private List<T> list;
	@Schema(description = "결과페이징정보")
	private ResPageDto paginate;
	@Schema(description = "미션데이터")
	private ResMissionResultDto missionResult;
	@Schema(description = "결과메시지")
	private ResCmnMsgDto message = new ResCmnMsgDto();

	public ResListDto(List<T> list) {
		this.resultCnt = list.size();
		this.list = list;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
//		this.message.setResultCnt(this.resultCnt);
	}

	public ResListDto(List<T> list, ResPageDto paginateDto) {
//		this.resultCnt = paginateDto.getTotalRecords();
		this.resultCnt = list.size();
		this.list = list;
		this.paginate = paginateDto;
		this.message = BeanUtil.convert(MsgUtil.getMsgBox(message.getMsgC()), ResCmnMsgDto.class);
		this.success = true;
//		this.message.setResultCnt(this.resultCnt);
	}
}
