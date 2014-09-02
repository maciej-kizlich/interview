package pl.maciejkizlich.interview.mail;

public interface MailProvider {

	void sendMail(MailerConfig config);
	
}
