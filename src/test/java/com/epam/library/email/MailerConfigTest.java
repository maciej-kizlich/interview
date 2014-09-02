package com.epam.library.email;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.maciejkizlich.interview.mail.MailerConfig;
import pl.maciejkizlich.interview.mail.templates.EMAIL_TEMPLATES;

public class MailerConfigTest {
	
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldBuildProperConfig(){
		
		//given
		String recipient = "test@epam.com";
	
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("name", recipient);
        
        //when
        MailerConfig emailConfig = new MailerConfig.EmailBuilder(recipient).template(EMAIL_TEMPLATES.USER_REGISTRATION).supplyVariables(valuesMap).build();
        
        //then
        assertEquals(recipient, emailConfig.getRecipientEmail());
        assertEquals(EMAIL_TEMPLATES.USER_REGISTRATION, emailConfig.getTemplate());
        assertEquals(emailConfig.getStTemplate().getAttribute("name"), recipient);
	
	}

	@Test
	public void shouldFailValidationDueToMissingParameterValue(){
		
		//given
		String recipient = "test@epam.com";
		
		EMAIL_TEMPLATES template = EMAIL_TEMPLATES.USER_REGISTRATION;
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        
        //then
        expectedException.expectMessage("Email template [" + template.toString() + "] is not valid, missing parameter: [" + "name" + "]");
        
        //when
        new MailerConfig.EmailBuilder(recipient).template(template).supplyVariables(valuesMap).build();
		
	}
	
	@Test
	public void shouldFailValidationDueToMissingRecipient(){
		
		//given
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("name", "testname");
        
        //then
        expectedException.expectMessage("Email recipient is not set!");
        
        //when
        new MailerConfig.EmailBuilder(null).template(EMAIL_TEMPLATES.USER_REGISTRATION).supplyVariables(valuesMap).build();
		
	}
	
	@Test
	public void shouldSkipValidation(){
		
        Map<String, Object> invalidValues = new HashMap<String, Object>();
        
        new MailerConfig.EmailBuilder(null).template(EMAIL_TEMPLATES.USER_REGISTRATION).skipValidation().supplyVariables(invalidValues).build();
        
	}
	
}
