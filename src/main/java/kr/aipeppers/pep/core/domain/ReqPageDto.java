package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ReqPageDto {

	@ApiModelProperty(value="페이지번호", example="1")
	private int page;
	@ApiModelProperty(value="페이지당갯수", example="20")
	private int pageUnit;
	@ApiModelProperty(value="소팅컬럼")
	private String sortColumn;
	@ApiModelProperty(value="소팅순서(ASC, DESC)")
	private String sortOrder;

}
