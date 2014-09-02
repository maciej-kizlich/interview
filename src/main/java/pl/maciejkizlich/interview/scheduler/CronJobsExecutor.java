package pl.maciejkizlich.interview.scheduler;

import java.security.InvalidParameterException;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import pl.maciejkizlich.interview.spring.postprocessors.CronJobsBeanPostProcessor;
import pl.maciejkizlich.interview.utils.ApplicationContextProvider;

public class CronJobsExecutor {
	
	private ApplicationContext appContext;

	private Map<String, Class<? extends Object>> jobBeansDefinitions;

	public CronJobsExecutor() {
		this.jobBeansDefinitions = CronJobsBeanPostProcessor
				.getJobBeansDefinitions();
		this.appContext = ApplicationContextProvider.getApplicationContext();
	}

	public void execute(String jobName) {
		validate(jobName);

		AbstractScheduledJob<?> prepareJobBean = prepareJobBean(jobName);

		prepareJobBean.initJob();
	}

	private AbstractScheduledJob<?> prepareJobBean(String jobName) {
		AutowireCapableBeanFactory autowireCapableBeanFactory = appContext
				.getAutowireCapableBeanFactory();

		Object bean = appContext.getBean(jobName);

		autowireCapableBeanFactory.autowireBean(bean);

		return (AbstractScheduledJob<?>) bean;
	}

	private void validate(String jobName) {

		if (appContext.containsBean(jobName) == false) {
			throw new NoSuchBeanDefinitionException(
					"Cannot find bean definition in Spring ApplicationContext");
		}

		if (jobBeansDefinitions.keySet().contains(jobName) == false) {
			throw new InvalidParameterException(
					"Cannot find proper cron job bean definition");
		}
	}
}