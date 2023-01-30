package kr.aipeppers.pep.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import kr.aipeppers.pep.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/* excelReader */
@Slf4j
public class ExcelRederUtil {

	public static String[][] simpleExcelReadPoi(File targetFile, int sheetNo) throws Exception {
		return simpleExcelReadPoi(new FileInputStream(targetFile), sheetNo, 0, 0);
	}

	public static String[][] simpleExcelReadPoi(File targetFile, int sheetNo, int startColNo, int startRowNo) throws Exception {
		return simpleExcelReadPoi(new FileInputStream(targetFile), sheetNo, startColNo, startRowNo);
	}

	public static String[][] simpleExcelReadPoi(InputStream targetFile, int sheetNo) throws Exception {
		return simpleExcelReadPoi(targetFile, sheetNo, 0, 0);
	}

	/* Poi 사용 */
	public static String[][] simpleExcelReadPoi(InputStream file, int sheetNo, int startColNo, int startRowNo) throws Exception {
		org.apache.poi.ss.usermodel.Workbook workbook = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
//		org.apache.poi.ss.usermodel.Row row = null;
		org.apache.poi.ss.usermodel.Cell cell = null;
		String[][] data = null;
		try {
	//		FileInputStream file = new FileInputStream(targetFile);
			workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			sheet = workbook.getSheetAt(sheetNo);

			int rows = sheet.getLastRowNum();
			int cells = sheet.getRow(0).getLastCellNum();
	//			if(rows > 200){ rows = 200; }
			log.debug("!!!!!!!!!rows:" + rows);
			log.debug("!!!!!!!!!cells:" + cells);
			data = new String[rows + 1][cells];
			int idx = 0;
			for(Iterator all = sheet.iterator(); all.hasNext(); ){
				org.apache.poi.ss.usermodel.Row ds = (org.apache.poi.ss.usermodel.Row)all.next();
				if (idx > startRowNo) {
					for(int i = startColNo; i < cells; i++){
						int cellType = 9;
						cell = ds.getCell(i);
	//					log.debug("!!!!!!!!!!row:" + idx + "  cell("+i+"):" + cell);
						if(null != cell) { cellType = cell.getCellType();}
						switch(cellType){
							case 0:  //Cell.CELL_TYPE_NUMERIC :
								if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
									data[idx][i] = cell.getDateCellValue().toString();
								} else{
									data[idx][i] = Integer.toString((int) cell.getNumericCellValue());
								}
								break;
							case 1: //Cell.CELL_TYPE_STRING :
								data[idx][i] = cell.getRichStringCellValue().getString();
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN :
								data[idx][i] = cell.getBooleanCellValue()+"";
								break;
							case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA :
								if(evaluator.evaluateFormulaCell(cell) == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
									if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
										data[idx][i] = "";
									} else{
										Double value = new Double(cell.getNumericCellValue());
										if ((double) value.longValue() == value.doubleValue()) {
											data[idx][i] = data[idx][i] = Long.toString(value.longValue());
										} else {
											data[idx][i] = data[idx][i] = value.toString();
										}
									}
								} else if(evaluator.evaluateFormulaCell(cell) == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING){
									data[idx][i] = cell.getStringCellValue();
								} else if(evaluator.evaluateFormulaCell(cell) == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN){
									data[idx][i] = new Boolean(cell.getBooleanCellValue()).toString();
								} else {
									data[idx][i] = cell.toString();
								}
								break;
							case 9:
								data[idx][i] = "";
							default:
						}
					}
				}

				idx++;
			}
				// 데이터 검증 테스트
	//			for (int r = 0; r < data.length; r++) {
	//				for (int c = 0; c < data[0].length; c++) {
	//					log.debug("index : " + r + ", column Index : " + c + ", data : " + data[r][c]);
	//				}
	//			}
	//		targetFile.deleteOnExit();
		} catch (Exception e) {
			log.error("error: {}", e);
			throw new BaseException(e);
		} finally {
			file.close();
			if (workbook != null) { workbook.close(); }
		}
		return data;
	}
}
