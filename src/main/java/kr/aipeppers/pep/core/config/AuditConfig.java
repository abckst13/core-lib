package kr.aipeppers.pep.core.config;

//import java.util.Optional;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//
//import kr.aipeppers.pep.core.cont.CmnConst;
//import kr.aipeppers.pep.core.util.JwtUtil;
//import kr.aipeppers.pep.core.util.SpringUtil;
//
//@Configuration
//public class AuditConfig {
//
//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return new AuditorAwareImpl();
//	}
//
//	public class AuditorAwareImpl implements AuditorAware<String> {
//
//		@Override
//		public Optional<String> getCurrentAuditor() {
//			try {
//				HttpServletRequest httpRequest = SpringUtil.getHttpServletRequest();
//				String authHeader = httpRequest.getHeader(CmnConst.HTTP_HEADER_AUTHORIZATION);
//				authHeader = authHeader.substring(authHeader.indexOf("Bearer") + 7, authHeader.length());
//				String memId = JwtUtil.decodeTokenStr(authHeader);
//				return Optional.of(memId);
//			} catch (Exception e) {
//				return Optional.empty();
//			}
//		}
//	}
//
//}
