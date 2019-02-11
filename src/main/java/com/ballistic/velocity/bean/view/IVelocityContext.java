package com.ballistic.velocity.bean.view;

import com.ballistic.velocity.bean.email.EmailContent;
import com.ballistic.velocity.model.pojo.Document;
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
            Document document = (Document) object;
            logger.info("Request Content :- " + document.toString());
            velocityContext.put("campaign", document);
        } else if (templateType.equals(TemplateType.BAD_REQUEST)) {
        } else {
            /**
             * if new Integration come then use else-if and case the object with related to the class
             * and fill the velocityContext object
             * */

        }

        return velocityContext;
    }
}
