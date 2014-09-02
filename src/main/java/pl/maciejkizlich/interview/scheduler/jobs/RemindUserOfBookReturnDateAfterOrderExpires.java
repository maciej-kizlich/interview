package pl.maciejkizlich.interview.scheduler.jobs;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import pl.maciejkizlich.interview.mail.MailerConfig;
import pl.maciejkizlich.interview.mail.templates.EMAIL_TEMPLATES;
import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.scheduler.AbstractScheduledJob;

public class RemindUserOfBookReturnDateAfterOrderExpires extends AbstractScheduledJob<List<Object[]>>{

	@Override
	protected List<Object[]> getJobDatasource() {
		
		Integer numberOfDays = Integer.valueOf(adminRepository.getConfigByName("book.return.reminder.in.days.after").getValue());
		DateTime dateInPast = timeProvider.getCurrentTime().minusDays(numberOfDays);
		
		List<Object[]> results = bookOrderRepository.findBooksAndUsersWithOrdersExpiresOn(dateInPast);
		return results;
	}

	@Override
	protected void executeJob(List<Object[]> jobDatasource) {
		for(Object[] row : jobDatasource){
			
			String userName = ((User) row[0]).getUsername();
			String bookName = ((Book) row[1]).getTitle() ;
			String returnDate = ((Timestamp) row[2]).toString();
			
			LOG.info("Sending reminder to user: " + userName); 
			
	        Map<String, Object> valuesMap = new HashMap<String, Object>();
	        valuesMap.put("name", userName);
	        valuesMap.put("bookName", bookName);
	        valuesMap.put("returnDate", (returnDate.split("\\s+"))[0]);
	        
	        MailerConfig emailConfig = new MailerConfig.EmailBuilder(userName).template(EMAIL_TEMPLATES.BOOK_RETURN_REMINDER).supplyVariables(valuesMap).build();
	        mailProvider.sendMail(emailConfig);
		
		}
	}

}
