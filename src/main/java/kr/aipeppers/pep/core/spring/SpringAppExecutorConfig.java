package kr.aipeppers.pep.core.spring;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAppExecutorConfig {

	/**
	 * Thread pool task executor.
	 *
	 * @return the executor
	 */
	@Bean(name="taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(1000);
//		executor.setAllowCoreThreadTimeOut(true);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}
