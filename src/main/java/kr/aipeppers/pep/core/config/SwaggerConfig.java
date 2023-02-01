package kr.aipeppers.pep.core.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.google.common.collect.Lists;

import kr.aipeppers.pep.core.cont.CmnConst;
import kr.aipeppers.pep.core.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

@Slf4j
@Configuration
//@EnableSwagger2
public class SwaggerConfig {

	@Value("${spring.config.activate.on-profile}")
	private String profile;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${swagger.host}")
	private String swaggerHost;

	@Bean
	public Docket swaggerSpringfoxDocket() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2);

		log.debug("spring.config.activate.on-profile: {}", profile);
		log.debug("profile : {}", ConfigUtil.getProfile());
		log.debug("swaggerHost : {}", swaggerHost);
		log.debug(">>>>>>>>>>>>{}", ConfigUtil.getString("pep.host.ext"));
		if(!CmnConst.PROFILE_DEFAULT.equals(profile)) { //개발서버는 서버정보가 달라 yml 파일에 강제 설정한다. (TODO : 향후 환경에 맞게 수정 필요)
			List<String> schemaList = new ArrayList<String>();
			schemaList.add("http");
			schemaList.add("https");
			Set<String> targetSet = new HashSet<String>(schemaList);
			docket = docket.protocols(targetSet).host(swaggerHost);
		}

		docket = docket.ignoredParameterTypes(Authentication.class)
//				.securityContexts(Lists.newArrayList(securityContext()))
//				.securitySchemes(Lists.newArrayList(apiKey()))
				.useDefaultResponseMessages(false)

				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("kr.aipeppers.pep")).paths(PathSelectors.regex("/.*"))
				.build();
		return docket;
	}

	private ApiInfo apiInfo() {
//		if (!appName.equals(CmnConst.APP_NAME_BO)) { //FRONT일때
//			return new ApiInfoBuilder().title("AI PEPPERS FRONT 백엔드 REST API 명세서").description("AI PEPPERS FRONT 백엔드 REST API 상세 명세및 테스트 제공 화면입니다.").build();
//		} else { //BACKOFFICE일때
			return new ApiInfoBuilder().title("AI PEPPERS BACKOFFICE 백엔드 REST API 명세서").description("AI PEPPERS BACKOFFICE 백엔드 REST API 상세 명세및 테스트 제공 화면입니다.").build();
//		}
	}

	@Deprecated
	private ApiKey apiKey() {
		return new ApiKey("JWT", CmnConst.HTTP_HEADER_AUTHORIZATION, "header");
	}

	@Deprecated
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/.*")).build();
	}

	@Deprecated
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}

	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof WebMvcRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
					List<T> mappings) {
				List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null)
						.collect(Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
}