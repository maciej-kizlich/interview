package pl.maciejkizlich.interview.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JavaMailProvider implements MailProvider {

	protected final static Logger LOG = LoggerFactory
			.getLogger(JavaMailProvider.class);

	@Override
	public void sendMail(MailerConfig config) {

		Properties props = new Properties();

		InputStream resourceAsStream = JavaMailProvider.class.getClassLoader().getResourceAsStream("properties/mail.properties");
		try {
			props.load(resourceAsStream);
		} catch (IOException e) {
			throw new MailProviderException("Could not load mail properties", e);
		}

		final String username = props.getProperty("mail.username");
		final String password = props.getProperty("mail.password");
		final boolean ignoreMailSend = Boolean.valueOf(props.getProperty("ignore.mail.send"));

		if (ignoreMailSend == false) {

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(config.getRecipientEmail()));

				String emailContent = config.getStTemplate().render();

				message.setSubject(config.getTemplate().getEmailSubject());
				message.setText(emailContent, "utf-8",
						MIME_SUBTYPE.HTML.getSubtype());

				Transport.send(message);

			} catch (MessagingException e) {
				throw new MailProviderException("Could not sent email", e);
			}
		} else {
			LOG.info("Skipped sending email");
		}

	}
	enum MIME_SUBTYPE{
		PLAINTEXT("text/plain"),
		HTML("html");
		
		private String subtype;

		public String getSubtype() {
			return subtype;
		}

		private MIME_SUBTYPE(String subtype) {
		this.subtype = subtype;
		}
	}
}
