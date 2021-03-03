package ExceptionHandlerLibrary;

import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//for sending email import external jars= activation.jar,mail.jar

public class EmailAction implements Action{

	@Override
	public void takeAction(Map<String, String> nvPair) {
		// TODO Auto-generated method stub
		System.out.println("In mail");
		//System.out.println("Email send to"+nvPair.toString());
		send(nvPair.get("from"), nvPair.get("password"), nvPair.get("to"), nvPair.get("subject"), "Exception occured..Try Again ..!!");

		
		
	}
	
	private static void send(String from, String password, String to, String subject, String msg)
	{
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() 
        {
        	protected PasswordAuthentication getPasswordAuthentication() 
        	{
        		return new PasswordAuthentication(from, password);
        	}    
        });
        
        try {
	         MimeMessage message = new MimeMessage(session);
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject(subject);
	         message.setText(msg);
	         Transport.send(message);
	         System.out.println("message sent successfully");
        }
        catch (MessagingException e) {
        	throw new RuntimeException(e);
        }
	}
	


                        
        

}
