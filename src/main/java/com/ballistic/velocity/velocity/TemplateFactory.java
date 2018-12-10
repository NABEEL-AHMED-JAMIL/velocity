package com.ballistic.velocity.velocity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

// scope prototype
/* * * * * * * * * * * * * * * * * * * * *
 *  Note :- TemplateFactory Section Done *
 * * * * * * * * * * * * * * * * * * * * */
@Component
public class TemplateFactory extends VelocityWriter {

    private static final Logger logger = LogManager.getLogger(TemplateFactory.class);

    public static Template BID_TEMPLATE_PATH = null;
    public static Template EMAIL_TEMPLATE_PATH = null;

    // init the block only one time
    static {
        logger.debug("Velocity Template INIT");
        try {
            getVelocityEngine().init();
            BID_TEMPLATE_PATH = getVelocityEngine().getTemplate(VelocityWriter.BID_TEMPLATE_PATH);
            EMAIL_TEMPLATE_PATH = getVelocityEngine().getTemplate(VelocityWriter.EMAIL_TEMPLATE_PATH);

        }catch (ExceptionInInitializerError ex) {
            logger.error("Error :- " + ex.getLocalizedMessage());
        }
        logger.debug("Velocity Template END-INIT");
    }

    private Template getTemplate(TemplateType templateType) {
        Template template = null;
        switch (templateType) {
            case BID_TEMPLATE_PATH:
                this.setWriter(new StringWriter());
                template = BID_TEMPLATE_PATH;
                break;
            case EMAIL_TEMPLATE_PATH:
                this.setWriter(new StringWriter());
                template =  EMAIL_TEMPLATE_PATH;
                break;
        }
        return template;
    }

    public StringWriter getWriterResponse(TemplateType templateType, VelocityContext context) throws NullPointerException {
        Template template = getTemplate(templateType);
        if(template != null) {
            template.merge(context, this.getWriter());
            logger.info("Response Content :- " + this.getWriter());
            return this.getWriter();
        }
        throw new NullPointerException("Template Not Found");
    }
}
