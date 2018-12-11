package com.ballistic.velocity.bean.email;

import com.ballistic.velocity.model.Campaign;
import com.ballistic.velocity.model.CampaignMockup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EmailTest {

    public static void main(String argsp[]) {

        while (true) {
            long startTime = System.currentTimeMillis();
            Campaign campaign = CampaignMockup.getCampaignMockup();

            HashMap<String, Object> mailMessage = new HashMap<>();
            mailMessage.put("BID_RESPONSE", String.format(EmailSubject.BID_RESPONSE, campaign.getCampaignId(), campaign.getAdId()));

            Set<String> sendTo = new HashSet<>();
            sendTo.add("***********@gmail.com");

            Set<String> sendCc = new HashSet<>();
            sendCc.add("***********@admaxim.com");

            EmailContent emailContent = new EmailContent();
            emailContent.setSendTo(sendTo);
            emailContent.setCcTo(sendCc);
            emailContent.setSubject(String.format(EmailSubject.BID_RESPONSE, campaign.getCampaignId(), campaign.getAdId()));
            emailContent.setMailMessage(mailMessage);
            EmailManager emailManager = new EmailManager();
            System.out.println("Email Status :- " + emailManager.sendEmail(emailContent) + " Response Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        }
    }

}