package com.ballistic.velocity.email;

import com.ballistic.velocity.util.IVelocityContext;
import com.ballistic.velocity.velocity.TemplateFactory;
import com.ballistic.velocity.velocity.TemplateType;
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

    @Autowired
    private TemplateFactory templateFactory;

    public boolean sendEmail(EmailContent emailContent) { return sendMail(emailContent) ? true : false ; }

    private Session getSession() {
        Session session = Session.getInstance(getProperties(),
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SenderEmail.USER_ID,SenderEmail.PASSWORD);
                }
            });
        return session;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }


    private boolean sendMail(EmailContent emailContent) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(SenderEmail.USER_ID));
            // send to's
            if(emailContent.getSendTo() != null && emailContent.getSendTo().size() > 0) {
                String sendTo = emailContent.getSendTo().toString();
                sendTo = sendTo.substring(1, sendTo.length()-1);
                logger.debug("Send To :- "  + sendTo);
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
                // send cc's
                if (emailContent.getCcTo() != null && emailContent.getCcTo().size() > 0) {
                    String ccSendTo = emailContent.getCcTo().toString();
                    ccSendTo = ccSendTo.substring(1, ccSendTo.length()-1);
                    logger.debug("Send Cc :- "  + ccSendTo);
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
    private String emailContent(EmailContent emailContent) {
        try {
            return this.templateFactory.
                getWriterResponse(TemplateType.EMAIL_TEMPLATE_PATH,
                    IVelocityContext.getVelocityContextWithMessage(TemplateType.EMAIL_TEMPLATE_PATH,this.templateFactory.getContext(), emailContent)).toString();
        }catch (NullPointerException ex) {
            return "Template Server Have Issue";
        }
    }

}
