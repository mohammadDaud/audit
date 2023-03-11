package com.ams.common.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService extends ContentIdGenerator{

	@Value("${from-address:logibiztechnologies@gmail.com}")
	private String fromAddress;

	@Value("${from-address-name:LogiBizTech}")
	private String fromAddressName;

	private final JavaMailSender javaMailSender;

	public boolean sendEmail(String subject, String body, String... toAddress)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = getMessageHelper(subject, msg, toAddress);

		helper.setText(body, true);

		javaMailSender.send(msg);
		return true;
	}

	public boolean sendEmailWithAttachment(String subject, String body, File attachement, String... toAddress)
			throws MessagingException, IOException {

		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = getMessageHelper(subject, msg, toAddress);

		// default = text/plain
		// helper.setText("Check attachment for image!");

		// true = text/html
		helper.setText(body, true);

		// hard coded a file path
		// FileSystemResource file = new FileSystemResource(new
		// File("path/android.png"));

		helper.addAttachment(attachement.getName(), attachement);

		javaMailSender.send(msg);
		return true;

	}

	public boolean sendEmailCc(String subject, String body, String cc, String... toAddress)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = getMessageHelper(subject, msg, toAddress);

		helper.setCc(cc);
		helper.setText(body, true);
		javaMailSender.send(msg);
		return true;
	}

	public void sendEmailWithInlineImage(String subject, String body, String imagePath, String[] toAddress, String contentId,String logo, String contentLogo, String... ccAddress)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = getMessageHelper(subject, mimeMessage, toAddress);

		if(ArrayUtils.isNotEmpty(ccAddress) && StringUtils.isNotBlank(ccAddress[0]))
			helper.setCc(ccAddress[0]);

		helper.setTo(toAddress);
		// add Cid for inline image
//		String contentId = ContentIdGenerator.getContentId();
//		helper.setText(String.format(body, contentId), true);
		helper.setText(body, true);
		Path pathLogo = FileSystems.getDefault().getPath(logo);
		helper.addInline(contentLogo, pathLogo.toFile());
		Path path = FileSystems.getDefault().getPath(imagePath);
		helper.addInline(contentId, path.toFile());
		javaMailSender.send(mimeMessage);
	}

	private MimeMessageHelper getMessageHelper(String subject, MimeMessage msg, String... toAddress)
			throws UnsupportedEncodingException, MessagingException {
		InternetAddress from = new InternetAddress(fromAddress, fromAddressName);
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setFrom(from);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		return helper;
	}

}

class ContentIdGenerator {

	static int seq = 0;
	static String hostname;

	public static void getHostname() {
		try {
			hostname = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			// we can't find our hostname? okay, use something no one else is
			// likely to use
			hostname = new Random(System.currentTimeMillis()).nextInt(100000) + ".localhost";
		}
	}

	/**
	 * Sequence goes from 0 to 100K, then starts up at 0 again. This is large
	 * enough, and saves
	 * 
	 * @return
	 */
	public static synchronized int getSeq() {
		return (seq++) % 100000;
	}

	/**
	 * One possible way to generate very-likely-unique content IDs.
	 * 
	 * @return A content id that uses the hostname, the current time, and a sequence
	 *         number to avoid collision.
	 */
	public static String getContentId() {
		getHostname();
		int c = getSeq();
		return c + "." + System.currentTimeMillis() + "@" + hostname;
	}
}
