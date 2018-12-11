package com.ballistic.velocity.bean.email;

import java.util.Map;
import java.util.Set;

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
 *  Note :- Email Section Done *
 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
public class EmailContent {

    private Set <String> sendTo;
    private Set <String> ccTo;
    private String subject;
    private Map <String, Object> mailMessage;

    public EmailContent() {}

    public EmailContent(Set <String> sendTo, String subject, Map <String, Object> mailMessage) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.mailMessage = mailMessage;
    }

    public EmailContent(Set <String> sendTo, Set <String> ccTo, String subject, Map <String, Object> mailMessage) {
        this.sendTo = sendTo;
        this.ccTo = ccTo;
        this.subject = subject;
        this.mailMessage = mailMessage;
    }

    public Set <String> getSendTo() { return sendTo; }
    public void setSendTo(Set <String> sendTo) { this.sendTo = sendTo; }

    public Set <String> getCcTo() { return ccTo; }
    public void setCcTo(Set <String> ccTo) { this.ccTo = ccTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Map <String, Object> getMailMessage() { return mailMessage; }
    public void setMailMessage(Map <String, Object> mailMessage) { this.mailMessage = mailMessage; }

    @Override
    public String toString() {
        return "EmailContent{" + "sendTo=" + sendTo + ", ccTo=" + ccTo + ", subject='" + subject + '\'' + ", mailMessage=" + mailMessage + '}';
    }
}
