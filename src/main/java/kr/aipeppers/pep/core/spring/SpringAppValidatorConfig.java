package kr.aipeppers.pep.core.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springmodules.validation.commons.ConfigurableBeanValidator;
import org.springmodules.validation.commons.DefaultValidatorFactory;
import org.springmodules.validation.commons.ValidatorFactory;

import kr.aipeppers.pep.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SpringAppValidatorConfig {

	/**
	 * Validator factory.
	 *
	 * @return the validator factory
	 */
	@Bean
	public ValidatorFactory validatorFactory() {
		DefaultValidatorFactory validatorFactory = null;
		try {
			validatorFactory = new DefaultValidatorFactory();
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:kr/aipeppers/pep/**/validator/*Validator.xml");
			resources = ArrayUtils.add(resources, new ClassPathResource("config/validator/validator-rules.xml"));
			validatorFactory.setValidationConfigLocations(resources);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return validatorFactory;
	}
	
	/**
	 * Configurable bean validator.
	 *
	 * @return the configurable bean validator
	 */
	@Bean
	public ConfigurableBeanValidator configurableBeanValidator() {
		ConfigurableBeanValidator configurableBeanValidator = new ConfigurableBeanValidator();
		configurableBeanValidator.setValidatorFactory(validatorFactory());
		return configurableBeanValidator;
	}

}
