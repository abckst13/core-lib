package kr.gscaltex.gsc.core.config;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.interceptor.DefaultInterceptor;
import kr.gscaltex.gsc.core.interceptor.FrontInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.hosts.allows}")
	private String[] corsHostsAllows;

	@Autowired
	@Qualifier("cmnInterceptor")
	private HandlerInterceptor cmnInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(defaultInterceptor())
			.addPathPatterns("/**")
		;

		registry.addInterceptor(cmnInterceptor)
			.addPathPatterns("/restapi/**")
			.addPathPatterns("/cmn/**")
			.addPathPatterns("/test/**")
			.addPathPatterns("/sample/**")
			;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		if(!StringUtils.equals(corsHostsAllows[0], "none")) {
			registry.addMapping("/**")
			.allowedOrigins(corsHostsAllows);
//			.allowedMethods("GET", "POST")
		}
	}

	@Bean("cmnInterceptor")
	@ConditionalOnProperty(name = "spring.application.name", havingValue = CmnConst.APP_NAME_FRONT)
	public FrontInterceptor frontInterceptor() {
		return new FrontInterceptor();
	}

	@Bean
	public DefaultInterceptor defaultInterceptor() {
		return new DefaultInterceptor();
	}

//	@Bean
//	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//		RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
//		requestMappingHandlerMapping.setOrder(0);
//		requestMappingHandlerMapping.setAlwaysUseFullPath(true);
//		return requestMappingHandlerMapping;
//	}
}
