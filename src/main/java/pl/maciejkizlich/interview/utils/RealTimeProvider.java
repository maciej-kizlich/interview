package pl.maciejkizlich.interview.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class RealTimeProvider implements TimeProvider {
	
	@Override
	public LocalDate getCurrentDate() {
		
		LocalDate localDate = new LocalDate();
	
		return localDate;
	}
	
	@Override
	public DateTime getCurrentTime() {

	LocalTime localTime = new LocalTime();
	
	return localTime.toDateTimeToday();
	
	}
}
