package kr.gscaltex.gsc.core.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import kr.gscaltex.gsc.core.spring.RequestWrapper;
import kr.gscaltex.gsc.core.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest wReq = request;
        HttpServletResponse wRes = response;

        // content-type이 multipart/form-data 가 아니면 wrapping
        if (!HttpUtil.isMultipart(request)) {
            wReq = new RequestWrapper(request);
        }

        wRes = new ContentCachingResponseWrapper(response);
        String requestURI = wReq.getRequestURI();
        log.debug("requestURI:{}", requestURI);

        /*
         * if (requestURI.contains("/actuator") || requestURI.contains("/swagger")
         * || requestURI.contains("/api/public/") || requestURI.contains("/webjars/")
         * || requestURI.contains("/v2/api-docs") || requestURI.contains("/v3/api-docs")
         * || requestURI.contains("/web/")
         * || requestURI.contains("/resources/") || requestURI.contains("/favicon")
         * || requestURI.contains("/test/") || requestURI.contains("/main/")
         * || requestURI.contains("/cmn/")
         * ) {
         * chain.doFilter(wReq, wRes);
         *
         * // //내부 컨텐츠버퍼 -> 실제 response outputStream으로 write 처리!!!
         * //이게 있어야 실제 response로 보내진다. 그전까지는 내부 buffer에 write하게 되고,
         * //실제응답전에 해당 buffer의 데이터만 가져와서 작업이 되는 구조!
         * wRes.copyBodyToResponse();
         * return;
         * }
         */

        chain.doFilter(wReq, wRes);

        // Set-Cookie SameSite=None 테스트 - 테스트 결과 아래 wRes.copyBodyToResponse(); 이전에 해야 쿠키가 적용됨.
//		Cookie testCookie = new Cookie("SameOriginTestCookie", "VALUE-1234567890");
//		testCookie.setMaxAge(600);
//		testCookie.setPath("/");
//		wRes.addCookie(testCookie);

//        this.addSameSite(wRes, "None");

        // 내부 컨텐츠버퍼 -> 실제 response outputStream으로 write 처리
        // 이게 있어야 실제 response로 보내진다. 그전까지는 내부 buffer에 write하게 되고,
        // 실제응답전에 해당 buffer의 데이터만 가져와서 작업이 되는 구조
        if (wRes instanceof ContentCachingResponseWrapper) {
            ((ContentCachingResponseWrapper) wRes).copyBodyToResponse();
        }

        return;
    }

    /**
     * SESSION에 대한 Set-Cookie에만 적용이 됨
     * 만약 다른 쿠키값도 사용해야 한다면 Response를 가로채서 해야 함
     *
     * @return
     */
//	@Bean
//	public CookieSerializer cookieSerializer() {
//		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
////		cookieSerializer.setUseSecureCookie(true);
////		cookieSerializer.setSameSite("None");
//		return cookieSerializer;
//	}
//
//	private void addSameSite(HttpServletResponse response, String sameSite) {
////		log.debug("addSameSite Called!!!");
//
//		Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
//		boolean firstHeader = true;
//		for (String header : headers) {
//			if(firstHeader) {
////				log.debug("firstHeader: " + String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
//				response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
//				firstHeader = false;
//				continue;
//			}
////			log.debug("NotFirstHeader: " + String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
//			response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
//		}
//	}
}
