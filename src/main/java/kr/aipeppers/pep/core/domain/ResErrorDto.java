package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ResErrorDto {
	@Schema(description = "결과코드")
	private String code;
	@Schema(description = "결과메시지")
	private String msg ;
//	@Schema(description = "결과메시지")
//	private String msg = "failed";
	@Schema(description = "결과데이터")
	private String data;
	@Schema(description = "null 값을 위한 예비 data")
	private String dataSe;

	public ResErrorDto(String msgId, String msgNm) {
		this.code = msgId;
		this.msg = msgNm;
//		this.data = msgNm;
		this.data = null;
	}
}
