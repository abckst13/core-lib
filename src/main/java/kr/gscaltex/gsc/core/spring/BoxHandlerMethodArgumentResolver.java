package kr.gscaltex.gsc.core.spring;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.data.Box;
import kr.gscaltex.gsc.core.util.JsonUtil;
import kr.gscaltex.gsc.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class BoxHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter mthodParameter) {
		Class<?> paramType = mthodParameter.getParameterType();
		return Box.class.isAssignableFrom(paramType);
	}

	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (StringUtil.nvl(request.getContentType()).startsWith("application/json")) {
			String body = null;
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			InputStream inputStream = null;
			try {
				inputStream = request.getInputStream();
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CmnConst.UTF8));
					char[] charBuffer = new char[128];
					int bytesRead = -1;
					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
						stringBuilder.append(charBuffer, 0, bytesRead);
					}
				} else {
					stringBuilder.append("");
				}
			} catch (IOException ex) {
				log.error("error: ", ex);
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						log.error("error: ", ex);
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException ex) {
						log.error("error: ", ex);
					}
				}
			}

			body = stringBuilder.toString();
			log.debug("body:"+body);
			Box rtnBox = new Box();
			if (body != null && !body.isEmpty()) {
				Box box = JsonUtil.toObject(body, Box.class);
				rtnBox = box.getBox("payload");
				if (rtnBox == null || rtnBox.isEmpty()) {
					rtnBox = new Box();
					if (null != box.getBox("payload") && !box.getBox("payload").isEmpty()) {
						rtnBox.put("saveList", box.getList("payload"));
					}
				} else {
					if (rtnBox.containsKey("srchBox")) {
						rtnBox = rtnBox.getBox("srchBox");
					}
					if (rtnBox.containsKey("viewBox")) {
						rtnBox = rtnBox.getBox("viewBox");
					}
					if (rtnBox.containsKey("view1Box")) {
						rtnBox = rtnBox.getBox("view1Box");
					}
					if (rtnBox.containsKey("view2Box")) {
						rtnBox = rtnBox.getBox("view2Box");
					}
					if (rtnBox.containsKey("view3Box")) {
						rtnBox = rtnBox.getBox("view3Box");
					}
					if (rtnBox.containsKey("view4Box")) {
						rtnBox = rtnBox.getBox("view4Box");
					}
					if (rtnBox.containsKey("view5Box")) {
						rtnBox = rtnBox.getBox("view5Box");
					}

				}
				log.debug("rtnBox:"+rtnBox);

				Object sesUserData = request.getSession().getAttribute(CmnConst.SES_USER_DATA);
				Iterator<String> iterator = rtnBox.keySet().iterator();
				Object key, val;
				while (iterator.hasNext()) {
					key = iterator.next();
					val = rtnBox.get(key);
					if (val instanceof Box) {
						((Box) val).put("sBox", sesUserData);
					} else if (val instanceof List) {
						List<Box> list = rtnBox.getList(key.toString());
						for (Box rowBox : list) {
							rowBox.put("sBox", sesUserData);
						}
					}
				}
			}

			rtnBox.put("sBox", (Box)request.getSession().getAttribute(CmnConst.SES_USER_DATA));
			return rtnBox;
		} else {
			Box box = new Box(request);
			Box newParamBox = new Box(box);
			request.setAttribute(CmnConst.PARAM_BOX_ORG, newParamBox);
			request.setAttribute(CmnConst.PARAM_BOX, box);
			box.put("sBox", (Box)request.getSession().getAttribute(CmnConst.SES_USER_DATA));
			return box;
		}
	}
}
