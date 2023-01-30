package kr.aipeppers.pep.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

	private static final int POLL_SIZE = 20;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

		scheduler.setPoolSize(POLL_SIZE);
		scheduler.setWaitForTasksToCompleteOnShutdown(false);
		scheduler.initialize();

		taskRegistrar.setTaskScheduler(scheduler);
	}

}
