package kr.aipeppers.pep.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ResPageDto {

	@ApiModelProperty(value="페이지번호", example="1")
	private int page;
	@ApiModelProperty(value="페이지당갯수", example="20")
	private int pageUnit;
	@ApiModelProperty(value="소팅컬럼")
	private String sortColumn;
	@ApiModelProperty(value="소팅순서(ASC, DESC)")
	private String sortOrder;
	
	@ApiModelProperty(hidden=true)
	private int currPage;
	@ApiModelProperty(hidden=true)
	private int rowNum;
	@ApiModelProperty(hidden=true)
	private int recordSize;
	@ApiModelProperty(hidden=true)
	private int totalPages;
	@ApiModelProperty(hidden=true)
	private int totalRecords;
	@ApiModelProperty(hidden=true)
	private int startNum;
	@ApiModelProperty(hidden=true)
	private int endNum;
	@ApiModelProperty(hidden=true)
	private int startPage;
	@ApiModelProperty(hidden=true)
	private int endPage;
	@ApiModelProperty(hidden=true)
	private int prevPage;
	@ApiModelProperty(hidden=true)
	private int nextPage;
	@ApiModelProperty(hidden=true)
	private int currBlock;
	@ApiModelProperty(hidden=true)
	private int totalBlock;
	@ApiModelProperty(hidden=true)
	private int pageSize;
}
