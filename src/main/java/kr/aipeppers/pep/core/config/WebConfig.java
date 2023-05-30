package kr.aipeppers.pep.core.config;

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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.interceptor.BoInterceptor;
import kr.aipeppers.pep.core.interceptor.DefaultInterceptor;
import kr.aipeppers.pep.core.interceptor.UiInterceptor;

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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/profil").addResourceLocations("file:///var/www/html/mobileapp_api/app/webroot/uploads/images");
		registry.addResourceHandler("/upload/post").addResourceLocations("file:///var/www/html/mobileapp_api/app/webroot/uploads/images/post");
	}

	@Bean("cmnInterceptor")
	@ConditionalOnProperty(name = "spring.application.name", havingValue = CmnConst.APP_NAME_BO)
	public BoInterceptor boInterceptor() {
		return new BoInterceptor();
	}

	@Bean("cmnInterceptor")
	@ConditionalOnProperty(name = "spring.application.name", havingValue = CmnConst.APP_NAME_UI)
	public UiInterceptor uiInterceptor() {
		return new UiInterceptor();
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
