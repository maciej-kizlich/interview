package pl.maciejkizlich.interview.scheduler;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import pl.maciejkizlich.interview.mail.MailProvider;
import pl.maciejkizlich.interview.persistence.dao.AdminRepository;
import pl.maciejkizlich.interview.persistence.dao.BookOrderRepository;
import pl.maciejkizlich.interview.persistence.dao.BookRepository;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.AsyncJobError;
import pl.maciejkizlich.interview.utils.TimeProvider;

@Async
public abstract class AbstractScheduledJob<DATASOURCE_TYPE> {

	@Autowired
	protected BookOrderRepository bookOrderRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected AdminRepository adminRepository;

	@Autowired
	protected BookRepository bookRepository;

	@Autowired
	protected TimeProvider timeProvider;

	@Autowired
	protected MailProvider mailProvider;

	@Autowired
	private PlatformTransactionManager txManager;
	
	protected final static Logger LOG = LoggerFactory.getLogger(AbstractScheduledJob.class);

	public void initJob() {
		final TransactionTemplate txTemplate = new TransactionTemplate(txManager);

		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					DATASOURCE_TYPE jobDatasource = getJobDatasource();
					executeJob(jobDatasource);
				} catch (Exception th) {
					processException(th);
				}
			}
		});
	}
	
	private void processException(Exception th) {
		
		AsyncJobError error = new AsyncJobError();
		error.setDateOfOccuring(timeProvider.getCurrentTime().toDate());
		error.setJobName(getFailedJobName(th.getStackTrace()));
		error.setLastException(th.getClass().getName());

		Exception initialException = getInitialException(th);

		error.setInitialException(initialException.getClass().toString());
		error.setInitialMessage(initialException.getMessage());

		adminRepository.saveAsyncJobError(error);
	}

	private String getFailedJobName(
			StackTraceElement[] stackTraceElements) {
		for (StackTraceElement element : stackTraceElements) {
			String className = element.getClassName();
			try {
				Class<?> clazz = Class.forName(className);
				if (AbstractScheduledJob.class.isAssignableFrom(clazz)){
					return StringUtils.substringAfterLast(className, ".");
				}
			} catch (ClassNotFoundException e) {
				return null;
			}
		}
		return null;
	}
	
	private Exception getInitialException(Throwable th) {
		
		Exception initialException = null;
		
		while(null != th.getCause()){
			initialException = (Exception) th.getCause();
			th = initialException;
		}
		
		if(null == initialException){
			return (Exception) th;
		}
		
		return initialException;
	}

	protected abstract DATASOURCE_TYPE getJobDatasource();

	protected abstract void executeJob(DATASOURCE_TYPE jobDatasource);

	protected DateTime getCurrentDateTime() {
		return timeProvider.getCurrentTime();
	}
}
