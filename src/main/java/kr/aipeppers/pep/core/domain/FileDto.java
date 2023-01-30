package kr.aipeppers.pep.core.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kr.aipeppers.pep.core.util.ConfigUtil;
import kr.aipeppers.pep.core.util.DateUtil;
import lombok.Data;

@Data
public class FileDto {
	private int fileNo;
	private String fileGb;
	private String refNo;
	private String fileNm;
	private String sysFileNm;
	private String filePath;
	private long fileSize;
	private String fileExt;
	private String fileType;
	private String fileUrl;
	private String fileKind;

	private String minioPath;

//	private MultipartFile multipartFile;

//	public FileDto(MultipartFile mFile, String fileGb, Date date, String refNo, String fileKind) {
	public FileDto(String fileGb, Date date, String refNo, String fileKind) {		
		this.minioPath = ConfigUtil.getString("file.path.sttBatch") + DateUtil.format(date, DateUtil.YEARMONTH_PATTERN) + "/" + DateUtil.format(date, DateUtil.DAY_PATTERN) + "/";

//		this.fileNo;
		this.fileGb = fileGb;
		this.refNo = refNo;
//		this.fileNm = mFile.getOriginalFilename();
		this.sysFileNm = UUID.randomUUID().toString();
		this.filePath = this.minioPath;
//		this.fileSize = mFile.getSize();
//		this.fileExt = mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(".") + 1, mFile.getOriginalFilename().length()).toLowerCase();
//		this.fileType = mFile.getContentType();
		this.fileKind = fileKind;

//		this.multipartFile = mFile;
	}

//	public FileEtt toFileEtt() {
//		FileEtt ett = new FileEtt();
//		ett.setFileNo(fileNo);
//	    ett.setFileGb(fileGb);
//	    ett.setRefNo(refNo);
//	    ett.setFileNm(fileNm);
//	    ett.setSysFileNm(sysFileNm);
//	    ett.setFilePath(filePath);
//	    ett.setFileSize(fileSize);
//	    ett.setFileExt(fileExt);
//	    ett.setFileType(fileType);
//	    ett.setFileUrl(fileUrl);
//	    ett.setFileKind(fileKind);
//
//		return ett;
//	}

}
