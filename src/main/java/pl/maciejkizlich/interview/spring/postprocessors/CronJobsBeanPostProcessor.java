package pl.maciejkizlich.interview.spring.postprocessors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import pl.maciejkizlich.interview.scheduler.AbstractScheduledJob;

public class CronJobsBeanPostProcessor implements BeanPostProcessor {

	private static Map<String, Class<? extends Object>> jobBeansDefinitions = new HashMap<String, Class<? extends Object>>();

	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {

		Class<?> superclass = arg0.getClass().getSuperclass();

		if (AbstractScheduledJob.class.isAssignableFrom(superclass)) {
			jobBeansDefinitions.put(arg1, arg0.getClass().getSuperclass());
		}

		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

	public static Map<String, Class<? extends Object>> getJobBeansDefinitions() {
		return jobBeansDefinitions;
	}
}