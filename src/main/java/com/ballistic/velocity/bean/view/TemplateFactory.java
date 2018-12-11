package com.ballistic.velocity.bean.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

/* * * * * * * * * * * * * * * * * * * * *
 *  Note :- TemplateFactory Section Done *
 * * * * * * * * * * * * * * * * * * * * */
@Component
public class TemplateFactory extends VelocityWriter {

    private static final Logger logger = LogManager.getLogger(TemplateFactory.class);

    public static Template BID_TEMPLATE_PATH = null;
    public static Template EMAIL_TEMPLATE_PATH = null;

    public TemplateFactory() { }

    // init the block only one time
    @PostConstruct
    public void init() throws IOException {
        logger.debug("Velocity Template INIT");
        try {
            this.engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            this.engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            this.engine.init();
            logger.debug("Bid-Template Path :- " + VelocityWriter.BID_TEMPLATE_PATH);
            BID_TEMPLATE_PATH = this.getEngine().getTemplate(VelocityWriter.BID_TEMPLATE_PATH);
            logger.debug("Emil-Template Path :- " + VelocityWriter.EMAIL_TEMPLATE_PATH);
            EMAIL_TEMPLATE_PATH = this.getEngine().getTemplate(VelocityWriter.EMAIL_TEMPLATE_PATH);
        } catch (ExceptionInInitializerError ex) {
            logger.error("Error :- " + ex.getLocalizedMessage());
        } catch (ResourceNotFoundException ex) {
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * public static void main (String args[]) throws IOException {  *
     *    TemplateFactory templateFactory = new TemplateFactory();   *
     *    templateFactory.init();                                    *
     *    }                                                          *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
}