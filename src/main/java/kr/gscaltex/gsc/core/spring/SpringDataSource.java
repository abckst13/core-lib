package kr.gscaltex.gsc.core.spring;


import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import kr.gscaltex.gsc.core.exception.BaseException;
import kr.gscaltex.gsc.core.mybatis.CamelMapObjectWrapperFactory;
//import kr.gscaltex.gsc.core.mybatis.CustomSqlSessionTemplate;
import kr.gscaltex.gsc.core.mybatis.ReloadMapper;
import kr.gscaltex.gsc.core.util.crypto.AesUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
public class SpringDataSource {
	private static final String EXPRESSION = "execution(* kr.gscaltex.gsc..*Service.*(..))";

	@Value("${spring.datasource.hikari.encpassword}")
	private String encpassword;

	@Value("${spring.datasource.hikari.password}")
	private String password;

	/**
	 * Data source spied.
	 *
	 * @return the data source
	 */
	@Bean(destroyMethod="close")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSourceSpied() {
//		return DataSourceBuilder.create().password(AesUtil.decrypt(encpassword)).build();
		return DataSourceBuilder.create().password(password).build();
	}

	/**
	 * Sql session factory.
	 *
	 * @return the sql session factory
	 * @throws Exception the exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() {
		SqlSessionFactory sqlSessionFactory = null;
		try {
//			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			ReloadMapper factoryBean = new ReloadMapper();
			PathMatchingResourcePatternResolver matchingResolver = new PathMatchingResourcePatternResolver();
			factoryBean.setDataSource(dataSourceSpied());
			factoryBean.setConfigLocation(matchingResolver.getResource("classpath:mybatis/mybatis-config.xml"));
			factoryBean.setMapperLocations(matchingResolver.getResources("kr/gscaltex/gsc/**/mapper/*.xml")); //classpath:dbio/*.xml
			factoryBean.setObjectWrapperFactory(new CamelMapObjectWrapperFactory());
			sqlSessionFactory = factoryBean.getObject();
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return sqlSessionFactory;
	}

	/**
	 * Sql session.
	 *
	 * @return the sql session template
	 * @throws Exception the exception
	 */
//	@Bean(name="sqlSession", destroyMethod="clearCache")
//	public SqlSessionTemplate sqlSession() {
//		SqlSessionTemplate sqlSession = new CustomSqlSessionTemplate(sqlSessionFactory());
//		return sqlSession;
//	}

	@Bean
	@Primary
	public DataSourceTransactionManager transactionManager() throws Exception {
		// MYBATIS transactionManager
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSourceSpied());
		return dataSourceTransactionManager;
	}

	@Bean
	public TransactionInterceptor transactionAdvice() throws Exception {
		List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class));
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setRollbackRules(rollbackRules);
		transactionAttribute.setName("*");

		MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
		attributeSource.setTransactionAttribute(transactionAttribute);

		return new TransactionInterceptor(transactionManager(), attributeSource);
	}

	@Bean
	public Advisor transactionAdvisor() throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}

}
