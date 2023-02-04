package org.unibl.etf.ip.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.unibl.etf.ip.dao.mysql.MySQLMessageDAO;
import org.unibl.etf.ip.model.dto.Message;

public class MessageService {

	private static final String BEGIN = "U nastavku Vam dostavljamo odgovor na e-mail naslovljen sa ";

	public List<Message> getAll() {
		return new MySQLMessageDAO().getAll();
	}

	public List<Message> getAllByStatus(boolean read) {
		return new MySQLMessageDAO().getAllByStatus(read);
	}
	
	public List<Message> getAllByContent(String content) {
		return new MySQLMessageDAO().getAllByContent(content);
	}

	public Message getById(Integer id) {
		return new MySQLMessageDAO().getById(id);
	}

	public boolean read(Integer id, Message message) {
		return new MySQLMessageDAO().update(id, message);
	}

	public void sendResponse(String email, String title, String response) {
		final String username = "ip.webshop1@gmail.com";
		final String password = "esdslcrzpsvlnmdd";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Odgovor od korisni�?ke podrške", StandardCharsets.UTF_8.name());
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(BEGIN + title + ":<br /><br />" + response, "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException e) {
		}
	}

}
