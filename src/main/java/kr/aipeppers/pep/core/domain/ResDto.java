package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@Deprecated
@NoArgsConstructor
public class ResDto extends CmnDto {
	private int resultCnt;

	public ResDto(int resultCnt) {
		this.resultCnt = resultCnt;
	}
}