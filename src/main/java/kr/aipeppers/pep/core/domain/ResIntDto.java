package kr.aipeppers.pep.core.domain;

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
	@Schema(description = "결과코드")
	private int code;
	@Schema(description = "결과메시지")
	private String msg = "success";

	public ResIntDto(int resultCnt) {
		this.code = resultCnt > 0 ? 200 : 500;
		this.msg = "success";
	}

}