package com.epam.library.testdoubles;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pl.maciejkizlich.interview.utils.TimeProvider;

public class FakeTimeProvider implements TimeProvider{

	private DateTime now;
	
	@Override
	public LocalDate getCurrentDate() {
		return null;
	}

	@Override
	public DateTime getCurrentTime() {
		return now;
	}
	
	public FakeTimeProvider(DateTime now){
		this.now = now;
	}
	
	public DateTime getNow() {
		return now;
	}
}
