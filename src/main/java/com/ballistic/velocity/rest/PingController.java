package com.ballistic.velocity.rest;

import com.ballistic.velocity.email.EmailContent;
import com.ballistic.velocity.email.EmailManager;
import com.ballistic.velocity.email.EmailSubject;
import com.ballistic.velocity.model.Campaign;
import com.ballistic.velocity.model.dto.CampaignEmailDto;
import com.ballistic.velocity.util.IVelocityContext;
import com.ballistic.velocity.velocity.TemplateFactory;
import com.ballistic.velocity.velocity.TemplateType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PingController {

    private static final Logger logger = LogManager.getLogger(PingController.class);

    @Autowired
    private TemplateFactory templateFactory;
    @Autowired
    private EmailManager emailManager;
    private EmailContent emailContent;
    private Map<String, Object> mailMessage;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 1) If user send the zip then response will be zip   *
     * 2) if user send the dev it will send the only json  *
     * 3) if both have then send the email send zip        *
     *  * * * * * * * * * * * * * * * * * * * * * * * * *  */
    @RequestMapping(value = "/rtb-bid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String showCampaigns(@RequestBody Campaign campaigns) {
        long startTime = System.currentTimeMillis();
        try {// json send back
            String response = this.templateFactory.getWriterResponse(TemplateType.BID_TEMPLATE_PATH, IVelocityContext.
                getVelocityContextWithMessage(TemplateType.BID_TEMPLATE_PATH,this.templateFactory.getContext(),campaigns)).toString();
            logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return response;
        }catch (NullPointerException ex) {
            logger.error("Error :- " + ex.getLocalizedMessage() + " Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return "Error :- " + ex.getLocalizedMessage();
        }
    }

    /* * * * * * * * * *  *
     * Send Email Process *
     * * * * * * * * * *  */
    @RequestMapping(value = "/email/campaigns", method = RequestMethod.POST)
    public String emailCampaigns(@RequestHeader(required = false) Map<String,String> headers, @RequestBody CampaignEmailDto campaignEmailDto) {
        long startTime = System.currentTimeMillis();
        this.mailMessage = new HashMap<>();
        this.mailMessage.put("BID_RESPONSE", String.format(EmailSubject.BID_RESPONSE,
            campaignEmailDto.getCampaign().getCampaignId(), campaignEmailDto.getCampaign().getAdId()));
        this.emailContent = new EmailContent(campaignEmailDto.getSendTo(), campaignEmailDto.getCcTo(), campaignEmailDto.getSubject(), this.mailMessage);
        Boolean status = this.emailManager.sendEmail(this.emailContent);
        if(!status) {
            logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return "Your Email Not Send";
        }
        logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return "Your Email Send";
    }

}