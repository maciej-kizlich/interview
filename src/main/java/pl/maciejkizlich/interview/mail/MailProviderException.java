package pl.maciejkizlich.interview.mail;

@SuppressWarnings("serial")
public class MailProviderException extends RuntimeException{

	public MailProviderException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public MailProviderException(String msg) {
		super(msg);
	}
}
