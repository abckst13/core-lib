package kr.gscaltex.gsc.core.util.paginate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;
import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.data.Box;
import kr.gscaltex.gsc.core.domain.ResPageDto;
import kr.gscaltex.gsc.core.util.BeanUtil;
import kr.gscaltex.gsc.core.util.ConfigUtil;
import kr.gscaltex.gsc.core.util.JsonUtil;
import kr.gscaltex.gsc.core.util.StringUtil;

@Slf4j
@Component
public class Paginate {

	@Value("${paginate.page-unit}")
	private int paginatePageUnit;

	@Value("${paginate.page-size}")
	private int paginatePageSize;
	
	@Value("${paginate.return-max-row}")
	private int paginateReturnMaxRow;

	private String getPaginateParam(String name, String suffix) {
		if(StringUtil.isNotEmpty(suffix)) {
			name = name + "_" + suffix;
		}
		return StringUtil.camelToLower(name);
	}

	public void init(Box paramBox, ModelMap modelMap, int totalRecords) throws Exception {
		PaginateModel paginateModel = initLoad(paramBox, totalRecords);
		String paramPaginate = getPaginateParam(CmnConst.PARAM_PAGINATE, paginateModel.getSuffix());
		modelMap.addAttribute(paramPaginate, paramBox.get(paramPaginate));
	}

	public void init(Box paramBox, ModelMap modelMap) throws Exception {
		PaginateModel paginateModel = initLoad(paramBox, -1);
		String paramPaginate = getPaginateParam(CmnConst.PARAM_PAGINATE, paginateModel.getSuffix());
		modelMap.addAttribute(paramPaginate, paramBox.get(paramPaginate));
	}
	
	public ResPageDto init(Box paramBox, int totalRecords) throws Exception {
		PaginateModel paginateModel = initLoad(paramBox, totalRecords);
		String paramPaginate = getPaginateParam(CmnConst.PARAM_PAGINATE, paginateModel.getSuffix());
		return BeanUtil.convert(paramBox.get(paramPaginate), ResPageDto.class);
	}

	public ResPageDto init(Box paramBox) throws Exception {
		PaginateModel paginateModel = initLoad(paramBox, -1);
		String paramPaginate = getPaginateParam(CmnConst.PARAM_PAGINATE, paginateModel.getSuffix());
		return BeanUtil.convert(paramBox.get(paramPaginate), ResPageDto.class);
	}

	public PaginateModel initLoad(Box paramBox, int totalRecords) throws Exception {
		log.debug("paginatePageUnit: {}", paginatePageUnit);
		PaginateModel paginateModel = new PaginateModel();
		String paramPaginateSuffix = paramBox.getParameter(CmnConst.PARAM_PAGINATE_SUFFIX, "");
		paginateModel.setSuffix(paramPaginateSuffix);

		int pageUnit = paramBox.getInt("pageUnit") == 0 ? paginatePageUnit : paramBox.getInt("pageUnit");
		int paramRecordSize = paramBox.getInt(getPaginateParam(CmnConst.PARAM_PAGINATE_RECORD_SIZE, paramPaginateSuffix));
		if(paramRecordSize > 0) {
			pageUnit = paramRecordSize;
		}
		int pageSize = paginatePageSize != 0 ? paginatePageSize : 10;
		int paramPageSize = paramBox.getInt(getPaginateParam(CmnConst.PARAM_PAGINATE_PAGE_SIZE, paramPaginateSuffix));
		if(paramPageSize > 0) {
			pageSize = paramPageSize;
		}
		paginateModel.setPageParam(getPaginateParam(CmnConst.PARAM_PAGINATE_PAGE, paramPaginateSuffix));
		paginateModel.setRecordSize(pageUnit);
		paginateModel.setPageSize(pageSize);
		paginateModel.setSortColumn(paramBox.getString("sortColumn"));
		paginateModel.setSortOrder(paramBox.getString("sortOrder"));

		if (totalRecords == -1) {
			paginateModel.init2(paramBox);
		} else {
			paginateModel.init(paramBox, totalRecords);
		}

		String paramPaginate = getPaginateParam(CmnConst.PARAM_PAGINATE, paramPaginateSuffix);
		paramBox.put(paramPaginate, paginateModel);

		if (paramBox.getBoolean("isExcel")) {
			paginateModel.setStartNum(1);
			paginateModel.setEndNum(ConfigUtil.getInt("excel.max.row"));
		}
		
		if (paginateReturnMaxRow > 0) { //리턴 row 과다방지
			if (paginateReturnMaxRow < (paginateModel.getEndNum() - paginateModel.getStartNum())) {
				paginateModel.setEndNum(paginateModel.getStartNum() + paginateReturnMaxRow - 1);
			}
		}
		
		paginateModel.setPage(paramBox.getInt("page"));
		paginateModel.setPageUnit(paramBox.getInt("pageUnit"));
		return paginateModel;
	}

}

