package kr.gscaltex.gsc.core.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebUtil {

	/**
	 * Sets the disposition.
	 *
	 * @param filename the filename
	 * @param request the request
	 * @param response the response
	 */
	public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/download;charset=" + CmnConst.CHARSET);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;
		try {
			UserAgentUtil userAgentUtil = new UserAgentUtil(request);
			if (userAgentUtil.detectMSIE() || userAgentUtil.detectChrome()) {
				encodedFilename = URLEncoder.encode(filename, CmnConst.CHARSET).replaceAll("\\+", "%20");
			} else if (userAgentUtil.detectFirefox()) {
				encodedFilename = "\"" + new String(filename.getBytes(CmnConst.CHARSET), "8859_1") + "\"";
			} else if (userAgentUtil.detectOpera()) {
				encodedFilename = "\"" + new String(filename.getBytes(CmnConst.CHARSET), "8859_1") + "\"";
			} else if (userAgentUtil.detectSafari()) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, CmnConst.CHARSET));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
//				encodedFilename = sb.toString().replaceAll("\\+", "%20");
			} else {
				throw new BaseException("Not supported browser");
			}
//			log.debug("userAgentUtil.detectChrome():" + userAgentUtil.detectChrome());
//			log.debug("Content-Disposition:" + dispositionPrefix + encodedFilename);
			response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
			response.setHeader("Content-Transfer-Encoding", "binary");
			if (userAgentUtil.detectOpera()) {
				response.setContentType("application/octet-stream;charset=" + CmnConst.CHARSET);
			}
		} catch (Exception e) {
			throw new BaseException(e);
		}
//		if(StringUtil.isNotEmpty(CookieUtil.getCookie("ajaxFileDownloading"))) {
//			CookieUtil.setCookie("ajaxFileDownload", "true");
//		}
	}

	/**
	 * Download clear header.
	 *
	 * @param request the request
	 * @param response the response
	 */
	public static final void downloadClearHeader(HttpServletRequest request, HttpServletResponse response) {
		if(WebUtil.isDownloadAccept(request)) {
			response.setHeader("Content-Disposition", null);
			response.setHeader("Content-Description", null);
		}
		// CookieUtil.setCookie("fileDownload", null, "/");
	}

	/**
	 * Checks if is download.
	 *
	 * @param request the request
	 * @return true, if is download
	 */
	public static final boolean isDownloadAccept(HttpServletRequest request) {
		String xAccept = request.getHeader("X-Accept");
		if(xAccept != null && xAccept.toLowerCase().startsWith("application/download")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is download extension.
	 *
	 * @param request the request
	 * @return true, if is download extension
	 */
	public static final boolean isDownloadExtension(HttpServletRequest request) {
		String uri = SpringUtil.getOriginatingServletPath(request);
		if(uri.endsWith(".file")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is do extension.
	 *
	 * @param request the request
	 * @return true, if is do extension
	 */
	public static final boolean isDoExtension(HttpServletRequest request) {
		String uri = SpringUtil.getOriginatingServletPath(request);
		if(uri.endsWith(".do")) {
			return true;
		}
		return false;
	}

}
