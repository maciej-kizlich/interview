package pl.maciejkizlich.interview.mail.templates;


public enum EMAIL_TEMPLATES {

	USER_REGISTRATION("User registration confirmation", "register"),
	BOOK_RETURN_REMINDER("Book return reminder", "book_return_reminder");

	private String emailSubject;
	
	private String templateFileName;
	
	EMAIL_TEMPLATES(String subject, String templateFile){
		this.emailSubject = subject;
		this.templateFileName = templateFile;
	}
	
	public String getEmailSubject(){
		return this.emailSubject;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}
}
