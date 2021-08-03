package core.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

/**
 * The Class Mailer - Used for email functionality
 */
@Component
public class Mailer {

	/**
	 * Send password reset email to user
	 * @param code - the password reset code
	 * @param to- address of recipient
	 * @return true if email successfully sent
	 */
	public synchronized boolean sendPasswordResetEmail(String code, String to) {
		try {
		String content  = code;

	      Properties prop = new Properties();
	        prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
			
			Session session = Session.getInstance(prop, new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication("visiontradingaston@gmail.com", "Likliklik6");
			    }
			});
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(
			  Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Vision Trading - Password Reset Code");

			String msg = content;

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}
	
	
	
	
	
}
