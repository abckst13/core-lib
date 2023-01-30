package kr.aipeppers.pep.core.util.paginate;

import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.util.NumberUtil;
import kr.aipeppers.pep.core.util.StringUtil;

@Slf4j
@Data
public class PaginateModel implements Serializable {

	private static final long serialVersionUID = -6119717142471756964L;

	// 페이지 파라미터 변수
	private String pageParam = "page";

	// 페이지 사이즈
	private int pageSize;

	// 레코드 사이즈(페이지당 크기(pageUnit과 동일), pageUnit은 입력값)
	private int recordSize;

	// 현재 페이지
	private int currPage;

	// 총 레코드 수
	private int totalRecords = 0;

	// 총 페이지수
	private int totalPages;

	// 현재페이지 시작번호
	private int startNum;

	// 현재페이지 종료번호
	private int endNum;

	// 블럭 시작 페이지
	private int startPage;

	// 블럭 종료 페이지
	private int endPage;

	// 이전 블럭의 마지막 페이지
	private int prevPage;

	// 다음 블럭의 첫번째 페이지
	private int nextPage;

	// 현재 블럭
	private int currBlock;

	// 전체 블럭
	private int totalBlock;

	// paginate suffix
	private String suffix;
	
	// 소팅 컬럼
	private String sortColumn;
	
	// 소팅 순서
	private String sortOrder;
	
	//입력 - 현재 페이지
	private int page;
	//입력 - 페이지당 크기
	private int pageUnit;
	
	public void init(Box paramBox) {
		String currPageStr = paramBox.getString(pageParam);
		currPage = StringUtil.isEmpty(currPageStr) ? 1 : Integer.parseInt(currPageStr);
		if(currPage<1) {
			currPage = 1;
		}
		totalPages = (totalRecords - 1) / recordSize + 1;
		if(currPage > totalPages) {
			currPage = totalPages;
		}
		startNum = (currPage - 1) * recordSize + 1;
		endNum = currPage * recordSize;
		prevPage = ((currPage - 1) / pageSize) * pageSize;
		if (prevPage <= 0 ) {
			prevPage = 1;
		}
		startPage = prevPage + 1;

		nextPage = startPage + pageSize;
		endPage = nextPage - 1;
		if(endPage > totalPages) {
			endPage = totalPages;
		}
		currBlock = NumberUtil.toInt(Math.ceil((double)currPage/pageSize));
		totalBlock = NumberUtil.toInt(Math.ceil((double)totalPages/pageSize));
	}

	public void init2(Box paramBox) {
		String currPageStr = paramBox.getString(pageParam);
		currPage = StringUtil.isEmpty(currPageStr) ? 1 : Integer.parseInt(currPageStr);
		if (currPage < 1) {
			currPage = 1;
		}

		startNum = (currPage - 1) * recordSize + 1;
		endNum = currPage * recordSize;
	}

	public void init(Box paramBox, int totalRecords) {
		setTotalRecords(totalRecords);
		init(paramBox);
	}

	public void setTotalCount(int totalRecords) {
		if(currPage < 1) {
			return;
		}
		this.totalRecords = totalRecords;
//		int totalPages = (totalCount - 1) / pageSize + 1;
//		if(pageNumber > totalPages) {
//			pageNumber = totalPages;
//		}
		startNum = (currPage - 1) * pageSize + 1;
		endNum = currPage * pageSize;
	}

	public void setDirectTotalCount(int totalRecords, int directPageSize) {
		if(currPage < 1) {
			return;
		}
		this.totalRecords = totalRecords;
		totalPages = (totalRecords - 1) / directPageSize + 1;
		if(currPage > totalPages) {
			currPage = totalPages;
		}

		startNum = (currPage - 1) * directPageSize + 1;
		endNum = currPage * directPageSize;
	}

}
