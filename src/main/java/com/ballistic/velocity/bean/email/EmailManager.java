package com.ballistic.velocity.bean.email;

import com.ballistic.velocity.bean.view.IVelocityContext;
import com.ballistic.velocity.bean.view.TemplateFactory;
import com.ballistic.velocity.bean.view.TemplateType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
 * Note :- EmailManager Section Done *
 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
@Component
public class EmailManager {

    private static final Logger logger = LogManager.getLogger(EmailManager.class);

    private final String MAIL_SMTP_HOST = "mail.smtp.host";
    private final String SMTP_GMAIL_COM = "smtp.gmail.com";
    private final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private final String MAIL_PORT = "465";
    private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final String MAIL_SAMP_AUTH_TRUE = "true";
    private final String SSL_SOCKET_FACTORU = "javax.net.ssl.SSLSocketFactory";
    private final String MAIL_SMTP_PORT = "mail.smtp.port";

    @Autowired
    private TemplateFactory templateFactory;

    public boolean sendEmail(EmailContent emailContent) { return sendMail(emailContent) ? true : false ; }

    private Session getSession() {
        Session session = Session.getInstance(getProperties(),
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SenderEmail.USER_ID,SenderEmail.PASSWORD);
                }
            });
        return session;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(MAIL_SMTP_HOST, SMTP_GMAIL_COM);
        props.put(MAIL_SMTP_SOCKETFACTORY_PORT, MAIL_PORT);
        props.put(MAIL_SMTP_SOCKETFACTORY_CLASS, SSL_SOCKET_FACTORU);
        props.put(MAIL_SMTP_AUTH, MAIL_SAMP_AUTH_TRUE);
        props.put(MAIL_SMTP_PORT, MAIL_PORT);
        return props;
    }

    /* * * * * * * * * * * * * * * * * * * * *
     * Note :- if email set not proved then  *
     * * * * * * * * * * * * * * * * * * * * */
    private boolean sendMail(EmailContent emailContent) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SenderEmail.USER_ID));
            if(emailContent.getSendTo() != null && emailContent.getSendTo().size() > 0) {
                // * * * * * * * * * * *Send to's* * * * * * * * *
                String sendTo = emailContent.getSendTo().toString();
                sendTo = sendTo.substring(1, sendTo.length()-1);
                logger.debug("Send To :- "  + sendTo);
                // * * * * * * * * * * * * * * * * * * * * * * * *
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
                if (emailContent.getCcTo() != null && emailContent.getCcTo().size() > 0) {
                    // * * * * * * * * *Send cc's* * * * * * * * *
                    String ccSendTo = emailContent.getCcTo().toString();
                    ccSendTo = ccSendTo.substring(1, ccSendTo.length()-1);
                    logger.debug("Send Cc :- "  + ccSendTo);
                    // * * * * * * * * * * * * * * * * * * * * * * *
                    message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccSendTo));
                }
                message.setSubject(emailContent.getSubject());
                message.setContent(emailContent(emailContent), "text/html; charset=utf-8");
                Transport.send(message);
                logger.info("Email Send Successfully.");
                return true;
            }else {
                logger.error("Error :- Sent To List Null");
                return false;
            }
        } catch (MessagingException ex) {
            logger.error("Error :- " +  ex + " Use this linke to Enable https://myaccount.google.com/u/1/lesssecureapps");
            return false;
        }catch (Exception ex) {
            logger.error("Error :- " +  ex);
            return false;
        }
    }
    /**
     * Note :- Velocity Template Writer here
     * */
    private String emailContent(EmailContent emailContent) throws Exception {
        try {
            return this.templateFactory.
                getWriterResponse(TemplateType.EMAIL_TEMPLATE_PATH,
                    IVelocityContext.getVelocityContextWithMessage(TemplateType.EMAIL_TEMPLATE_PATH,this.templateFactory.getContext(), emailContent)).toString();
        }catch (Exception ex) {
            throw new Exception(ex.getLocalizedMessage());
        }
    }

}