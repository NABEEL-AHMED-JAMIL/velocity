package com.ballistic.velocity.util;

import com.ballistic.velocity.email.EmailContent;
import com.ballistic.velocity.model.Campaign;
import com.ballistic.velocity.velocity.TemplateType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;

public interface IVelocityContext {

    public static final Logger logger = LogManager.getLogger(IVelocityContext.class);

    public static VelocityContext getVelocityContextWithMessage(TemplateType templateType, VelocityContext velocityContext, Object object) {

        if(templateType.equals(TemplateType.EMAIL_TEMPLATE_PATH)) {
            EmailContent emailContent = (EmailContent) object;
            logger.info("Request Content :- " + emailContent.toString());
            velocityContext.put("mailMessage", emailContent.getMailMessage());
        } else if(templateType.equals(TemplateType.BID_TEMPLATE_PATH)) {
            Campaign campaign = (Campaign) object;
            logger.info("Request Content :- " + campaign.toString());
            velocityContext.put("campaign", campaign);
        } else {
            /**
             * if new Integration come then use else-if and case the object with related to the class
             * and fill the velocityContext object
             * */
        }

        return velocityContext;
    }

}
